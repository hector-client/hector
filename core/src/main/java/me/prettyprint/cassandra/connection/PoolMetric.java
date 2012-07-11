package me.prettyprint.cassandra.connection;

public interface PoolMetric {

  int getNumActive();
  int getNumIdle();
  int getNumBlockedThreads();
  String getName();
  boolean getIsActive();
  
}
