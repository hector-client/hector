package me.prettyprint.cassandra.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.TException;
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

  @Override
  public long getReadFail() {
    return counters.get(Counter.READ_FAIL).longValue();
  }

  public long getReadSuccess() {
    return counters.get(Counter.READ_SUCCESS).longValue();
  }

  @Override
  public long getSkipHostSuccess() {
    return counters.get(Counter.SKIP_HOST_SUCCESS).longValue();
  }

  @Override
  public long getRecoverableTimedOutCount() {
    return counters.get(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS).longValue();
  }

  @Override
  public long getRecoverableUnavailableCount() {
    return counters.get(Counter.RECOVERABLE_UNAVAILABLE_EXCEPTIONS).longValue();
  }

  @Override
  public long getWriteFail() {
    return counters.get(Counter.WRITE_FAIL).longValue();
  }

  @Override
  public void updateKnownHosts() throws TException {
   log.info("Updating all known cassandra hosts on all clients");
   for (CassandraClientPool pool: pools) {
     pool.updateKnownHosts();
   }
  }

  @Override
  public long getNumPoolExhaustedEventCount() {
    return counters.get(Counter.POOL_EXHAUSTED).longValue();
  }

  @Override
  public Set<String> getExhaustedPoolNames() {
    Set<String> ret = new HashSet<String>();
    for (CassandraClientPool pool: pools) {
      ret.addAll(pool.getExhaustedPoolNames());
    }
    return ret;
  }

  @Override
  public int getNumActive() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumActive();
    }
    return ret;
  }

  @Override
  public int getNumBlockedThreads() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumBlockedThreads();
    }
    return ret;
  }

  @Override
  public int getNumExhaustedPools() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumExhaustedPools();
    }
    return ret;
  }

  @Override
  public int getNumIdleConnections() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumIdle();
    }
    return ret;
  }

  @Override
  public int getNumPools() {
    int ret = 0;
    for (CassandraClientPool pool: pools) {
      ret += pool.getNumPools();
    }
    return ret;
  }

  @Override
  public Set<String> getPoolNames() {
    Set<String> ret = new HashSet<String>();
    for (CassandraClientPool pool: pools) {
      ret.addAll(pool.getPoolNames());
    }
    return ret;
  }

  @Override
  public Set<String> getKnownHosts() {
    Set<String> ret = new HashSet<String>();
    for (CassandraClientPool pool: pools) {
      ret.addAll(pool.getKnownHosts());
    }
    return ret;
  }

  @Override
  public long getRecoverableTransportExceptionCount() {
    return counters.get(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS).longValue();
  }

  @Override
  public long getRecoverableErrorCount() {
    return getRecoverableTimedOutCount() + getRecoverableTransportExceptionCount() +
        getRecoverableUnavailableCount();
  }

  public void addPool(CassandraClientPool pool) {
    pools.add(pool);
  }
}
