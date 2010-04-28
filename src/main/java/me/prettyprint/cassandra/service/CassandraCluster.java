package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

/**
 * A class to encapsulate the "Meta-API" portion of the thrift API, the definitions 
 * historically at bottom of the cassandra.thrift file
 * 
 * @author Nate McCall (nate@vervewireless.com)
 */
public interface CassandraCluster {
  
  /**
   * Returns a Set of Strings listing the available keyspaces. This includes 
   * the system keyspace. 
   */
  Set<String> describeKeyspaces() throws TTransportException, TException;
  
  /**
   * Returns the name of the cluster as defined by the ClusterName configuration
   * element in the configuration file   
   */
  String describeClusterName() throws TTransportException, TException;
  
  /**
   * Returns the Thrift API version. Note: this is not the version of Cassandra, but 
   * the underlying Thrift API
   */
  String describeThriftVersion() throws TTransportException, TException;
  
  /**
   * Describe the structure of the ring for a given Keyspace
   * 
   */
  List<TokenRange> describeRing(Keyspace keyspace) throws TTransportException, TException; 
  
  /**
   * Return a Set of hostnames for this cluster
   */
  Set<String> getHostNames() throws TTransportException, TException;
  
  /**
   * Describe the given keyspace. The key for the outer map is the ColumnFamily name.
   * The inner map contains configuration properties mapped to their values. 
   */
  Map<String, Map<String, String>> describeKeyspace(Keyspace keyspace) throws TTransportException, TException, NotFoundException;

}
