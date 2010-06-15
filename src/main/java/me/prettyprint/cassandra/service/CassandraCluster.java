package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Map;
import java.util.Set;


import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.PoolExhaustedException;

import org.apache.cassandra.thrift.TokenRange;

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
  Set<String> describeKeyspaces() throws HectorException;
  
  /**
   * Returns the name of the cluster as defined by the ClusterName configuration
   * element in the configuration file   
   */
  String describeClusterName() throws HectorException;
  
  /**
   * Returns the Thrift API version. Note: this is not the version of Cassandra, but 
   * the underlying Thrift API
   */
  String describeThriftVersion() throws HectorException;
  
  /**
   * Describe the structure of the ring for a given Keyspace
   * 
   */
  List<TokenRange> describeRing(String keyspace) throws HectorException; 
  
  /**
   * Describe the given keyspace. The key for the outer map is the ColumnFamily name.
   * The inner map contains configuration properties mapped to their values. 
   */
  Map<String, Map<String, String>> describeKeyspace(String keyspace) throws HectorException;

  /**
   * Queries the cluster for its name and returns it.
   * @return
   */
  String getClusterName() throws HectorException;

  /**
   * Gets the list of known hosts.
   * This method is not failover-safe. If will fail fast if the contacted host is down. 
   * 
   * @param fresh whether the get  fresh list of hosts or reuse the possibly previous value cached.
   * @return
   * @throws IllegalStateException
   * @throws PoolExhaustedException
   */
  List<String> getKnownHosts(boolean fresh) throws HectorException;

}
