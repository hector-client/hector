package me.prettyprint.cassandra.service;

import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

/**
 * Controls access to CassandraCluster, as we need a live CassandraClient 
 * in order to do anything useful
 * 
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
public enum CassandraClusterFactory {
  
  INSTANCE;
  
  public static CassandraClusterFactory getInstance() {
    return INSTANCE;
  }
    
  
  private CassandraClusterFactory() {
  }
  
  public CassandraCluster create(CassandraClient cassandraClient) throws TTransportException, TException, UnknownHostException {
    return new CassandraClusterImpl(cassandraClient);
  }
  
}
