package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.ResultStatus;


/**
 * Describes the state of the executed cassandra command.
 * This is a handy call metadata inspector which reports the call's execution time, status,
 * which actual host was connected etc.
 *
 * @author Ran
 * @author zznate
 */
public class ExecutionResult<T> implements ResultStatus {

  private final T value;
  private final long execTime;
  private final CassandraHost cassandraHost;
  
  protected static final String BASE_MSG_FORMAT = "%s took (%dus) for query (%s) on host: %s";
  private static final int MICRO_DENOM = 1000;

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

  /**
   * @return the execution time, as already recorded, in nanos
   */
  public long getExecutionTimeNano() {
    return execTime;
  }

  /**
   * Execution time is actually recorded in nanos, so we divide this by 1000 
   * make the number more sensible
   * @return
   */
  public long getExecutionTimeMicro() {
    return execTime / MICRO_DENOM;
  }


  @Override
  public String toString() {
    return formatMessage("ExecutionResult", "n/a");
  }
  
  protected String formatMessage(String resultName, String query) {
    return String.format(BASE_MSG_FORMAT, resultName, getExecutionTimeMicro(), query, (cassandraHost != null ? cassandraHost.getName() : "[none]"));
  }

  /** The cassandra host that was actually used to execute the operation */
  public CassandraHost getHostUsed() {
    return this.cassandraHost;
  }


}
