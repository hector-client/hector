package me.prettyprint.cassandra.connection;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Iterables;

import me.prettyprint.cassandra.service.CassandraHost;

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
  public HClientPool getPool(List<HClientPool> pools,
      Set<CassandraHost> excludeHosts) {
    HClientPool pool = getPoolSafely(getAndIncrement(pools.size()), pools);    
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {
      while ( excludeHosts.contains(pool.getCassandraHost()) ) {
        pool = getPoolSafely(getAndIncrement(pools.size()), pools);
      }
    }    
    return pool;
  }
  
  private HClientPool getPoolSafely(int location, List<HClientPool> pools) {
    return Iterables.get(pools, location, pools.get(0));
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
