package me.prettyprint.cassandra.model;

import org.apache.cassandra.thrift.ConsistencyLevel;

/**
 * A simple implementation of {@link ConsistencyLevelPolicy} which returns QUORUM as the desired
 * consistency level for both reads and writes.
 * 
 * @author Ran Tavory
 *
 */
public class QuorumAllConsistencyLevelPolicy implements ConsistencyLevelPolicy {

  @Override
  public ConsistencyLevel get(OperationType op) {
    return ConsistencyLevel.QUORUM;
  }

  @Override
  public ConsistencyLevel get(OperationType op, String cfName) {
    return ConsistencyLevel.QUORUM;
  }

}
