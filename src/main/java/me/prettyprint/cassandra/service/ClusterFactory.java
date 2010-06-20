package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public class ClusterFactory {

  private static final Map<String, Cluster> clusters = new HashMap<String, Cluster>();
  
  public static Cluster get(String clusterName) {
    return clusters.get(clusterName);
  }
  /**
   * 
   * @param clusterName The cluster name. This is an identifying string for the cluster, e.g. 
   * "production" or "test" etc. Clusters will be created on demand per each unique clusterName key.
   * @param hostIp host:ip format string
   * @return
   */
  public static Cluster getOrCreate(String clusterName, String hostIp) {
    /*
     I would like to move off of string literals for hosts, perhaps
     providing them for convinience, and used specific types
      
     */
    return getOrCreate(clusterName, new CassandraHostConfigurator(hostIp));
  }

  public static Cluster getOrCreate(String clusterName, 
      CassandraHostConfigurator cassandraHostConfigurator) {
    Cluster c = clusters.get(clusterName);
    if (c == null) {
      synchronized (clusters) {
        c = clusters.get(clusterName);
        if (c == null) {
          c = create(clusterName, cassandraHostConfigurator);
          clusters.put(clusterName, c);
        }
      }
    }
    return c;
  }
  
  public static Cluster create(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    return new ClusterImpl(clusterName, cassandraHostConfigurator);
  }
}
