package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;

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

  /**
   * Make the results of this Comparator stable (and thus transitive) by caching the numActive value
   * for each HClientPool as they are seen, then reusing the cached value instead of the current value
   * (which may have changed) if the same pool is compared again.
   *
   * Without this change the new TimSort algorithm in Java 7 sometimes throws a:
   * java.lang.IllegalArgumentException: Comparison method violates its general contract!
   */
  static final class ShufflingCompare implements Comparator<HClientPool> {
    private Map<HClientPool, Integer> cachedActive = new HashMap<HClientPool, Integer>();

    public int compare(HClientPool o1, HClientPool o2) {
      if ( log.isDebugEnabled() ) {
        log.debug("comparing 1: {} and count {} with 2: {} and count {}",
          new Object[]{o1.getCassandraHost(), o1.getNumActive(), o2.getCassandraHost(), o2.getNumActive()});
      }
      return getNumActive(o1) - getNumActive(o2);
    }
    private int getNumActive(HClientPool p) {
      Integer ret = cachedActive.get(p);
      if (ret == null) {
        ret = p.getNumActive();
        cachedActive.put(p, ret);
      }
      return ret;
    }
  }

  @Override
  public HClientPool createConnection(HClientFactory clientFactory, CassandraHost host, CassandraClientMonitor monitor) {
	  return new ConcurrentHClientPool(clientFactory, host, monitor);
  }
}
