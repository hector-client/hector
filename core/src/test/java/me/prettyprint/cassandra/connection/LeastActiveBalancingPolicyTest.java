package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.junit.Test;
import org.mockito.Mockito;

public class LeastActiveBalancingPolicyTest extends BaseBalancingPolicyTest {

  private LeastActiveBalancingPolicy leastActiveBalancingPolicy;

  @Test
  public void testGetPoolOk() {
    leastActiveBalancingPolicy = new LeastActiveBalancingPolicy();
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
    Mockito.when(poolWith5Active.getNumActive()).thenReturn(8);
    assertEquals(poolWith7Active, leastActiveBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith7Active, leastActiveBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith7Active, leastActiveBalancingPolicy.getPool(pools, null));
    Mockito.when(poolWith5Active.getNumActive()).thenReturn(4);
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
  }

  @Test
  public void testSkipExhausted() {
    leastActiveBalancingPolicy = new LeastActiveBalancingPolicy();
    assertEquals(poolWith7Active, leastActiveBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.2:9161")))));
  }

  @Test
  public void testShuffleOnAllEqual() {
    ConcurrentHClientPool poolWith5Active2 = Mockito.mock(ConcurrentHClientPool.class);
    Mockito.when(poolWith5Active2.getNumActive()).thenReturn(5);
    Mockito.when(poolWith5Active2.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.4:9163"));
    ConcurrentHClientPool poolWith5Active3 = Mockito.mock(ConcurrentHClientPool.class);
    Mockito.when(poolWith5Active3.getNumActive()).thenReturn(5);
    Mockito.when(poolWith5Active3.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.5:9164"));

    pools.add(poolWith5Active2);
    pools.add(poolWith5Active3);

    leastActiveBalancingPolicy = new LeastActiveBalancingPolicy();
    // should hit all three equal hosts over the course of 50 runs
    Set<CassandraHost> foundHosts = new HashSet<CassandraHost>(3);
    for (int i = 0; i < 50; i++) {
      HClientPool foundPool = leastActiveBalancingPolicy.getPool(pools, null);
      foundHosts.add(foundPool.getCassandraHost());
      assert 5 == foundPool.getNumActive();
    }
    assertEquals(3, foundHosts.size());
  }

  @Test
  public void testShufflingCompareStability() {
    final int POOL_SIZE = 360;
    List<HClientPool> pools = new ArrayList<HClientPool>(POOL_SIZE);
    for (int i = 0; i < POOL_SIZE; i++) {
      pools.add(new TestPool());
    }
    // Do it enough times and it will fail
    for (int i = 0; i < 50; i++) {
      Collections.shuffle(pools);
      Collections.sort(pools, new LeastActiveBalancingPolicy.ShufflingCompare());
    }

  }
  private static class TestPool implements HClientPool {
    private Random rand = new Random();

    public int getNumActive() {
      return rand.nextInt(30);
    }

    public int getNumIdle() {
        return 0;
    }

    public int getNumBlockedThreads() {
        return 0;
    }

    public String getName() {
        return null;
    }

    public boolean getIsActive() {
        return false;
    }

    public long getExhaustedTime() {
        return 0;
    }

    public HClient borrowClient() throws HectorException {
        return null;
    }

    public CassandraHost getCassandraHost() {
        return null;
    }

    public int getNumBeforeExhausted() {
        return 0;
    }

    public boolean isExhausted() {
        return false;
    }

    public int getMaxActive() {
        return 0;
    }

    public String getStatusAsString() {
        return null;
    }

    public void releaseClient(HClient client) throws HectorException {

    }

    public void shutdown() {

    }
  }
}
