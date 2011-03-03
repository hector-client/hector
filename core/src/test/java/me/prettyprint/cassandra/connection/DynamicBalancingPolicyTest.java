package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DynamicBalancingPolicyTest {
  private List<HClientPool> pools = new ArrayList<HClientPool>();
  private DynamicLoadBalancingPolicy dynBalancingPolicy;
  private LatencyAwareHClientPool poolWithScore5;
  private LatencyAwareHClientPool poolWithScore7;
  private LatencyAwareHClientPool poolWithScore10;

  @Before
  public void setup() {
    poolWithScore5 = Mockito.mock(LatencyAwareHClientPool.class);
    Mockito.when(poolWithScore5.score()).thenReturn(5.0);
    poolWithScore7 = Mockito.mock(LatencyAwareHClientPool.class);
    Mockito.when(poolWithScore7.score()).thenReturn(7.0);
    poolWithScore10 = Mockito.mock(LatencyAwareHClientPool.class);
    Mockito.when(poolWithScore10.score()).thenReturn(10.0);

    Mockito.when(poolWithScore5.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    Mockito.when(poolWithScore7.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    Mockito.when(poolWithScore10.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));

    pools.add(poolWithScore5);
    pools.add(poolWithScore7);
    pools.add(poolWithScore10);
  }

  @Test
  public void testGetPoolOk() {
    dynBalancingPolicy = new DynamicLoadBalancingPolicy();
    dynBalancingPolicy.add(poolWithScore10);
    dynBalancingPolicy.add(poolWithScore7);
    dynBalancingPolicy.add(poolWithScore5);
    dynBalancingPolicy.updateScores();
    assertEquals(poolWithScore5, dynBalancingPolicy.getPool(pools, null));
    assertEquals(poolWithScore5, dynBalancingPolicy.getPool(pools, null));
    assertEquals(poolWithScore5, dynBalancingPolicy.getPool(pools, null));
    Mockito.when(poolWithScore5.score()).thenReturn(8.0);
    dynBalancingPolicy.updateScores();
    assertEquals(poolWithScore7, dynBalancingPolicy.getPool(pools, null));
    assertEquals(poolWithScore7, dynBalancingPolicy.getPool(pools, null));
    assertEquals(poolWithScore7, dynBalancingPolicy.getPool(pools, null));
    Mockito.when(poolWithScore10.score()).thenReturn(4.0);
    dynBalancingPolicy.updateScores();
    assertEquals(poolWithScore10, dynBalancingPolicy.getPool(pools, null));
    assertEquals(poolWithScore10, dynBalancingPolicy.getPool(pools, null));
    assertEquals(poolWithScore10, dynBalancingPolicy.getPool(pools, null));
  }

  @Test
  public void testSkipExhausted() {
    dynBalancingPolicy = new DynamicLoadBalancingPolicy();
    dynBalancingPolicy.add(poolWithScore10);
    Mockito.when(poolWithScore10.score()).thenReturn(100.0);
    Mockito.when(poolWithScore10.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));
    dynBalancingPolicy.add(poolWithScore7);
    Mockito.when(poolWithScore7.score()).thenReturn(7.0);
    Mockito.when(poolWithScore7.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    dynBalancingPolicy.add(poolWithScore5);
    Mockito.when(poolWithScore5.score()).thenReturn(5.0);
    Mockito.when(poolWithScore5.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    dynBalancingPolicy.updateScores();

    assertEquals(poolWithScore7, dynBalancingPolicy.getPool(pools,
        new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWithScore5, dynBalancingPolicy.getPool(pools,
        new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.3:9162")))));
  }

  @Test
  public void testShuffleOnAllEqual() {
    DynamicLoadBalancingPolicy dbp = new DynamicLoadBalancingPolicy();
    LatencyAwareHClientPool poolWithScore2_1 = Mockito.mock(LatencyAwareHClientPool.class);
    Mockito.when(poolWithScore2_1.score()).thenReturn(2.0);
    Mockito.when(poolWithScore2_1.getNumActive()).thenReturn(5);
    Mockito.when(poolWithScore2_1.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.4:9163"));
    LatencyAwareHClientPool poolWithScore2_2 = Mockito.mock(LatencyAwareHClientPool.class);
    Mockito.when(poolWithScore2_2.score()).thenReturn(2.0);
    Mockito.when(poolWithScore2_2.getNumActive()).thenReturn(5);
    Mockito.when(poolWithScore2_2.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.5:9164"));
    LatencyAwareHClientPool poolWithScore2_3 = Mockito.mock(LatencyAwareHClientPool.class);
    Mockito.when(poolWithScore2_3.score()).thenReturn(2.0);
    Mockito.when(poolWithScore2_3.getNumActive()).thenReturn(5);
    Mockito.when(poolWithScore2_3.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.6:9165"));

    List<HClientPool> pool = new ArrayList<HClientPool>();
    pool.add(poolWithScore2_1);
    pool.add(poolWithScore2_2);
    pool.add(poolWithScore2_3);
    dbp.add(poolWithScore2_1);
    dbp.add(poolWithScore2_2);
    dbp.add(poolWithScore2_3);
    dbp.updateScores();

    // should hit all three equal hosts over the course of 50 runs
    Set<CassandraHost> foundHosts = new HashSet<CassandraHost>();
    for (int i = 0; i < 50; i++) {
      HClientPool foundPool = dbp.getPool(pool, null);
      CassandraHost p = foundPool.getCassandraHost();
      foundHosts.add(p);
      assert 5 == foundPool.getNumActive();
    }
    assertEquals(3, foundHosts.size());
  }
}
