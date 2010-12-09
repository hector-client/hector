package me.prettyprint.hector.api.mutation;

import me.prettyprint.cassandra.service.CassandraHost;

/**
 * Result from a mutation.
 *
 * @author Ran Tavory
 *
 */
public interface MutationResult {

  public long getExecutionTimeMicro();

  public CassandraHost getHostUsed();
}