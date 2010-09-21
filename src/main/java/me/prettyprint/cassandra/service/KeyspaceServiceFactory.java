package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.ConsistencyLevel;

import java.util.Map;

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

  public KeyspaceService create(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPools)
      throws HectorTransportException {
    return new KeyspaceServiceImpl(client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, clientMonitor);
  }
}
