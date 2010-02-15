package me.prettyprint.cassandra.service;

import java.util.Set;

/**
 * Holds the list of all available pools, keyed by their url:port.
 *
 * This class is a simple multiplexer b/w the different client pools, keyed by url:port.
 *
 * @author Ran Tavory (rantav@gmain.com)
 *
 */
public interface CassandraClientPoolStore {

  /**
   * Gets a pool defined by url:port
   * @param url
   * @param port
   * @return
   */
  CassandraClientPool getPool(String url, int port);

  /**
   * Borrows a client from the pool defined by url:port
   * @param url
   * @param port
   * @return
   */
  CassandraClient borrowClient(String url, int port)
      throws IllegalStateException, PoolExhaustedException, Exception;

  /**
   * Releases a client from the pool it belongs to.
   */
  void releaseClient(CassandraClient client) throws Exception;

  /**
   * Tells all the clients in the pool to update their list of known hosts.
   */
  void updateKnownHosts();

  Set<String> getExhaustedPoolNames();

  Set<String> getPoolNames();

  int getNumPools();

  int getNumIdle();

  int getNumExhaustedPools();

  int getNumBlockedThreads();

  int getNumActive();

  Set<String> getKnownHosts();
}
