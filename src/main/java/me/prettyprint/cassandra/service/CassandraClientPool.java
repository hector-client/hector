package me.prettyprint.cassandra.service;

import java.util.Set;

import org.apache.thrift.TException;

/**
 * Holds the list of all available pools, keyed by their url:port.
 *
 * This class is a simple multiplexer b/w the different client pools, keyed by url:port.
 *
 * To obtain new CassandraClient object, invoke the appropriate borrowClient() for your
 * intended usage (more details below). Once the application is done with the client,
 * releaseClient().
 *
 * Callers can interact with CassandraClientPool in one of two basic ways:
 * <ul>
 * <li>Configuring connections before hand via dependency injection and using {@link #borrowClient()}</li>
 * <li>Providing a host or list of hosts at runtime to the overloaded versions of borrowClient</li>
 * </ul>
 *
 * Example code where the client intends to manage to connections at the code level:
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
 * Example code and configuration where CassandraClientPool has been configured with hosts via
 * SpringFramework:
 *
 * <pre>
 * &#64;Resource
 * private CassandraClientPool cassandraClientPool;
 * ...
 *
 * CassandraClient client = cassandraClientPool.borrowClient();
 * //... same as above
 * </pre>
 *
 * In the configuration file, we would have:
 *
 * <pre>
 * ...
 *   &lt;bean id="cassandraClientMonitor" class="me.prettyprint.cassandra.service.CassandraClientMonitor"/&gt;
 *
 *   &lt;bean id="jmxMonitor" class="me.prettyprint.cassandra.service.JmxMonitor"&gt;
 *       &lt;constructor-arg&gt;&lt;ref bean="cassandraClientMonitor"/&gt;&lt;/constructor-arg&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id="cassandraClientPoolFactory" class="me.prettyprint.cassandra.service.CassandraClientPoolFactory" factory-method="getInstance"/&gt;
 *
 *   &lt;bean id="cassandraClientPool" factory-bean="cassandraClientPoolFactory" factory-method="createNew"&gt;
 *       &lt;constructor-arg&gt;&lt;ref bean="cassandraHostConfigurator"/&gt;&lt;/constructor-arg&gt;
 *   &lt;/bean&gt;
 *   
 *   &lt;bean id="cassandraHostConfigurator" class="me.prettyprint.cassandra.service.CassandraHostConfigurator"&gt;
 *       &lt;constructor-arg value="localhost:9170"/&gt;
 *   &lt;/bean&gt;
 *   ...
 * </pre>
 *
 * @author Ran Tavory (rantav@gmain.com)
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
public interface CassandraClientPool {

  /**
   * Borrow a previously created CassandraClient. To be used when you have set up the
   * hosts before hand or you dont care about which host services the request
   * @return
   */
  CassandraClient borrowClient()
      throws IllegalStateException, PoolExhaustedException, Exception;

  /**
   * Borrows a client from the pool defined by url:port
   * @param url
   * @param port
   * @return
   */
  CassandraClient borrowClient(String url, int port)
      throws IllegalStateException, PoolExhaustedException, Exception;

  /**
   * Borrows a client, similar to {@link #borrowClient(String, int)}, but expects the url:port
   * string format
   * @param urlPort a string of the format url:port
   */
  CassandraClient borrowClient(String urlPort)
      throws IllegalStateException, PoolExhaustedException, Exception;

  /**
   * Borrows a load-balanced client, a random client from the array of given client addresses.
   *
   * This method is typically used to allow load balancing b/w the list of given client URLs. The
   * method will return a random client from the array of the given url:port pairs.
   * The method will try connecting each host in the list and will only stop when there's one
   * successful connection, so in that sense it's also useful for failover.
   *
   * @param clientUrls An array of "url:port" cassandra client addresses.
   *
   * @return A randomly chosen client from the array of clientUrls.
   * @throws Exception
   */
  CassandraClient borrowClient(String[] clientUrls) throws Exception;

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

  /**
   * Use this method to invalidate all client connections to the same host.
   */
  void invalidateAllConnectionsToHost(CassandraClient client);
}
