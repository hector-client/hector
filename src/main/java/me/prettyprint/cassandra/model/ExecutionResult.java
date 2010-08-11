package me.prettyprint.cassandra.model;

/**
 * Describes the state of the executed cassandra command.
 * This is a handy call metadata inspector which reports the call's execution time, status,
 * which actual host was connected etc.
 *
 * @author Ran
 *
 */
/*package*/ class ExecutionResult<T> {

  private final T value;
  private final long execTime;

  public ExecutionResult(T value, long execTime) {
    this.value = value;
    this.execTime = execTime;
  }

  /**
   * @return the actual value returned from the query (or null if it was a mutation that doesn't
   * return a value)
   */
  public T get() {
    return value;
  }

  public long getExecutionTimeMicro() {
    return execTime;
  }

  @Override
  public String toString() {
    return "ExecutionResult(" + toStringInternal() + ")";
  }

  protected String toStringInternal() {
    return "" + execTime + "us";
  }

//TODO(ran):  /** The cassandra host that was actually used to execute the operation */
//  String getHostUsed();

  // Rest of meta methods here
}
