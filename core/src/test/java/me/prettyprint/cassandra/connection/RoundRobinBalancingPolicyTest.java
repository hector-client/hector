package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Test;
import org.mockito.Mockito;


public class RoundRobinBalancingPolicyTest extends BaseBalancingPolicyTest {

  private RoundRobinBalancingPolicy roundRobinBalancingPolicy;
  
  @Test
  public void testGetPoolOk() {
    roundRobinBalancingPolicy = new RoundRobinBalancingPolicy();
    assertEquals(poolWith5Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith7Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith10Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith5Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith7Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith10Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith5Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith7Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    assertEquals(poolWith10Active.getNumActive(), roundRobinBalancingPolicy.getPool(pools, null).getNumActive());
    // go to 65k to roll the counter a couple of times
    for (int x=0; x<(256*256); x++) {
      assert roundRobinBalancingPolicy.getPool(pools, null).getNumActive() >= 5;      
    }
  }
  
  @Test
  public void testIgnoreExhausted() {
    Mockito.when(poolWith5Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    Mockito.when(poolWith7Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    Mockito.when(poolWith10Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));
    
    roundRobinBalancingPolicy = new RoundRobinBalancingPolicy();
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
  }
  
  @Test
  public void testIgnoreExhaustedAll() {
    Mockito.when(poolWith5Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    Mockito.when(poolWith7Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    Mockito.when(poolWith10Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));
    
    roundRobinBalancingPolicy = new RoundRobinBalancingPolicy();
    /*
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, 
        new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160"),new CassandraHost("127.0.0.2:9161")))));
    */
    assertNotNull(roundRobinBalancingPolicy.getPool(pools, 
        new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160"),new CassandraHost("127.0.0.2:9161"),new CassandraHost("127.0.0.3:9162")))));
    
    
  }
}
