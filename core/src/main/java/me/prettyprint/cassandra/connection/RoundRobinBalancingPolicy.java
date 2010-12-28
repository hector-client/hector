package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.cliffc.high_scale_lib.Counter;

import me.prettyprint.cassandra.service.CassandraHost;

public class RoundRobinBalancingPolicy implements LoadBalancingPolicy {

  private AtomicLong counter;
  
  public RoundRobinBalancingPolicy() {
    counter = new AtomicLong(Long.MIN_VALUE);
  }
  
  @Override
  public ConcurrentHClientPool getPool(Collection<ConcurrentHClientPool> pools,
      Set<CassandraHost> excludeHosts) {
    ConcurrentHClientPool[] pa = pools.toArray(new ConcurrentHClientPool[pools.size()]);
    int location = getAndIncrement(pa.length);
    ConcurrentHClientPool pool = pa[location];
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {
      while ( excludeHosts.contains(pool.getCassandraHost()) ) {
        pool = pa[getAndIncrement(pa.length)]; 
      }
    }
    return pool;
  }
    
  private int getAndIncrement(int size) {
    return (int)counter.getAndIncrement() % size;    
  }

}
