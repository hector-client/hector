package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class LeastActiveBalancingPolicyTest {

  private LeastActiveBalancingPolicy leastActiveBalancingPolicy;
  private List<ConcurrentHClientPool> pools = new ArrayList<ConcurrentHClientPool>();
  
  private ConcurrentHClientPool poolWith5Active;
  private ConcurrentHClientPool poolWith7Active;
  private ConcurrentHClientPool poolWith10Active;
  
  
  @Before
  public void setup() {
    poolWith5Active = Mockito.mock(ConcurrentHClientPool.class);    
    Mockito.when(poolWith5Active.getNumActive()).thenReturn(5);
    poolWith7Active = Mockito.mock(ConcurrentHClientPool.class);
    Mockito.when(poolWith7Active.getNumActive()).thenReturn(7);
    poolWith10Active = Mockito.mock(ConcurrentHClientPool.class);
    Mockito.when(poolWith10Active.getNumActive()).thenReturn(10);
    
    pools.add(poolWith5Active);
    pools.add(poolWith7Active);
    pools.add(poolWith10Active);
  }
  
  @Test
  public void testGetPoolOk() {
    leastActiveBalancingPolicy = new LeastActiveBalancingPolicy();
    assertEquals(poolWith5Active, leastActiveBalancingPolicy.getPool(pools, null));
  }
  
  @Test
  public void testSkipExhausted() {
    Mockito.when(poolWith5Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    Mockito.when(poolWith7Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    Mockito.when(poolWith10Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));
    
    leastActiveBalancingPolicy = new LeastActiveBalancingPolicy();
    assertEquals(poolWith7Active, leastActiveBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
  }
}
