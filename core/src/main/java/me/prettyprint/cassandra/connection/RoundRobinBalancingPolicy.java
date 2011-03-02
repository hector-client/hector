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
  public HClientPool getPool(Collection<HClientPool> pools,
      Set<CassandraHost> excludeHosts) {
    Object[] pa = pools.toArray();
    int location = getAndIncrement(pa.length);    
    HClientPool pool = (HClientPool)pa[location];    
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {
      while ( excludeHosts.contains(pool.getCassandraHost()) ) {
        pool = (HClientPool)pa[getAndIncrement(pa.length)]; 
      }
    }    
    return pool;
  }
    
  private int getAndIncrement(int size) {
    counter.compareAndSet(16384, 0);
    return counter.getAndIncrement() % size;    
  }

  @Override
  public HClientPool createConnection(CassandraHost host) {
  	return new ConcurrentHClientPool(host);
  }
}
