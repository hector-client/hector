package me.prettyprint.cassandra.model;

import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.thrift.TException;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class KeyspaceFactory {

  /**
   *
   * @param client
   * @param keyspaceName
   * @param keyspaceDesc
   * @param consistencyLevel
   * @param failoverPolicy
   * @param clientPool A pool that may be used to obtain new clients in case the current client
   *  fails
   * @return
   * @throws TException
   */
  public Keyspace create(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPool)
      throws TException {

    CassandraClientMonitor monitor = null;
    monitor = JmxMonitor.INSTANCE.getCassandraMonitor(client);
    return new KeyspaceImpl(client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPool, monitor);
  }
}
