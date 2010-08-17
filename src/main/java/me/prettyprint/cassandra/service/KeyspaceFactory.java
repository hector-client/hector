package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.model.HectorTransportException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KsDef;

import java.util.Map;

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
      KsDef keyspaceDesc, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPools)
      throws HectorTransportException {
    return new KeyspaceImpl(client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, clientMonitor);
  }
}
