package me.prettyprint.cassandra.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*package*/ class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientMonitor.class);
  private final Map<Counter, AtomicLong> counters;

  private final Set<CassandraClientPool> pools;

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

  public CassandraClientMonitor() {
    // Use a high concurrency map.
    pools = Collections.newSetFromMap(new ConcurrentHashMap<CassandraClientPool,Boolean>());
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
   for (CassandraClientPool pool: pools) {
     pool.updateKnownHosts();
   }
  }


  public long getNumPoolExhaustedEventCount() {
    return counters.get(Counter.POOL_EXHAUSTED).longValue();
  }


  public Set<String> getExhaustedPoolNames() {
    Set<String> ret = new HashSet<String>();
    for (CassandraClientPool pool: pools) {
      ret.addAll(pool.getExhaustedPoolNames());
    }
    return ret;
  }


  public int getNumActive() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumActive();
    }
    return ret;
  }


  public int getNumBlockedThreads() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumBlockedThreads();
    }
    return ret;
  }


  public int getNumExhaustedPools() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumExhaustedPools();
    }
    return ret;
  }


  public int getNumIdleConnections() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumIdle();
    }
    return ret;
  }


  public int getNumPools() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumPools();
    }
    return ret;
  }


  public Set<String> getPoolNames() {
    Set<String> ret = new HashSet<String>();
    for (CassandraClientPool pool: pools) {
      ret.addAll(pool.getPoolNames());
    }
    return ret;
  }


  public Set<CassandraHost> getKnownHosts() {
    Set<CassandraHost> ret = new HashSet<CassandraHost>();
    for (CassandraClientPool pool: pools) {
      ret.addAll(pool.getKnownHosts());
    }
    return ret;
  }


  public long getRecoverableTransportExceptionCount() {
    return counters.get(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS).longValue();
  }


  public long getRecoverableErrorCount() {
    return getRecoverableTimedOutCount() + getRecoverableTransportExceptionCount() +
        getRecoverableUnavailableCount() + getRecoverableLoadBalancedConnectErrors();
  }

  public void addPool(CassandraClientPool pool) {
    pools.add(pool);
  }


  public long getRecoverableLoadBalancedConnectErrors() {
    return counters.get(Counter.RECOVERABLE_LB_CONNECT_ERRORS).longValue();
  }


  public long getNumConnectionErrors() {
    return counters.get(Counter.CONNECT_ERROR).longValue();
  }
}
