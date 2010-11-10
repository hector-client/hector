package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Set;

import org.cliffc.high_scale_lib.Counter;

import me.prettyprint.cassandra.service.CassandraHost;

public class RoundRobinBalancingPolicy implements LoadBalancingPolicy {

  private final Counter counter;
  
  public RoundRobinBalancingPolicy() {
    counter = new Counter();
    counter.set(0);
  }
  
  @Override
  public ConcurrentHClientPool getPool(Collection<ConcurrentHClientPool> pools,
      Set<CassandraHost> excludeHosts) {
    ConcurrentHClientPool[] pa = pools.toArray(new ConcurrentHClientPool[pools.size()]);
    int location = getAndIncrement(pa.length);
    ConcurrentHClientPool pool = pa[location];
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {
      while ( excludeHosts.contains(pool.getCassandraHost()) ) {
        pool = pa[++location == pa.length ? 0 : location]; 
      }
    }
    return pool;
  }
  
  private int getAndIncrement(int size) {
    int val = (int)(counter.get() % size); 
    counter.increment();
    return val;
  }

}
