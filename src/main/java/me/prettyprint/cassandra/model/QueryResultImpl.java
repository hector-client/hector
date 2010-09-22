package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.query.Query;
import me.prettyprint.hector.api.query.QueryResult;

/**
 *
 * @author Ran Tavory
 *
 * @param <T> The type of the result. May be for example Column of SuperColumn
 */
public final class QueryResultImpl<T> extends ExecutionResult<T> implements QueryResult<T> {

  private final Query<T> query;

  public QueryResultImpl(ExecutionResult<T> res, Query<T> query) {
    super(res.get(), res.getExecutionTimeMicro(), res.getHostUsed());
    this.query = query;
  }

  /**
   * @return the query used to create this result
   */
  public Query<T> getQuery() {
    return query;
  }


  @Override
  public String toString() {
    return formatMessage("Result", query.toString());
  }
}
