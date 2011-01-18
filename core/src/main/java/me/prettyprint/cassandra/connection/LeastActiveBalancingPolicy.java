package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import java.util.*;

public class LeastActiveBalancingPolicy implements LoadBalancingPolicy {

  private static final long serialVersionUID = 329849818218657061L;

    @Override
  public ConcurrentHClientPool getPool(Collection<ConcurrentHClientPool> pools, Set<CassandraHost> excludeHosts) {
    List<ConcurrentHClientPool> vals = new ArrayList<ConcurrentHClientPool>(pools); 
      Collections.sort(vals, new Comparator<ConcurrentHClientPool>() {      
        public int compare(ConcurrentHClientPool o1, ConcurrentHClientPool o2) {
          if ( o1.getNumActive() < o2.getNumActive() ) {
            return -1;
          } else if ( o1.getNumActive() > o2.getNumActive() ) {
            return 1;
          }
          return 0;
      }            
    });
    Iterator<ConcurrentHClientPool> iterator = vals.iterator();
    ConcurrentHClientPool concurrentHClientPool = iterator.next();
    if ( excludeHosts != null && excludeHosts.size() > 0 ) {          
      while (iterator.hasNext()) {
        concurrentHClientPool = (ConcurrentHClientPool) iterator.next();
        if ( !excludeHosts.contains(concurrentHClientPool.getCassandraHost()) ) {
          break;
        }
      }
    }
    return concurrentHClientPool;
  }

}
