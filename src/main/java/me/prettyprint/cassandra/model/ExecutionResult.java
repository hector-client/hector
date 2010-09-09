package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.CassandraHost;


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
  private final CassandraHost cassandraHost;
  
  protected static final String BASE_MSG_FORMAT = "%s took (%dus) for query (%s) on host: %s";

  public ExecutionResult(T value, long execTime, CassandraHost cassandraHost) {
    this.value = value;
    this.execTime = execTime;
    this.cassandraHost = cassandraHost;
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
    return formatMessage("ExecutionResult", "n/a");
  }
  
  protected String formatMessage(String resultName, String query) {
    return String.format(BASE_MSG_FORMAT, resultName, execTime, query, (cassandraHost != null ? cassandraHost.getName() : "[none]"));
  }

  /** The cassandra host that was actually used to execute the operation */
  public CassandraHost getHostUsed() {
    return this.cassandraHost;
  }


}
