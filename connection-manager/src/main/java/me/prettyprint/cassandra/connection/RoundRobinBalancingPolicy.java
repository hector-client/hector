package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Iterables;

/**
 * Implements a RoundRobin balancing policy based off the contents
 * of the active {@link HClientPool}. If a pool is shutdown by another 
 * thread in the midst of the selection process, we return the pool
 * at position 0
 *
 * @author zznate
 */
public class RoundRobinBalancingPolicy implements LoadBalancingPolicy {

  private static final long serialVersionUID = 1107204068032227079L;
  private AtomicInteger counter;
  
  public RoundRobinBalancingPolicy() {
    counter = new AtomicInteger();
  }
  
  @Override
  public HClientPool getPool(Collection<HClientPool> pools,
      Set<HCassandraHost> excludeHosts) {
    HClientPool pool = getPoolSafely(pools);    
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {
      while ( excludeHosts.contains(pool.getCassandraHost()) ) {
        pool = getPoolSafely(pools);
        if ( excludeHosts.size() >= pools.size() )
          break;
      }
    }    
    return pool;
  }
  
  private HClientPool getPoolSafely(Collection<HClientPool> pools) {
    try {
      return Iterables.get(pools, getAndIncrement(pools.size()));
    } catch (IndexOutOfBoundsException e) {
      return pools.iterator().next();
    }       
  }
    
  private int getAndIncrement(int size) {
    counter.compareAndSet(16384, 0);
    return counter.getAndIncrement() % size;    
  }

  @Override
  public HClientPool createConnection(HCassandraHost host) {
  	return new ConcurrentHClientPool(host);
  }
}
