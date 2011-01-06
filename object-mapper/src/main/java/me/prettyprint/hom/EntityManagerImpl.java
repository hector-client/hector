package me.prettyprint.hom;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hom.annotations.AnnotationScanner;

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
   * {@link Table} annotation to determine the Cassandra column family name.
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

    CFMappingDef<T, I> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
    if (null == cfMapDef) {
      throw new IllegalStateException("No class annotated with @Entity for type, " + clazz.getName());
    }

    return objMapper.getObject(keyspace, cfMapDef.getColFamName(), clazz, id);
  }

  /**
   * Load an entity instance given the raw column slice. This is a stop gap
   * solution for instanting objects using entity manager while iterating over
   * rows.
   * 
   * @param <T>
   *            The type of entity to load for compile time type checking
   * @param <I>
   *            Type of the entity's ID
   * @param clazz
   *            The type of entity to load for runtime instance creation
   * @param id
   *            ID of the instance to load
   * @param rowSlice
   *            Raw row slice as returned from Hector API, of the type
   *            <code>ColumnSlice<String, byte[]></code>
   * @return
   */
  public <T, I> T load(Class<T> clazz, I id, ColumnSlice<String, byte[]> colSlice) {
    if (null == clazz) {
      throw new IllegalArgumentException("clazz cannot be null");
    }
    if (null == id) {
      throw new IllegalArgumentException("id cannot be null");
    }

    CFMappingDef<T, I> cfMapDef = cacheMgr.getCfMapDef(clazz, false);
    if (null == cfMapDef) {
      throw new IllegalStateException("No class annotated with @Entity for type, " + clazz.getName());
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
    // TODO Auto-generated method stub
    
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean contains(Object entity) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Query createNamedQuery(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    // TODO Auto-generated method stub
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createQuery(String qlString) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public void flush() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Object getDelegate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public FlushModeType getFlushMode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EntityTransaction getTransaction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isOpen() {    
    return open;
  }

  @Override
  public void joinTransaction() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public <T> T merge(T entity) {
    // TODO Auto-generated method stub
    return null;
  }

  
  @Override
  public void refresh(Object entity) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void remove(Object entity) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    // TODO Auto-generated method stub
    
  }
}
