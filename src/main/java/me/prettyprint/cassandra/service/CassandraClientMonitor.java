package me.prettyprint.cassandra.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.cassandra.connection.ConcurrentHClientPool;
import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientMonitor.class);
  private final Map<Counter, AtomicLong> counters;

  private final HConnectionManager connectionManager;

  /**
   * List of available JMX counts
   */
  public enum Counter {
    RECOVERABLE_TIMED_OUT_EXCEPTIONS,
    RECOVERABLE_UNAVAILABLE_EXCEPTIONS,
    RECOVERABLE_TRANSPORT_EXCEPTIONS,
    SKIP_HOST_SUCCESS,
    WRITE_SUCCESS,
    WRITE_FAIL,
    READ_SUCCESS,
    READ_FAIL,
    POOL_EXHAUSTED,
    /** Load balance connection errors */
    RECOVERABLE_LB_CONNECT_ERRORS,
    /** Connection time errors - unable to connect to host or something... */
    CONNECT_ERROR,
  }

  public CassandraClientMonitor(HConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
    counters = new HashMap<Counter, AtomicLong>();
    for (Counter counter: Counter.values()) {
      counters.put(counter, new AtomicLong(0));
    }
  }

  public void incCounter(Counter counterType) {
    counters.get(counterType).incrementAndGet();
  }

  public long getWriteSuccess() {
    return counters.get(Counter.WRITE_SUCCESS).longValue();
  }


  public long getReadFail() {
    return counters.get(Counter.READ_FAIL).longValue();
  }

  public long getReadSuccess() {
    return counters.get(Counter.READ_SUCCESS).longValue();
  }


  public long getSkipHostSuccess() {
    return counters.get(Counter.SKIP_HOST_SUCCESS).longValue();
  }


  public long getRecoverableTimedOutCount() {
    return counters.get(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS).longValue();
  }


  public long getRecoverableUnavailableCount() {
    return counters.get(Counter.RECOVERABLE_UNAVAILABLE_EXCEPTIONS).longValue();
  }


  public long getWriteFail() {
    return counters.get(Counter.WRITE_FAIL).longValue();
  }


  public void updateKnownHosts() throws HectorTransportException {
   log.info("Updating all known cassandra hosts on all clients");
   
  }


  public long getNumPoolExhaustedEventCount() {
    return counters.get(Counter.POOL_EXHAUSTED).longValue();
  }


  public Set<String> getExhaustedPoolNames() {
    Set<String> ret = new HashSet<String>();
    
    return ret;
  }


  public int getNumActive() {
    int ret = 0;
    Collection<ConcurrentHClientPool> pools = connectionManager.getActivePools();
    for (ConcurrentHClientPool concurrentHClientPool : pools) {
      ret += concurrentHClientPool.getNumActive();
    }
    return ret;
  }


  public int getNumBlockedThreads() {
    int ret = 0;
    Collection<ConcurrentHClientPool> pools = connectionManager.getActivePools();
    for (ConcurrentHClientPool concurrentHClientPool : pools) {
      ret += concurrentHClientPool.getNumBlockedThreads();
    }
    return ret;
  }


  public int getNumExhaustedPools() {
    return connectionManager.getDownedHosts().size();
  }


  public int getNumIdleConnections() {
    int ret = 0;
    Collection<ConcurrentHClientPool> pools = connectionManager.getActivePools();
    for (ConcurrentHClientPool concurrentHClientPool : pools) {
      ret += concurrentHClientPool.getNumIdle();
    }
    return ret;
  }


  public int getNumPools() {
    return connectionManager.getHosts().size();
  }



  public Set<CassandraHost> getKnownHosts() {
    return connectionManager.getHosts();
  }


  public long getRecoverableTransportExceptionCount() {
    return counters.get(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS).longValue();
  }


  public long getRecoverableErrorCount() {
    return getRecoverableTimedOutCount() + getRecoverableTransportExceptionCount() +
        getRecoverableUnavailableCount() + getRecoverableLoadBalancedConnectErrors();
  }  


  public long getRecoverableLoadBalancedConnectErrors() {
    return counters.get(Counter.RECOVERABLE_LB_CONNECT_ERRORS).longValue();
  }


  public long getNumConnectionErrors() {
    return counters.get(Counter.CONNECT_ERROR).longValue();
  }
}
