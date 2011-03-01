package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.service.CassandraHost;

import org.apache.cassandra.concurrent.RetryingScheduledThreadPoolExecutor;
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
public class DynamicLoadBalancingPolicy implements LoadBalancingPolicy
{

    private static final long serialVersionUID = -1044985880174118325L;
    private static final Logger log = LoggerFactory.getLogger(DynamicLoadBalancingPolicy.class);
    public static RetryingScheduledThreadPoolExecutor tasks = new RetryingScheduledThreadPoolExecutor("BGTasks");

    // references which is used to make the real time requests faster.
    private Map<HClientPool, Double> scores = Maps.newConcurrentMap();
    private List<LatencyAwareHClientPool> allPools = new CopyOnWriteArrayList<LatencyAwareHClientPool>();

    // default values this can be changed by the Client.
    private int UPDATE_INTERVAL = 10000;
    private int RESET_INTERVAL = 20000;
    private double DYNAMIC_BADNESS_THRESHOLD = 0.10;

    public DynamicLoadBalancingPolicy()
    {

        // Pre-calculate the scores so as we can compare it fast.
        Runnable updateThread = new Runnable()
        {
            public void run()
            {
                updateScores();
            }
        };

        // Clear Stats.
        Runnable resetThread = new Runnable()
        {
            public void run()
            {
                for (LatencyAwareHClientPool pool : allPools)
                {
                    pool.clear();
                }
            }
        };
        tasks.scheduleWithFixedDelay(updateThread, UPDATE_INTERVAL, UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
        tasks.scheduleWithFixedDelay(resetThread, RESET_INTERVAL, RESET_INTERVAL, TimeUnit.MILLISECONDS);
    }

    @Override
    public HClientPool getPool(Collection<HClientPool> pools, Set<CassandraHost> excludeHosts)
    {
        List<HClientPool> poolList = Lists.newArrayList(pools);

        // remove the hosts from the list.
        if (excludeHosts != null)
        {
            filter(poolList, excludeHosts);
        }

        Collections.shuffle(poolList);
        HClientPool fp = poolList.get(0);
        Double first = scores.get(fp);
        for (int i = 1; i < poolList.size(); i++)
        {
            HClientPool np = poolList.get(i);
            Double next = scores.get(np);
            if ((first - next) / first > DYNAMIC_BADNESS_THRESHOLD)
            {
                Collections.sort(poolList, new SortByScoreComparator());
                if (log.isDebugEnabled())
                    log.debug(String.format("According to score we have chosen {} vs first {}", poolList.get(0), fp));
                break;
            }
        }
        return poolList.get(0);
    }

    private void filter(List<HClientPool> from, Set<CassandraHost> subList)
    {
        Iterator<HClientPool> it = from.iterator();
        while (it.hasNext())
        {
            if (subList.contains(it.next().getCassandraHost()))
                it.remove();
        }
    }

    private class SortByScoreComparator implements Comparator<HClientPool>
    {
        public int compare(HClientPool p1, HClientPool p2)
        {
            Double scored1 = scores.get(p1);
            Double scored2 = scores.get(p2);
            if (scored1.equals(scored2))
                return 0;
            if (scored1 < scored2)
                return -1;
            else
                return 1;
        }
    }

    @Override
    public HClientPool createConnection(CassandraHost host)
    {
        LatencyAwareHClientPool pool = new LatencyAwareHClientPool(host);
        add(pool);
        return pool;
    }

    void add(LatencyAwareHClientPool pool)
    {
        allPools.add(pool);
        // update the reference of the scores intially it is Zero.
        scores.put(pool, 0.0);
    }

    void updateScores() // this is expensive
    {
        for (LatencyAwareHClientPool pool : allPools)
        {
            scores.put(pool, pool.score());
            pool.resetIntervel();
        }
    }

    public int getUpdateInterval()
    {
        return UPDATE_INTERVAL;
    }

    public void setUpdateInterval(int updateInterval)
    {
        UPDATE_INTERVAL = updateInterval;
    }

    public int getResetInterval()
    {
        return RESET_INTERVAL;
    }

    public void setResetInterval(int resetInterval)
    {
        RESET_INTERVAL = resetInterval;
    }

    public double getBadnessThreshold()
    {
        return DYNAMIC_BADNESS_THRESHOLD;
    }

    public void setBadnessThreshold(double badness)
    {
        DYNAMIC_BADNESS_THRESHOLD = badness;
    }
}