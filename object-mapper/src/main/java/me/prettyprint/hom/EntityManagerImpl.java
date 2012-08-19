package me.prettyprint.hom;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hom.annotations.AnnotationScanner;
import me.prettyprint.hom.annotations.DefaultAnnotationScanner;
import me.prettyprint.hom.cache.HectorObjectMapperException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles entity management by scanning classpath for classes annotated with
 * {@link Entity} and providing basic load and save methods.
 *
 * @author
 */
public class EntityManagerImpl implements EntityManager {
  private static Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

  private Keyspace keyspace;
  private HectorObjectMapper objMapper;
  private ClassCacheMgr cacheMgr;
  private EntityManagerFactory emf;

  public EntityManagerImpl(Keyspace keyspace, String classpathPrefix) {
    this(keyspace, new DefaultAnnotationScanner(), classpathPrefix);
  }

  public EntityManagerImpl(Keyspace keyspace, AnnotationScanner scanner, String classpathPrefix) {
    this(keyspace, new String[]{classpathPrefix}, null, null, scanner);
  }

  public EntityManagerImpl(Keyspace keyspace, String[] classpathPrefix) {
    this(keyspace, new DefaultAnnotationScanner(), classpathPrefix);
  }

  public EntityManagerImpl(Keyspace keyspace, AnnotationScanner scanner, String[] classpathPrefix) {
    this(keyspace, classpathPrefix, null, null, scanner);
  }

  public EntityManagerImpl(Keyspace keyspace, EntityManagerFactory emf, Class<?>... classes) {
    this(keyspace, null, null, null);
    for (Class<?> clazz : classes) {
      cacheMgr.initializeCacheForClass(clazz);
    }
    this.emf = emf;
  }

  public EntityManagerImpl(Keyspace keyspace, String[] classpathPrefix, ClassCacheMgr cacheMgr,
                           HectorObjectMapper objMapper, AnnotationScanner scanner) {
    this.keyspace = keyspace;

    if (null != cacheMgr) {
      this.cacheMgr = cacheMgr;
    } else {
      this.cacheMgr = new ClassCacheMgr();
    }

    if (null != objMapper) {
      this.objMapper = objMapper;
    } else {
      this.objMapper = new HectorObjectMapper(this.cacheMgr);
    }

    initialize(scanner, classpathPrefix);
  }

  public EntityManagerImpl(Keyspace space, EntityManagerFactoryImpl entityManagerFactory, String[] packageToScan, AnnotationScanner scanner) {
    this(space, packageToScan, null, null, scanner);
    this.emf = entityManagerFactory;
  }

  /**
   * Initialize the manager by scanning the classpath starting with the
   * <code>classpathPrefix</code>, looking for classes annotated with
   * {@link Entity}. If an Entity class is found, it looks for the
   * {@link Table} annotation to determine the Cassandra column family
   * name.
   *
   * @param classpathPrefixArr
   * @see ClassCacheMgr
   */
  public void initialize(AnnotationScanner scanner, String[] classpathPrefixArr) {
    if (null != classpathPrefixArr && 0 < classpathPrefixArr.length) {
      for (String classpathPrefix : classpathPrefixArr) {
        initializeClasspath(scanner, classpathPrefix);
      }
    }
    if (logger.isDebugEnabled()) {
      logger.debug("classpath array has {} items : {}",
          (null != classpathPrefixArr ? classpathPrefixArr.length : "0"), classpathPrefixArr);
    }
  }

  private void initializeClasspath(AnnotationScanner scanner, String classpathPrefix) {
    Set<Class<?>> classSet = scanner.scan(classpathPrefix, Entity.class);
    for (Class<?> clazz : classSet) {
      cacheMgr.initializeCacheForClass(clazz);
    }
  }

  @Deprecated
  public <T, I> T load(Class<T> clazz, I id) {
    return find(clazz, id);
  }

  @Deprecated
  public <T> T load(Class<T> clazz, Object id, ColumnSlice<String, byte[]> colSlice) {
    return find(clazz, id, colSlice);
  }

  @Deprecated
  public <T> T save(T obj) {
    persist(obj);
    return obj;
  }

  /**
   * Load an entity instance. If the ID does not map to a persisted entity, then
   * null is returned.
   *
   * @param <T>   The type of entity to load for compile time type checking
   * @param clazz The type of entity to load for runtime instance creation
   * @param id    ID of the instance to load
   * @return instance of Entity or null if can't be found
   */
  @Override
  public <T> T find(Class<T> clazz, Object id) {
    if (null == clazz) {
      throw new IllegalArgumentException("clazz cannot be null");
    }
    if (null == id) {
      throw new IllegalArgumentException("id cannot be null");
    }

    CFMappingDef<T> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
    if (null == cfMapDef) {
      throw new HectorObjectMapperException("No class annotated with @"
          + Entity.class.getSimpleName() + " for type, " + clazz.getName());
    }

    return objMapper.getObject(keyspace, cfMapDef.getEffectiveColFamName(), id);
  }

  /**
   * Load an entity instance given the raw column slice. This is a stop gap
   * solution for instanting objects using entity manager while iterating over
   * rows.
   *
   * @param <T>      The type of entity to load for compile time type checking
   * @param clazz    The type of entity to load for runtime instance creation
   * @param id       ID of the instance to load
   * @param colSlice Raw row slice as returned from Hector API, of the type
   *                 <code>ColumnSlice<String, byte[]></code>
   * @return Completely instantiated persisted object
   */
  public <T> T find(Class<T> clazz, Object id, ColumnSlice<String, byte[]> colSlice) {
    if (null == clazz) {
      throw new IllegalArgumentException("clazz cannot be null");
    }
    if (null == id) {
      throw new IllegalArgumentException("id cannot be null");
    }

    CFMappingDef<T> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
    if (null == cfMapDef) {
      throw new HectorObjectMapperException("No class annotated with @"
          + Entity.class.getSimpleName() + " for type, " + clazz.getName());
    }

    T obj = objMapper.createObject(cfMapDef, id, colSlice);
    return obj;
  }

  /**
   * Save the list of entity intances.
   *
   * @param objColl
   * @return
   */
  public Collection<Object> persist(Collection<Object> objColl) {
    if (null == objColl) {
      throw new IllegalArgumentException("object to save cannot be null");
    }

    objMapper.saveObjCollection(keyspace, objColl);
    return objColl;
  }

  /**
   * Save the list of entity intances.
   *
   * @param objColl
   * @return
   */
  public Collection<?> persist(Collection<?> objColl, Mutator<byte[]> m) {
    if (null == objColl) {
      throw new IllegalArgumentException("object to save cannot be null");
    }

    objMapper.saveObjCollection(keyspace, objColl, m);
    return objColl;
  }

  /**
   * Save the entity instance.
   *
   * @param obj
   * @return
   */
  @Override
  public void persist(Object obj) {
    if (null == obj) {
      throw new IllegalArgumentException("object to save cannot be null");
    }
    objMapper.saveObj(keyspace, obj);
  }

  @Override
  public <T> T merge(T t) {
    return objMapper.saveObj(keyspace, t);
  }

  @Override
  public void remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
    return find(entityClass, primaryKey);
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
    return find(entityClass, primaryKey);
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
    return find(entityClass, primaryKey);
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    return find(entityClass, primaryKey);
  }

  @Override
  public void flush() {
    // no-op
  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    // no-op
  }

  @Override
  public FlushModeType getFlushMode() {
    return FlushModeType.AUTO;
  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    // no-op
  }

  @Override
  public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    // no-op
  }

  @Override
  public void refresh(Object entity) {
    // no-op
  }

  @Override
  public void refresh(Object entity, Map<String, Object> properties) {
    refresh(entity);
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode) {
    refresh(entity);
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    refresh(entity);
  }

  @Override
  public void clear() {
    // no-op
  }

  @Override
  public void detach(Object entity) {
    // no-op
  }

  @Override
  public boolean contains(Object entity) {
    return false;
  }

  @Override
  public LockModeType getLockMode(Object entity) {
    return LockModeType.NONE;
  }

  @Override
  public void setProperty(String propertyName, Object value) {
    // no-op
  }

  @Override
  public Map<String, Object> getProperties() {
    return Collections.emptyMap();
  }

  @Override
  public Query createQuery(String qlString) {
    return null;
  }

  @Override
  public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
    return null;
  }

  @Override
  public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
    return null;
  }

  @Override
  public Query createNamedQuery(String name) {
    return null;
  }

  @Override
  public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {
    return null;
  }

  @Override
  public void joinTransaction() {
    // no-op
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    return null;
  }

  @Override
  public Object getDelegate() {
    return this;
  }

  @Override
  public void close() {
    // no-op
  }

  @Override
  public boolean isOpen() {
    return true;
  }

  @Override
  public EntityTransaction getTransaction() {
    return null;
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    return emf;
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return null;
  }

  @Override
  public Metamodel getMetamodel() {
    return null;
  }
}
