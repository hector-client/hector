package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private final Map<Counter, AtomicLong> counters;

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
}
