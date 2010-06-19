package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public class ClusterFactory {

  
  public static Cluster create(String hostIp) {
    /*
     I would like to move off of string literals for hosts, perhaps
     providing them for convinience, and used specific types
      
     */
    return create(new CassandraHostConfigurator(hostIp));
  }

  public static Cluster create(CassandraHostConfigurator cassandraHostConfigurator) {
    return null;
  }
}
