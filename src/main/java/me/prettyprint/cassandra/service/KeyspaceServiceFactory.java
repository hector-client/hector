package me.prettyprint.cassandra.service;

import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.exceptions.HectorTransportException;


/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class KeyspaceServiceFactory {

  private final CassandraClientMonitor clientMonitor;

  public KeyspaceServiceFactory(CassandraClientMonitor clientMonitor) {
    this.clientMonitor = clientMonitor;
  }

  public KeyspaceService create(String keyspaceName,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager,
      FailoverPolicy failoverPolicy)
      throws HectorTransportException {
    return new KeyspaceServiceImpl(keyspaceName, consistencyLevel,
        connectionManager, failoverPolicy);
  }
  public KeyspaceService create(String keyspaceName,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager,
      FailoverPolicy failoverPolicy,
      Map<String, String> credentials)
      throws HectorTransportException {
    return new KeyspaceServiceImpl(keyspaceName, consistencyLevel,
        connectionManager, failoverPolicy, credentials);
  }
}
