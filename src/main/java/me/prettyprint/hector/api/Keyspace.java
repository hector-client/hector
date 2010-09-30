package me.prettyprint.hector.api;


/**
 *
 * @author Ran Tavory
 *
 */
public interface Keyspace {

  void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp);

  Cluster getCluster();

  long createClock();

}