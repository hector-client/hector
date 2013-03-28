package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.utils.DaemonThreadPoolFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * This LB Algorithm has the Phi algo which Dynamic snitch uses, LB is based on the probablity of failure of the node.
 * TODO: Make cassandra code abstracted enough so we can inherit from the same.
 * 
 * @author Vijay Parthasarathy
 */
public class DynamicLoadBalancingPolicy implements LoadBalancingPolicy {

  private static final long serialVersionUID = -1044985880174118325L;
  private static final Logger log = LoggerFactory.getLogger(DynamicLoadBalancingPolicy.class);
  
  private final ScheduledExecutorService tasks = new ScheduledThreadPoolExecutor(1, new DaemonThreadPoolFactory(getClass()));

  // references which is used to make the real time requests faster.
  private Map<HClientPool, Double> scores = Maps.newConcurrentMap();
  private List<LatencyAwareHClientPool> allPools = new CopyOnWriteArrayList<LatencyAwareHClientPool>();

  // default values this can be changed by the Client.
  private int UPDATE_INTERVAL = 100;
  private int RESET_INTERVAL = 20000;
  private double DYNAMIC_BADNESS_THRESHOLD = 0.10;

  public DynamicLoadBalancingPolicy() {

    // Pre-calculate the scores so as we can compare it fast.
    Runnable updateThread = new Runnable() {
      public void run() {
        try {
          updateScores();
        } catch(Exception e) {
          log.info("exception updating scores", e);
        }
      }
    };

    // Clear Stats.
    Runnable resetThread = new Runnable() {
      public void run() {
        try {
          for (LatencyAwareHClientPool pool : allPools) {
            pool.clear();
          }
        } catch(Exception e) {
          log.info("exceotuib reseting stats", e);
        }
      }
    };
    tasks.scheduleWithFixedDelay(updateThread, UPDATE_INTERVAL, UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
    tasks.scheduleWithFixedDelay(resetThread, RESET_INTERVAL, RESET_INTERVAL, TimeUnit.MILLISECONDS);
  }

  @Override
  public HClientPool getPool(Collection<HClientPool> pools, Set<CassandraHost> excludeHosts) {
    List<HClientPool> poolList = Lists.newArrayList(pools);

    // remove the hosts from the list.
    if (excludeHosts != null) {
      filter(poolList, excludeHosts);
    }

    Collections.shuffle(poolList);
    HClientPool fp = poolList.get(0);
    Double first = scores.get(fp);
    for (int i = 1; i < poolList.size(); i++) {
      HClientPool np = poolList.get(i);
      Double next = scores.get(np);
      if ((first - next) / first > DYNAMIC_BADNESS_THRESHOLD) {
        Collections.sort(poolList, new SortByScoreComparator());
        if (log.isDebugEnabled())
          log.debug("According to score we have chosen {} vs first {}", poolList.get(0), fp);
        break;
      }
    }
    return poolList.get(0);
  }

  private void filter(List<HClientPool> from, Set<CassandraHost> subList) {
    Iterator<HClientPool> it = from.iterator();
    while (it.hasNext()) {
      if (subList.contains(it.next().getCassandraHost()))
        it.remove();
    }
  }

  private class SortByScoreComparator implements Comparator<HClientPool> {
    public int compare(HClientPool p1, HClientPool p2) {
      Double scored1 = scores.get(p1);
      Double scored2 = scores.get(p2);
      if (scored1.equals(scored2))
        return 0;
      if (scored1 < scored2)
        return -1;
      else return 1;
    }
  }

  @Override
  public HClientPool createConnection(HClientFactory clientFactory, CassandraHost host, CassandraClientMonitor monitor) {
    LatencyAwareHClientPool pool = new LatencyAwareHClientPool(clientFactory, host, monitor);
    add(pool);
    return pool;
  }

  // This is helper class for the test cases. TODO: cleanup.
  void add(LatencyAwareHClientPool pool) {
    allPools.add(pool);
    // update the reference of the scores intially it is Zero.
    scores.put(pool, 0.0);
  }

  // This will be a expensive call.
  void updateScores() {
    for (LatencyAwareHClientPool pool : allPools) {
      scores.put(pool, pool.score());
      pool.resetIntervel();
    }
  }

  public int getUpdateInterval() {
    return UPDATE_INTERVAL;
  }

  /**
   * Set the configured interval for the stats to be recalculated (until this time it is been cached.
   * 
   * @param updateInterval
   *          In ms.
   */
  public void setUpdateInterval(int updateInterval) {
    UPDATE_INTERVAL = updateInterval;
  }

  public int getResetInterval() {
    return RESET_INTERVAL;
  }

  /**
   * Set the configured interval for the stats to be reset so that the new stats are allowed and we can get rid of bad
   * nodes value. This is under the assumption that the bad nodes will eventually get better....
   * 
   * @param resetInterval
   *          in ms
   */
  public void setResetInterval(int resetInterval) {
    RESET_INTERVAL = resetInterval;
  }

  public double getBadnessThreshold() {
    return DYNAMIC_BADNESS_THRESHOLD;
  }

  /**
   * This is the percentage of badness which is acceptable...
   * 
   * Example: A should be 0.20 (20%) bad than B before B is choosen rathar than A.
   * 
   * @param badness
   *          in %
   */
  public void setBadnessThreshold(double badness) {
    DYNAMIC_BADNESS_THRESHOLD = badness;
  }
}
