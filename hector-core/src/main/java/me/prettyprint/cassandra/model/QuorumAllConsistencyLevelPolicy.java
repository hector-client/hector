package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;

/**
 * A simple implementation of {@link ConsistencyLevelPolicy} which returns QUORUM as the desired
 * consistency level for both reads and writes.
 *
 * @author Ran Tavory
 *
 */
public final class QuorumAllConsistencyLevelPolicy implements ConsistencyLevelPolicy {

  @Override
  public HConsistencyLevel get(OperationType op) {
    return HConsistencyLevel.QUORUM;
  }

  @Override
  public HConsistencyLevel get(OperationType op, String cfName) {
    return HConsistencyLevel.QUORUM;
  }

}
