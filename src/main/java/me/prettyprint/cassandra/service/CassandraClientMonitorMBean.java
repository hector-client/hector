package me.prettyprint.cassandra.service;

import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClientPool.ExhaustedPolicy;

import org.apache.thrift.TException;

/**
 * Defines the various JMX methods the CassandraClientMonitor exposes.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
public interface CassandraClientMonitorMBean {

  long getWriteSuccess();
  long getReadSuccess();
  long getWriteFail();
  long getReadFail();
  long getTimedOutCount();
  long getUnavailableCount();
  long getSkipHostSuccess();
  long getPoolExhaustedCount();

  /**
   * Number of existing connection pools.
   * There may be up to one pool per cassandra host.
   */
  int getNumPools();

  /**
   * @return name of all exisging pools.
   */
  Set<String> getPoolNames();

  /**
   * Total number of idle clients in all client pools
   */
  int getNumIdle();

  /**
   * Total number of active clients in all client pools
   */
  int getNumActive();

  /**
   * Number of exhausted connection pools
   * @return
   */
  int getNumExhaustedPools();

  /**
   * List of exhausted pools.
   * @return
   */
  Set<String> getExhaustedPoolNames();

  /**
   * Number of threads that are currently blocked, waiting for a free connection.
   * This number may be greater than 0 only if the {@link ExhaustedPolicy} is
   * {@link ExhaustedPolicy#WHEN_EXHAUSTED_BLOCK}
   */
  int getNumBlockedThreads();

  public Set<String> getKnownHosts();

  /**
   * Tells all pulls to update their list of known hosts.
   * This is useful when an admin adds/removes a host from the ring and wants the application to
   * update asap.
   */
  public void updateKnownHosts() throws TException;
}
