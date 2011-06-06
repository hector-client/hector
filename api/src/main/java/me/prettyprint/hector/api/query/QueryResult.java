package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.ResultStatus;

/**
 * Return type from queries execution.
 *
 * @author Ran Tavory
 * 
 * @param <T> The type of the result. May be for example Column of SuperColumn
 */

public interface QueryResult<T> extends ResultStatus {

  /**
   * Get the result value.
   * @return
   */
  T get();

  Query<T> getQuery();

}