package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selects the least active host based on the number of active connections.
 * The list of hosts is shuffled on each pass to account for the case
 * where a number of hosts are at the minimum number of connections
 * (ie. they are not busy).
 * 
 * 
 * @author zznate
 */
public class LeastActiveBalancingPolicy implements LoadBalancingPolicy {
  
  private static final long serialVersionUID = 329849818218657061L;

  private static final Logger log = LoggerFactory.getLogger(LeastActiveBalancingPolicy.class);
  
  @Override
  public ConcurrentHClientPool getPool(Collection<ConcurrentHClientPool> pools, Set<CassandraHost> excludeHosts) {
    List<ConcurrentHClientPool> vals = new ArrayList<ConcurrentHClientPool>(pools);
    // shuffle pools to avoid always returning the same one when we are not terribly busy
    Collections.shuffle(vals);
    Collections.sort(vals, new ShufflingCompare());
    Iterator<ConcurrentHClientPool> iterator = vals.iterator();
    ConcurrentHClientPool concurrentHClientPool = iterator.next();
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {
      while (iterator.hasNext()) {        
        if ( !excludeHosts.contains(concurrentHClientPool.getCassandraHost()) ) {
          break;
        }
        concurrentHClientPool = (ConcurrentHClientPool) iterator.next();
      }
    }
    return concurrentHClientPool;
  }

  private final class ShufflingCompare implements Comparator<ConcurrentHClientPool> {
    
    public int compare(ConcurrentHClientPool o1, ConcurrentHClientPool o2) {
      if ( log.isDebugEnabled() ) {
        log.info("comparing 1: {} and count {} with 2: {} and count {}",
          new Object[]{o1.getCassandraHost(), o1.getNumActive(), o2.getCassandraHost(), o2.getNumActive()});
      }
      return o1.getNumActive() - o2.getNumActive();      
    }
  }
}
