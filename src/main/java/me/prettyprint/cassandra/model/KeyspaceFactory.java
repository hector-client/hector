package me.prettyprint.cassandra.model;

import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class KeyspaceFactory {

  public Keyspace create(CassandraClient cassandraClient, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel) {
    return new KeyspaceImpl(cassandraClient, keyspaceName, keyspaceDesc, consistencyLevel);
  }
}
