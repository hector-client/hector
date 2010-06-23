package me.prettyprint.cassandra.model;

/**
 * 
 * @author Ran Tavory
 *
 * @param <T> The type of the result. May be for example Column of SuperColumn
 */
public class Result<T> implements ExecutionResult {

  private T t;
  public Result(T t) {
    // TODO Auto-generated constructor stub
  }

  /**
   * @return the actual value returned from the query.
   */
  public T get() {
    return t;    
  }
  
  
  /**
   * @return the query used to create this result
   */
  public Query<T> getQuery() {
    // TODO Auto-generated method stub
    return null;
  }
  


  @Override
  public boolean isSuccess() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public long getExecutionTimeMili() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getHostUsed() {
    // TODO Auto-generated method stub
    return null;
  }
}
