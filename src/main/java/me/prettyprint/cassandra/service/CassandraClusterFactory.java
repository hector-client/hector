package me.prettyprint.cassandra.service;

import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

public class CassandraClusterFactory {

  private final CassandraClient cassandraClient;
  
  public CassandraClusterFactory(CassandraClient cassandraClient) {
    this.cassandraClient = cassandraClient;
  }
  
  public CassandraCluster create() throws TTransportException, TException, UnknownHostException {
    return new CassandraClusterImpl(cassandraClient);
  }
  
}
