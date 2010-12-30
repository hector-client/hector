package me.prettyprint.hom;

import java.util.Map;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public class EntityManagerConfigurator {

  public static final String PROP_PREFIX = "hector.hom.";
  public static final String CLASSPATH_PREFIX_PROP = PROP_PREFIX + "classpathPrefix";
  public static final String CLUSTER_NAME_PROP = PROP_PREFIX + "clusterName";
  public static final String KEYSPACE_PROP = PROP_PREFIX + "keyspace";
  
  public static CassandraHostConfigurator buildFromProps(Map<String,Object> properties) {
    
    return new CassandraHostConfigurator();
  }
  
  
  
}
