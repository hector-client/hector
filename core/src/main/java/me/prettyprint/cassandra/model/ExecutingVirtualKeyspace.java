package me.prettyprint.cassandra.model;

import java.nio.ByteBuffer;
import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.cassandra.service.VirtualKeyspaceOperation;
import me.prettyprint.cassandra.service.VirtualKeyspaceServiceImpl;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;

public class ExecutingVirtualKeyspace<E> extends ExecutingKeyspace {

  E keyPrefix;
  Serializer<E> keyPrefixSerializer;
  ByteBuffer prefixBytes;

  public ExecutingVirtualKeyspace(String keyspace, E keyPrefix,
      Serializer<E> keyPrefixSerializer, HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy) {
    super(keyspace, connectionManager, consistencyLevelPolicy, failoverPolicy);

    this.keyPrefix = keyPrefix;
    this.keyPrefixSerializer = keyPrefixSerializer;
    prefixBytes = keyPrefixSerializer.toByteBuffer(keyPrefix);
  }

  public ExecutingVirtualKeyspace(String keyspace, E keyPrefix,
      Serializer<E> keyPrefixSerializer, HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    super(keyspace, connectionManager, consistencyLevelPolicy, failoverPolicy,
        credentials);

    this.keyPrefix = keyPrefix;
    this.keyPrefixSerializer = keyPrefixSerializer;
    prefixBytes = keyPrefixSerializer.toByteBuffer(keyPrefix);
  }
  
  @Override
  public <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc)
      throws HectorException {
	  return doExecute(koc, null);
  }
  
  @Override
  public <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc,
		  final HConsistencyLevel level) throws HectorException {
    KeyspaceService ks = null;
    try {
      final ConsistencyLevelPolicy policy;
      if(level != null) {
        policy = new ConsistencyLevelPolicy() {
      	
      	@Override
      	public HConsistencyLevel get(OperationType op, String cfName) {
      		return level;
      	}
      	
      	@Override
      	public HConsistencyLevel get(OperationType op) {
      		return level;
      	}
       };
      }
      else {
        policy = consistencyLevelPolicy;
      }
      	  
      ks = new VirtualKeyspaceServiceImpl(keyspace, keyPrefix,
          keyPrefixSerializer, policy, connectionManager,
          failoverPolicy, credentials);
      return koc.doInKeyspaceAndMeasure(ks);
    } finally {
      if (ks != null) {
        // connectionManager.releaseClient(ks.getClient());
      }
    }
  }

  @Override
  public <T> ExecutionResult<T> doExecuteOperation(Operation<T> operation)
      throws HectorException {
    operation.applyConnectionParams(keyspace, consistencyLevelPolicy,
        failoverPolicy, credentials);
    connectionManager.operateWithFailover(new VirtualKeyspaceOperation<T>(
        operation, prefixBytes));
    return operation.getExecutionResult();
  }

}
