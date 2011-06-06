package me.prettyprint.cassandra.connection;

import me.prettyprint.hector.api.exceptions.HectorException;

public interface HClientPool extends PoolMetric {
  public HThriftClient borrowClient() throws HectorException;
  public HCassandraHost getCassandraHost();
  public int getNumBeforeExhausted();
  public boolean isExhausted();
  public int getMaxActive();
  public String getStatusAsString();
  public void releaseClient(HThriftClient client) throws HectorException;
  void shutdown();
}