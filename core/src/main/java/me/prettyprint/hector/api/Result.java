package me.prettyprint.hector.api;

public interface Result<T> {

  /**
   * @return the actual value returned from the query (or null if it was a mutation that doesn't
   * return a value)
   */
  T get();

  long getExecutionTimeMicro();
}
