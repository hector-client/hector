package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import org.cliffc.high_scale_lib.NonBlockingIdentityHashMap;

public class HConnectionManager {

  private final NonBlockingIdentityHashMap<CassandraHost, ConcurrentHClientPool> hostPools;
  
  public HConnectionManager() {
    hostPools = new NonBlockingIdentityHashMap<CassandraHost, ConcurrentHClientPool>();
  }
  
  
  
  public HThriftClient borrowClient() { // needed? may not be
    return null;
  }
  
  public void releaseClient(HThriftClient client) { // may not be needed
    
  }
  
  // operationWithFailover(Operation op)
  
  
}
