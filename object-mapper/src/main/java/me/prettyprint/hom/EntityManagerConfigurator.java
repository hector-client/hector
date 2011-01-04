package me.prettyprint.hom;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

/**
 * Config wrapper around the properties map required in the JPA
 * specification
 * 
 * @author zznate
 *
 */
public class EntityManagerConfigurator {

  public static final String PROP_PREFIX = "me.prettyprint.hom.";
  public static final String CLASSPATH_PREFIX_PROP = PROP_PREFIX + "classpathPrefix";
  public static final String CLUSTER_NAME_PROP = PROP_PREFIX + "clusterName";
  public static final String KEYSPACE_PROP = PROP_PREFIX + "keyspace";
  public static final String HOST_LIST_PROP = PROP_PREFIX + "hostList";
  
  private final String classpathPrefix;
  private final String clusterName;
  private final String keyspace;
  private CassandraHostConfigurator cassandraHostConfigurator;
  
  
  /**
   * Construct an EntityManagerConfigurator to extract the propeties related
   * to entity management
   * @param properties
   */
  public EntityManagerConfigurator(Map<String, Object> properties) {
    this(properties, null);        
  }
  
  /**
   * Same as single argument version, but allows for (nullable) 
   * {@link CassandraHostConfigurator} to be provided explicitly
   * 
   * @param properties
   * @param cassandraHostConfigurator
   */
  public EntityManagerConfigurator(Map<String, Object> properties, 
      CassandraHostConfigurator cassandraHostConfigurator) {
    classpathPrefix = getPropertyGently(properties, CLASSPATH_PREFIX_PROP,true);
    clusterName = getPropertyGently(properties, CLUSTER_NAME_PROP,true);
    keyspace = getPropertyGently(properties, KEYSPACE_PROP,true);
    if ( cassandraHostConfigurator == null ) {
      String hostList = getPropertyGently(properties, HOST_LIST_PROP, false);
      if ( StringUtils.isNotBlank(hostList) ) {
        cassandraHostConfigurator = new CassandraHostConfigurator(hostList);
      } else {
        cassandraHostConfigurator = new CassandraHostConfigurator();
      }
    }
    this.cassandraHostConfigurator = cassandraHostConfigurator;
  }
  
  
  public static String getPropertyGently(Map<String, Object> props, String key, boolean throwError) {
    if ( props.get(key) != null ) {
      return props.get(key).toString();
    }
    if ( throwError )
      throw new IllegalArgumentException(String.format("The configuration property '%s' cannot be null.", key));
    return null;
  }

  public String getClasspathPrefix() {
    return classpathPrefix;
  }

  public String getClusterName() {
    return clusterName;
  }

  public String getKeyspace() {
    return keyspace;
  }

  public CassandraHostConfigurator getCassandraHostConfigurator() {
    return cassandraHostConfigurator;
  }
    
}
