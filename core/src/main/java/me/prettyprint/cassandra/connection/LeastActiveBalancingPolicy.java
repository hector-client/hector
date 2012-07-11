package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

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
  public HClientPool getPool(Collection<HClientPool> pools, Set<CassandraHost> excludeHosts) {
    List<HClientPool> vals = Lists.newArrayList(pools);
    // shuffle pools to avoid always returning the same one when we are not terribly busy
    Collections.shuffle(vals);
    Collections.sort(vals, new ShufflingCompare());
    Iterator<HClientPool> iterator = vals.iterator();
    HClientPool concurrentHClientPool = iterator.next();
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

  private final class ShufflingCompare implements Comparator<HClientPool> {
    
    public int compare(HClientPool o1, HClientPool o2) {
      if ( log.isDebugEnabled() ) {
        log.debug("comparing 1: {} and count {} with 2: {} and count {}",
          new Object[]{o1.getCassandraHost(), o1.getNumActive(), o2.getCassandraHost(), o2.getNumActive()});
      }
      return o1.getNumActive() - o2.getNumActive();      
    }
  }
  
  @Override
  public HClientPool createConnection(HClientFactory clientFactory, CassandraHost host) {
	  return new ConcurrentHClientPool(clientFactory, host);
  }
}
