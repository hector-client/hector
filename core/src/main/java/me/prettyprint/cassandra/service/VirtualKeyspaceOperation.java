package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Cassandra.Client;

public class VirtualKeyspaceOperation<T> extends Operation<T> {

  Operation<T> operation;
  ByteBuffer prefixBytes;

  public VirtualKeyspaceOperation(Operation<T> operation, ByteBuffer prefixBytes) {
    super(operation.operationType, operation.failoverPolicy,
        operation.keyspaceName, operation.credentials);
    this.operation = operation;
    this.prefixBytes = prefixBytes;
  }

  @Override
  public void applyConnectionParams(String keyspace,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    operation.applyConnectionParams(keyspace, consistencyLevelPolicy,
        failoverPolicy, credentials);
  }

  @Override
  public void setResult(T executionResult) {
    operation.setResult(executionResult);
  }

  @Override
  public T getResult() {
    return operation.getResult();
  }

  @Override
  public ExecutionResult<T> getExecutionResult() {
    return operation.getExecutionResult();
  }

  @Override
  public void executeAndSetResult(Client cassandra, CassandraHost cassandraHost)
      throws Exception {
    operation.executeAndSetResult(new VirtualKeyspaceCassandraClient(cassandra, prefixBytes), cassandraHost);
  }

  @Override
  public void setException(HectorException e) {
    operation.setException(e);
  }

  @Override
  public boolean hasException() {
    return operation.hasException();
  }

  @Override
  public HectorException getException() {
    return operation.getException();
  }

  @Override
  public CassandraHost getCassandraHost() {
    return operation.getCassandraHost();
  }

  @Override
  public T execute(Client cassandra) throws Exception {
    return operation.execute(new VirtualKeyspaceCassandraClient(cassandra,
        prefixBytes));
  }

}
