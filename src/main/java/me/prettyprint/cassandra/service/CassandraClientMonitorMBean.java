package me.prettyprint.cassandra.service;

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
}
