package me.prettyprint.cassandra.model;

import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraClientPoolStore;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.thrift.TException;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class KeyspaceFactory {

  public Keyspace create(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPoolStore clientPools)
      throws TException {
    CassandraClientMonitor monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    return new KeyspaceImpl(client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);
  }
}
