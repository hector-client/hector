package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.CassandraHost;

public interface Cluster {
  
  List<CassandraHost> getKnownHosts();
  
  void addHost(CassandraHost cassandraHost);
  
  // rest of the methods from the current CassandraCluster
  
}
