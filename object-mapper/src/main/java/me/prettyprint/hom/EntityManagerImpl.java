package me.prettyprint.hom;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hom.annotations.AnnotationScanner;
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
  private boolean open;

  public EntityManagerImpl(Keyspace keyspace, String classpathPrefix) {
    this(keyspace, new String[] {
        classpathPrefix }, null, null);
  }

  public EntityManagerImpl(Keyspace keyspace, String[] classpathPrefix) {
    this(keyspace, classpathPrefix, null, null);
  }

  public EntityManagerImpl(Keyspace keyspace, String[] classpathPrefix, ClassCacheMgr cacheMgr,
      HectorObjectMapper objMapper) {
    this.keyspace = keyspace;

    if (null != cacheMgr) {
      this.cacheMgr = cacheMgr;
    }
    else {
      this.cacheMgr = new ClassCacheMgr();
    }

    if (null != objMapper) {
      this.objMapper = objMapper;
    }
    else {
      this.objMapper = new HectorObjectMapper(this.cacheMgr);
    }

    initialize(classpathPrefix);
  }

  /**
   * Initialize the manager by scanning the classpath starting with the
   * <code>classpathPrefix</code>, looking for classes annotated with
   * {@link Entity}. If an Entity class is found, it looks for the
   * {@link BasicTable} annotation to determine the Cassandra column family name.
   *
   * @param classpathPrefixArr
   */
  public void initialize(String[] classpathPrefixArr) {
    if (null != classpathPrefixArr && 0 < classpathPrefixArr.length) {
      for (String classpathPrefix : classpathPrefixArr) {
        initializeClasspath(classpathPrefix);
      }
    }
    if ( logger.isDebugEnabled()) {
      logger.debug("classpath array has {} items : {}",
           (null != classpathPrefixArr ? classpathPrefixArr.length : "0"), classpathPrefixArr);
    }
    open = true;
  }

  private void initializeClasspath(String classpathPrefix) {
    AnnotationScanner scanner = new AnnotationScanner();
    Set<Class<?>> classSet = scanner.scan(classpathPrefix, Entity.class);
    for (Class<?> clazz : classSet) {
      cacheMgr.initializeCacheForClass(clazz);
    }
  }

  /**
   * Load an entity instance. If the ID does not map to a persisted entity,
   * then null is returned.
   *
   * @param <T>
   *            The type of entity to load for compile time type checking
   * @param <I>
   *            Type of the entity's ID
   * @param clazz
   *            The type of entity to load for runtime instance creation
   * @param id
   *            ID of the instance to load
   * @return instance of Entity or null if can't be found
   */
  public <T, I> T load(Class<T> clazz, I id) {
    if (null == clazz) {
      throw new IllegalArgumentException("clazz cannot be null");
    }
    if (null == id) {
      throw new IllegalArgumentException("id cannot be null");
    }

    CFMappingDef<T> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
    if (null == cfMapDef) {
      throw new HectorObjectMapperException("No class annotated with @" + Entity.class.getSimpleName() + " for type, " + clazz.getName());
    }

    return (T)objMapper.getObject(keyspace, cfMapDef.getEffectiveColFamName(), id);
  }
  
  public <T, I> Map<I, T> load(Class<T> clazz, Collection<I> ids) {
	  if (null == clazz) {
		  throw new IllegalArgumentException("clazz cannot be null");
	  }
	  if (null == ids) {
		  throw new IllegalArgumentException("ids cannot be null");
	  }

	  CFMappingDef<T> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
	  if (null == cfMapDef) {
		  throw new HectorObjectMapperException("No class annotated with @" + Entity.class.getSimpleName() + " for type, " + clazz.getName());
	  }

	  Set<I> idSet = new HashSet<I>(ids.size(), 1);
	  idSet.addAll(ids);

	  return (Map<I, T>) objMapper.getObjects(keyspace, cfMapDef.getEffectiveColFamName(), idSet);
  }


  /**
   * Load an entity instance given the raw column slice. This is a stop gap
   * solution for instanting objects using entity manager while iterating over
   * rows.
   *
   * @param <T>
   *            The type of entity to load for compile time type checking
   * @param clazz
   *            The type of entity to load for runtime instance creation
   * @param id
   *            ID of the instance to load
   * @param colSlice
   *            Raw row slice as returned from Hector API, of the type
   *            <code>ColumnSlice<String, byte[]></code>
   * @return Completely instantiated persisted object
   */
  public <T> T load(Class<T> clazz, Object id, ColumnSlice<String, byte[]> colSlice) {
    if (null == clazz) {
      throw new IllegalArgumentException("clazz cannot be null");
    }
    if (null == id) {
      throw new IllegalArgumentException("id cannot be null");
    }

    CFMappingDef<T> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
    if (null == cfMapDef) {
      throw new HectorObjectMapperException("No class annotated with @" + Entity.class.getSimpleName() + " for type, " + clazz.getName());
    }

    T obj = objMapper.createObject(cfMapDef, id, colSlice);
    return obj;
  }

  /**
   * Save the entity instance.
   *
   * @param <T>
   * @param obj
   * @return
   */
  public <T> T save(T obj) {
    if (null == obj) {
      throw new IllegalArgumentException("object to save cannot be null");
    }
    return objMapper.saveObj(keyspace, obj);
  }

  @Override
  public void persist(Object entity) {
    save(entity);
  }


  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {
    return load(entityClass, primaryKey);
  }


  @Override
  public void clear() {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void close() {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public boolean contains(Object entity) {
    throw new RuntimeException("Method is not implemented");
//    return false;
  }

  @Override
  public Query createNamedQuery(String name) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public Query createQuery(String qlString) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }


  @Override
  public void flush() {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public Object getDelegate() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public FlushModeType getFlushMode() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public EntityTransaction getTransaction() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public boolean isOpen() {
    return open;
  }

  @Override
  public void joinTransaction() {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public <T> T merge(T entity) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }


  @Override
  public void refresh(Object entity) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void remove(Object entity) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public void detach(Object arg0) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2,
      Map<String, Object> arg3) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public LockModeType getLockMode(Object arg0) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public Metamodel getMetamodel() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public Map<String, Object> getProperties() {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }

  @Override
  public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void refresh(Object arg0, Map<String, Object> arg1) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void refresh(Object arg0, LockModeType arg1) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public void setProperty(String arg0, Object arg1) {
    throw new RuntimeException("Method is not implemented");

  }

  @Override
  public <T> T unwrap(Class<T> arg0) {
    throw new RuntimeException("Method is not implemented");
//    return null;
  }
}
