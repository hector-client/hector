package me.prettyprint.cassandra.model;

/**
 * 
 * @author Ran Tavory
 *
 * @param <T> The type of the result. May be for example Column of SuperColumn
 */
public interface Result<T> extends ExecutionResult {


  /**
   * @return the actual value returned from the query.
   */
  T get();
  
  
  /**
   * @return the query used to create this result
   */
  Query<T> getQuery();
  
}
