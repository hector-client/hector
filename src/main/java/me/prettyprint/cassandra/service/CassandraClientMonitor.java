package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private final Map<Counter, AtomicLong> counters;

  public enum Counter {
    TIMED_OUT_EXCEPTIONS, SKIP_HOST_SUCCESS, WRITE_SUCCESS, WRITE_FAIL, READ_SUCCESS, READ_FAIL, UNAVAILABLE_EXCEPTIONS

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

  @Override
  public long getWriteSuccess() {
    return counters.get(Counter.WRITE_SUCCESS).longValue();
  }
}
