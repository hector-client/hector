package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*package*/ class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientMonitor.class);
  private final Map<Counter, AtomicLong> counters;

  private CassandraClientPool poolStore;

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
   log.info("Updating all known cassandra hosts on all clients ");
   poolStore.updateKnownHosts();
  }

  @Override
  public long getNumPoolExhaustedEventCount() {
    return counters.get(Counter.POOL_EXHAUSTED).longValue();
  }

  public void setPoolStore(CassandraClientPool store) {
    poolStore = store;
  }

  @Override
  public Set<String> getExhaustedPoolNames() {
    return poolStore.getExhaustedPoolNames();
  }

  @Override
  public int getNumActive() {
    return poolStore.getNumActive();
  }

  @Override
  public int getNumBlockedThreads() {
    return poolStore.getNumBlockedThreads();
  }

  @Override
  public int getNumExhaustedPools() {
    return poolStore.getNumExhaustedPools();
  }

  @Override
  public int getNumIdleConnections() {
    return poolStore.getNumIdle();
  }

  @Override
  public int getNumPools() {
    return poolStore.getNumPools();
  }

  @Override
  public Set<String> getPoolNames() {
    return poolStore.getPoolNames();
  }

  @Override
  public Set<String> getKnownHosts() {
    return poolStore.getKnownHosts();
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
}
