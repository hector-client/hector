package me.prettyprint.cassandra.service;

import java.util.Map;


import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.service.ConsistencyLevel;
import org.apache.thrift.TException;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class KeyspaceFactory {

  private final CassandraClientMonitor clientMonitor;

  public KeyspaceFactory(CassandraClientMonitor clientMonitor) {
    this.clientMonitor = clientMonitor;
  }

  public Keyspace create(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPools)
      throws TException {
    return new KeyspaceImpl(client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, clientMonitor);
  }
}
