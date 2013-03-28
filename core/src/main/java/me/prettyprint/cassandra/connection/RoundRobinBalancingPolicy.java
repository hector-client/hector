package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Set;

import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;

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
  private int counter;
  
  public RoundRobinBalancingPolicy() {
    counter = 0;
  }
  
  @Override
  public HClientPool getPool(Collection<HClientPool> pools,
      Set<CassandraHost> excludeHosts) {
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
    int counterToReturn;
    
    // There should not be that much of contention here as
    // the "if" statement plus the increment is executed real fast.
    synchronized (this) {
      if (counter >= 16384) {
        counter = 0;
      }
      counterToReturn = counter++;
    }

    return counterToReturn % size;
  }

  @Override
  public HClientPool createConnection(HClientFactory clientFactory, CassandraHost host, CassandraClientMonitor monitor) {
    return new ConcurrentHClientPool(clientFactory, host, monitor);
  }
}
