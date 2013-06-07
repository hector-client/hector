package me.prettyprint.cassandra.connection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;

/**
 * Default interface for all load balancing policies.
 *
 */
public interface LoadBalancingPolicy extends Serializable {

  /**
   * Retrieves a pool from the collection of <code>pools</code> excluding <code>excludeHosts</code>.
   * 
   * @param pools collection of all available pools
   * @param excludeHosts excluded pools
   * @return a pool based on this load balancing policy
   */
  HClientPool getPool(Collection<HClientPool> pools, Set<CassandraHost> excludeHosts);

  /**
   * Creates a connection pool for <code>host</code>.
   * 
   * @param clientFactory an instance of {@link HClientFactory}
   * @param host an instance of {@link CassandraHost} representing the host this pool will represent 
   * @param monitor the monitor exposing JMX methods 
   * @return a connection pool
   */
  HClientPool createConnection(HClientFactory clientFactory, CassandraHost host, CassandraClientMonitor monitor);
}
