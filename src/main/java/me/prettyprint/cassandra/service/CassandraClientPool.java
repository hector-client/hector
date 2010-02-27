package me.prettyprint.cassandra.service;

import java.util.Set;

import org.apache.thrift.TException;

/**
 * Holds the list of all available pools, keyed by their url:port.
 *
 * This class is a simple multiplexer b/w the different client pools, keyed by url:port.
 *
 * To obtain new CassandraClient object invoke borrowClient(). Once the application
 * is done, releaseClient().
 *
 *Example code:
 *
 * <pre>
 *
 * CassandraClient client = clientpool.borrowClient(hostname, port);
 * try {
 *   // do something with client and buessiness logic
 * } catch (Exception e) {
 *   // process exception
 * } finally {
 *   clientpool.releaseClient(client);
 * }
 * </pre>
 *
 * @author Ran Tavory (rantav@gmain.com)
 *
 */
public interface CassandraClientPool {

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
   * Returns the client associated with this keyspace to the connection pool.
   * This is just short for releaseClient(k.getClient());
   */
  void releaseKeyspace(Keyspace k) throws Exception;

  /**
   * Tells all the clients in the pool to update their list of known hosts.
   * @throws TException
   */
  void updateKnownHosts() throws TException;

  Set<String> getExhaustedPoolNames();

  Set<String> getPoolNames();

  int getNumPools();

  int getNumIdle();

  int getNumExhaustedPools();

  int getNumBlockedThreads();

  int getNumActive();

  Set<String> getKnownHosts();

  /**
   * Use this method to invalidate the client and take it out of the pool.
   * This is usually so when the client has errors.
   */
  void invalidateClient(CassandraClient client);
}
