package me.prettyprint.cassandra.service;

public interface DownCassandraHostRetryServiceMBean {
  
  int getRetryDelayInSeconds();
  
  void setRetryDelayInSeconds(int retryDelayInSeonds);
  
  void applyRetryDelay();
  
  void flushQueue();

}
