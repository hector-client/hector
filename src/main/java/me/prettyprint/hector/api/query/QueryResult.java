package me.prettyprint.hector.api.query;

import me.prettyprint.cassandra.service.CassandraHost;

/**
 * Return type from queries execution.
 *
 * @author Ran Tavory
 *
 * @param <T> The type of the result. May be for example Column of SuperColumn
 */

public interface QueryResult<T> {

  /**
   * Get the result value.
   * @return
   */
  T get();

  long getExecutionTimeMicro();

  Query<T> getQuery();

  CassandraHost getHostUsed();
}