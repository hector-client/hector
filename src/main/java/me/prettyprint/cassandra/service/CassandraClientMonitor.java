package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientMonitor.class);
  private final Map<Counter, AtomicLong> counters;

  /** Set of all registered cassandra clients */
  private final Set<CassandraClient> clients;

  /**
   * List of available JMX counts
   */
  public enum Counter {
    TIMED_OUT_EXCEPTIONS,
    UNAVAILABLE_EXCEPTIONS,
    SKIP_HOST_SUCCESS,
    WRITE_SUCCESS,
    WRITE_FAIL,
    READ_SUCCESS,
    READ_FAIL,

  }

  public CassandraClientMonitor() {
    counters = new HashMap<Counter, AtomicLong>();
    for (Counter counter: Counter.values()) {
      counters.put(counter, new AtomicLong(0));
    }
    clients = new HashSet<CassandraClient>();
  }

  public void addClient(CassandraClient c) {
    synchronized (clients) {
      clients.add(c);
    }
  }

  public void removeClient(CassandraClient c) {
    synchronized (clients) {
      clients.remove(c);
    }
  }

  public void incCounter(Counter counterType) {
    counters.get(counterType).incrementAndGet();
  }

  @Override
  public long getWriteSuccess() {
    return counters.get(Counter.WRITE_SUCCESS).longValue();
  }

  @Override
  public long getReadFail() {
    return counters.get(Counter.READ_FAIL).longValue();
  }

  @Override
  public long getReadSuccess() {
    return counters.get(Counter.READ_SUCCESS).longValue();
  }

  @Override
  public long getSkipHostSuccess() {
    return counters.get(Counter.SKIP_HOST_SUCCESS).longValue();
  }

  @Override
  public long getTimedOutCount() {
    return counters.get(Counter.TIMED_OUT_EXCEPTIONS).longValue();
  }

  @Override
  public long getUnavailableCount() {
    return counters.get(Counter.UNAVAILABLE_EXCEPTIONS).longValue();
  }

  @Override
  public long getWriteFail() {
    return counters.get(Counter.WRITE_FAIL).longValue();
  }

  @Override
  public void updateKnownHosts() throws TException {
    synchronized (clients) {
      log.info("Updating all known cassandra hosts on all {} clients ", clients.size());
      for (CassandraClient c: clients) {
        c.updateKnownHosts();
      }
    }
  }
}
