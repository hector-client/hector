package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Cluster;

public class KeyspaceOperatorFactory {

  public static KeyspaceOperator create(String keyspace, Cluster cluster) {
    return new KeyspaceOperatorImpl(keyspace, cluster);
  }
}
