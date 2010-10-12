package me.prettyprint.cassandra.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraHost;

public class LeastActiveBalancingPolicy implements LoadBalancingPolicy {

  @Override
  public ConcurrentHClientPool getPool(Collection<ConcurrentHClientPool> pools, Set<CassandraHost> excludeHosts) {
    List<ConcurrentHClientPool> vals = new ArrayList<ConcurrentHClientPool>(pools); 
      Collections.sort(vals, new Comparator<ConcurrentHClientPool>() {      
        public int compare(ConcurrentHClientPool o1, ConcurrentHClientPool o2) {
        return 0;
      }            
    });
    ConcurrentHClientPool concurrentHClientPool = null;
    for (Iterator iterator = vals.iterator(); iterator.hasNext();) {
      concurrentHClientPool = (ConcurrentHClientPool) iterator.next();
      if ( !excludeHosts.contains(concurrentHClientPool.getCassandraHost()) ) {
        break;
      }
    }
    return concurrentHClientPool;
  }

}

/*

@Override
public int compareTo(ConcurrentHClientPool o) {    
  if ( getNumBeforeExhausted() > o.getNumBeforeExhausted() ) {
    return 1;
  } else if ( getNumBeforeExhausted() < o.getNumBeforeExhausted() ) {
    return -1;
  }
  return 0;
}

*/