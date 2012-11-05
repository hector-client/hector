package me.prettyprint.cassandra.service;

import java.util.Collections;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Cassandra;

/**
 * Defines an operation performed on cassandra
 *
 * @param <T>
 *          The result type of the operation (if it has a result), such as the
 *          result of get_count or get_column
 *
 *          Oh closures, how I wish you were here...
 */
public abstract class Operation<T> {
  /** Counts failed attempts */
  public final Counter failCounter;

  /** The stopwatch used to measure operation performance */
  public final String stopWatchTagName;

  public FailoverPolicy failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
  public ConsistencyLevelPolicy consistencyLevelPolicy;
  
  public String keyspaceName;
  

  public Map<String, String> credentials;
  
  protected T result;
  private HectorException exception;
  private CassandraHost cassandraHost;
  protected long execTime;
  public final OperationType operationType;
  
  public Operation(OperationType operationType) {
    this.failCounter = (operationType == OperationType.READ) ? Counter.READ_FAIL :
      Counter.WRITE_FAIL;
    this.operationType = operationType;
    this.stopWatchTagName = operationType.name();
  }

  public Operation(OperationType operationType, Map<String, String> credentials) {
    this(operationType, FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE, null, credentials);
  }

  public Operation(OperationType operationType, FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    this(operationType, failoverPolicy, null, credentials);
  }
  
  public Operation(OperationType operationType, FailoverPolicy failoverPolicy, String keyspaceName, Map<String, String> credentials) {
    this.failCounter = (operationType == OperationType.READ) ? Counter.READ_FAIL :
      Counter.WRITE_FAIL;
    this.operationType = operationType;
    this.stopWatchTagName = operationType.name();
    this.failoverPolicy = failoverPolicy;
    this.keyspaceName = keyspaceName;
    this.credentials = Collections.unmodifiableMap(credentials);
  }
  
  
  public void applyConnectionParams(String keyspace, ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String,String> credentials) {
    // TODO this is a first step. must be cleaned up.
    this.keyspaceName = keyspace;
    this.consistencyLevelPolicy = consistencyLevelPolicy;
    this.failoverPolicy = failoverPolicy;
    this.credentials = credentials;
  }

  public void setResult(T executionResult) {
    result = executionResult;
  }

  /**
   *
   * @return The result of the operation, if this is an operation that has a
   *         result (such as getColumn etc.
   */
  public T getResult() {
    // TODO remove in favor of getExecutionResult
    return result;
  }
  
  public ExecutionResult<T> getExecutionResult() {
    return new ExecutionResult<T>(result, execTime, cassandraHost);
  }

  /**
   * Performs the operation on the given cassandra instance.
   */
  public abstract T execute(Cassandra.Client cassandra) throws Exception;

  public void executeAndSetResult(Cassandra.Client cassandra, CassandraHost cassandraHost) throws Exception {
    this.cassandraHost = cassandraHost;
    long startTime = System.nanoTime();
    setResult(execute(cassandra));
    execTime = System.nanoTime() - startTime;
  }

  public void setException(HectorException e) {
    exception = e;
  }

  public boolean hasException() {
    return exception != null;
  }

  public HectorException getException() {
    return exception;
  }
  
  public CassandraHost getCassandraHost() {
    return this.cassandraHost;
  }
  
}

