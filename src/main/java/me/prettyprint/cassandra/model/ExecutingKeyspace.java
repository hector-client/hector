package me.prettyprint.cassandra.model;

import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.service.KeyspaceServiceImpl;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * Thread Safe
 * @author Ran Tavory
 *
 */
public class ExecutingKeyspace implements Keyspace {

  private ConsistencyLevelPolicy consistencyLevelPolicy;
  private FailoverPolicy failoverPolicy;

  private final HConnectionManager connectionManager;
  private final String keyspace;
  private final Map<String, String> credentials;

  public ExecutingKeyspace(String keyspace, HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy, FailoverPolicy failoverPolicy, 
      Map<String, String> credentials) {
    Assert.noneNull(keyspace, consistencyLevelPolicy, connectionManager);
    this.keyspace = keyspace;
    this.connectionManager = connectionManager;
    this.consistencyLevelPolicy = consistencyLevelPolicy;
    this.failoverPolicy = failoverPolicy;
    this.credentials = credentials;
  }

  @Override
  public void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp) {
    this.consistencyLevelPolicy = cp;
  }

  @Override
  public String toString() {
    return "ExecutingKeyspace(" + keyspace +"," + connectionManager + ")";
  }

  @Override
  public long createClock() {
    return connectionManager.createClock();
  }

  public <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc) throws HectorException {
    KeyspaceService ks = null;
    try {
      ks = new KeyspaceServiceImpl(keyspace, consistencyLevelPolicy, connectionManager, failoverPolicy, credentials);
      return koc.doInKeyspaceAndMeasure(ks);
    } finally {
      if (ks != null) {
        //connectionManager.releaseClient(ks.getClient());
      }
    }
  }
}
