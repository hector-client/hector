package me.prettyprint.cassandra.service;

import java.util.Set;

import me.prettyprint.cassandra.model.HectorPoolException;

public interface Cluster {
  
  Set<String> getKnownHosts();
  
  void addHost(CassandraHost cassandraHost);
  
  /**
   * Descriptive name of the cluster. 
   * This name is used to identify the cluster.
   * @return
   */
  String getName();
  
  // rest of the methods from the current CassandraCluster
  
  CassandraClient borrowClient() throws HectorPoolException;
  
  void releaseClient();
  
  
}
