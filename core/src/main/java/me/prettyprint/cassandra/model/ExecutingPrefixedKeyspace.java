package me.prettyprint.cassandra.model;

import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.service.PrefixedKeyspaceServiceImpl;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;

public class ExecutingPrefixedKeyspace<E> extends ExecutingKeyspace {

  E keyPrefix;
  Serializer<E> keyPrefixSerializer;

  public ExecutingPrefixedKeyspace(String keyspace, E keyPrefix,
      Serializer<E> keyPrefixSerializer, HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy) {
    super(keyspace, connectionManager, consistencyLevelPolicy, failoverPolicy);

    this.keyPrefix = keyPrefix;
    this.keyPrefixSerializer = keyPrefixSerializer;
  }

  public ExecutingPrefixedKeyspace(String keyspace, E keyPrefix,
      Serializer<E> keyPrefixSerializer, HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    super(keyspace, connectionManager, consistencyLevelPolicy, failoverPolicy,
        credentials);

    this.keyPrefix = keyPrefix;
    this.keyPrefixSerializer = keyPrefixSerializer;
  }

  @Override
  public <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc)
      throws HectorException {
    KeyspaceService ks = null;
    try {
      ks = new PrefixedKeyspaceServiceImpl(keyspace, keyPrefix,
          keyPrefixSerializer, consistencyLevelPolicy, connectionManager,
          failoverPolicy, credentials);
      return koc.doInKeyspaceAndMeasure(ks);
    } finally {
      if (ks != null) {
        // connectionManager.releaseClient(ks.getClient());
      }
    }
  }

}
