package me.prettyprint.cassandra.model;

/**
 * 
 * @author Ran Tavory
 *
 * @param <T> The type of the result. May be for example Column of SuperColumn
 */
public interface Result<T> extends ExecutionResult {


  // maybe: <T> T asType(Class<T> type);
  T get();
  
  Query<T> getQuery();
  
}
