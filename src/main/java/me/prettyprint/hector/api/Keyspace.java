package me.prettyprint.hector.api;

import me.prettyprint.cassandra.service.Cluster;

/**
 *
 * @author Ran Tavory
 *
 */
public interface Keyspace {

  void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp);

  Cluster getCluster();

  long createTimestamp();

}