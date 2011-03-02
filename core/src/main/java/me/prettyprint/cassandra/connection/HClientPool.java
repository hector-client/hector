package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.exceptions.HectorException;

public interface HClientPool extends PoolMetric {
  public HThriftClient borrowClient() throws HectorException;
  public CassandraHost getCassandraHost();
  public int getNumBeforeExhausted();
  public boolean isExhausted();
  public int getMaxActive();
  public String getStatusAsString();
  public void releaseClient(HThriftClient client) throws HectorException;
  void shutdown();
}