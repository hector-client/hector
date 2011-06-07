package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.connection.HCassandraHost;
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
public interface Operation<T> {
	
  public void setResult(T executionResult);

  /**
   *
   * @return The result of the operation, if this is an operation that has a
   *         result (such as getColumn etc.
   */
  public T getResult();
  
  public ExecutionResult<T> getExecutionResult();

  /**
   * Performs the operation on the given cassandra instance.
   */
  public abstract T execute(Cassandra.Client cassandra) throws HectorException;

  public void executeAndSetResult(Cassandra.Client cassandra, HCassandraHost cassandraHost) throws HectorException;

  public void setException(HectorException e);

  public boolean hasException();

  public HectorException getException();
  
  public HCassandraHost getCassandraHost();
  
}

