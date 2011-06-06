package me.prettyprint.hector.api;


/**
 * Defines the interface for the consistency level policy.
 * Implementations may create their own consistency level policies, such as
 * {@link AllOneConsistencyLevelPolicy} or {@link QuorumAllConsistencyLevelPolicy}
 *
 * @author Ran Tavory
 *
 */
public interface ConsistencyLevelPolicy {

  /**
   * Get the desired consistency level according to the operation type.
   * @param op
   * @return
   */
  HConsistencyLevel get(OperationType op);

  /**
   * Get desired consistency according to the operation type and column family name.
   * @param op
   * @param cfName
   * @return
   */
  HConsistencyLevel get(OperationType op, String cfName);

}
