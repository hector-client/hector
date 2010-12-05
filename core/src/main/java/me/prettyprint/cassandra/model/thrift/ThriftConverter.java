package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.hector.api.HConsistencyLevel;

import org.apache.cassandra.thrift.ConsistencyLevel;

/**
 * @author: peter
 * @author ran
 */
public class ThriftConverter {

  public static ConsistencyLevel consistencyLevel(HConsistencyLevel c) {
    switch (c) {
    case ALL:
      return ConsistencyLevel.ALL;
    case ANY:
      return ConsistencyLevel.ANY;
    case ONE:
      return ConsistencyLevel.ONE;
    case QUORUM:
      return ConsistencyLevel.QUORUM;
    case EACH_QUORUM:
      return ConsistencyLevel.EACH_QUORUM;
    case LOCAL_QUORUM:
      return ConsistencyLevel.LOCAL_QUORUM;
    default:
      throw new RuntimeException("Unregornized consistency level " + c);
    }
  }
}
