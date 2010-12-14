package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Test;
import org.mockito.Mockito;

public class LeastActiveBalancingPolicyTest extends BaseBalancingPolicyTest {

  private LeastActiveBalancingPolicy leastActiveBalancingPolicy;
  
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
