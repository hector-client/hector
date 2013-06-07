package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

/**
 * Defines the various JMX methods the CassandraClientMonitor exposes.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
public interface CassandraClientMonitorMBean {

  /**
   * @return Number of failed (and not-recovered) writes.
   */
  long getWriteFail();

  /**
   * @return Number of failed (and not recovered) reads.
   */
  long getReadFail();

  /**
   * @return Number of {@link org.apache.cassandra.thrift.TimedOutException} that the client has been able to recover from by
   * failing over to a different host in the ring.
   */
  long getRecoverableTimedOutCount();

  /**
   * @return Number of {@link org.apache.cassandra.thrift.UnavailableException} that the client has been able to recover from by
   * failing over to a different host in the ring.
   */
  long getRecoverableUnavailableCount();

  /**
   * @return Number of {@link TTransportException} that the client has been able to recover from by
   * failing over to a different host in the ring.
   */
  long getRecoverableTransportExceptionCount();

  /**
   * Returns the total number of recoverable errors which is the sum of getRecoverableTimedOutCount,
   * getRecoverableTimedOutCount and getRecoverableTransportExceptionCount
   * @return the total number of recoverable errors by failing over the other hosts.
   */
  long getRecoverableErrorCount();

  /**
   * @return Number of times a skip-host was performed. Hosts are skipped when there are errors at
   * the current host.
   */
  long getSkipHostSuccess();

  /**
   * @return Number of times clients were requested when connection pools were exhausted.
   */
  long getNumPoolExhaustedEventCount();

  /**
   * Number of existing connection pools.
   * There may be up to one pool per cassandra host.
   */
  int getNumPools();


  /**
   * Total number of idle clients in all client pools
   */
  int getNumIdleConnections();

  /**
   * Total number of active clients in all client pools
   */
  int getNumActive();

  /**
   * Number of exhausted connection pools
   * @return
   */
  int getNumExhaustedPools();

  long getRecoverableLoadBalancedConnectErrors();

  /**
   * List of exhausted pools.
   * @return
   */
  Set<String> getExhaustedPoolNames();

  /**
   * Number of threads that are currently blocked, waiting for a free connection.
   */
  int getNumBlockedThreads();

  /**
   * How many times did initial connection failed.
   * @return
   */
  long getNumConnectionErrors();

  public List<String> getKnownHosts();

  /**
   * Tells all pulls to update their list of known hosts.
   * This is useful when an admin adds/removes a host from the ring and wants the application to
   * update asap.
   */
  public void updateKnownHosts() throws HectorTransportException;

  /**
   * Retrieves stats per pool.
   *
   */
  public List<String> getStatisticsPerPool();
  
  /**
   * Add a host in the format of "[hostname]:[port]"
   * 
   * @param hostStr
   * @return
   */
  boolean addCassandraHost(String hostStr);
  
  /**
   * Remove a host in the format of "[hostname]:[port]"
   * @see {@link CassandraHost#equals(Object)} for how hosts are compared
   * 
   * @param hostStr
   * @return
   */
  boolean removeCassandraHost(String hostStr);
  
  /**
   * @see {@link #removeCassandraHost(String)} above for semantics of the host string. 
   * @see {@link HConnectionManager#removeCassandraHost(CassandraHost)} for details of this operation.
   * @param hostStr
   * @return
   */
  boolean suspendCassandraHost(String hostStr);
  
  /**
   * @see {@link #suspendCassandraHost(String)} above. This is the opposite.
   * @param hostStr
   * @return
   */
  boolean unsuspendCassandraHost(String hostStr);
  
  Set<String> getSuspendedCassandraHosts();

  boolean setCassandraHostRetryDelay(String retryDelay);


  /**
   * Total number of connections created due to previous idle connections.
   */
  int getNumRenewedIdleConnections();


  /**
   * Total number of connections created due to previous too long connections.
   */
  int getNumRenewedTooLongConnections();
}
