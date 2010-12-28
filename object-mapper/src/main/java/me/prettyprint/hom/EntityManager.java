package me.prettyprint.hom;

import java.util.Set;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hom.annotations.AnnotationScanner;
import me.prettyprint.hom.annotations.Entity;
import me.prettyprint.hom.annotations.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Handles entity management by scanning classpath for classes annotated with
 * {@link Entity} and providing basic load and save methods.
 * 
 * @author
 */
public class EntityManager {
  private static Logger logger = LoggerFactory.getLogger(EntityManager.class);

  private Keyspace keyspace;
  private HectorObjectMapper objMapper;
  private ClassCacheMgr cacheMgr;

  public EntityManager(Keyspace keyspace, String classpathPrefix) {
    this(keyspace, new String[] {
        classpathPrefix }, null, null);
  }

  public EntityManager(Keyspace keyspace, String[] classpathPrefix) {
    this(keyspace, classpathPrefix, null, null);
  }

  public EntityManager(Keyspace keyspace, String[] classpathPrefix, ClassCacheMgr cacheMgr,
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
      logger.debug("classpath array has " + classpathPrefixArr.length + " packages");
      for (String classpathPrefix : classpathPrefixArr) {
        logger.debug("classpath array : " + classpathPrefix);
        initializeClasspath(classpathPrefix);
      }
    }
    else {
      logger.debug("classpath array is null or empty : "
          + (null != classpathPrefixArr ? classpathPrefixArr.length : "null"));
    }
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
}
