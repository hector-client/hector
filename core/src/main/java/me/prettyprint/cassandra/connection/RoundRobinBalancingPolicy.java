package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class RoundRobinBalancingPolicy implements LoadBalancingPolicy {

  private static final long serialVersionUID = 1107204068032227079L;
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
