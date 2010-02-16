package me.prettyprint.cassandra.service;

import java.util.Map;


import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.thrift.TException;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class KeyspaceFactory {

  public Keyspace create(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPools)
      throws TException {
    CassandraClientMonitor monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    return new KeyspaceImpl(client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);
  }
}
