package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.service.CassandraHost;

public class RoundRobinBalancingPolicy implements LoadBalancingPolicy {

  private static final long serialVersionUID = 1107204068032227079L;
  private AtomicInteger counter;
  
  public RoundRobinBalancingPolicy() {
    counter = new AtomicInteger();
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
    counter.compareAndSet(16384, 0);
    return counter.getAndIncrement() % size;    
  }

}
