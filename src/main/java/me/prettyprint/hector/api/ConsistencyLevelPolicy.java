package me.prettyprint.hector.api;

import me.prettyprint.cassandra.service.OperationType;

import org.apache.cassandra.thrift.ConsistencyLevel;

public interface ConsistencyLevelPolicy {
  
  /**
   * Get the desired consistency level according to the operation type.
   * @param op
   * @return
   */
  ConsistencyLevel get(OperationType op);

  /**
   * Get desired consistency according to the operation type and column family name. 
   * @param op
   * @param cfName
   * @return
   */
  ConsistencyLevel get(OperationType op, String cfName);

}
