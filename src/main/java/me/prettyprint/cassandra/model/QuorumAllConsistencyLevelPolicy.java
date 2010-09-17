package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.ConsistencyLevelPolicy;

import org.apache.cassandra.thrift.ConsistencyLevel;

/**
 * A simple implementation of {@link ConsistencyLevelPolicy} which returns QUORUM as the desired
 * consistency level for both reads and writes.
 *
 * @author Ran Tavory
 *
 */
public final class QuorumAllConsistencyLevelPolicy implements ConsistencyLevelPolicy {

  public ConsistencyLevel get(OperationType op) {
    return ConsistencyLevel.QUORUM;
  }

  public ConsistencyLevel get(OperationType op, String cfName) {
    return ConsistencyLevel.QUORUM;
  }

}
