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
    assertEquals(poolWith5Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith5Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith5Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, null));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, null));    
  }
  
  @Test
  public void testSkipExhausted() {
    Mockito.when(poolWith5Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    Mockito.when(poolWith7Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    Mockito.when(poolWith10Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));
    
    roundRobinBalancingPolicy = new RoundRobinBalancingPolicy();
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith7Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
    assertEquals(poolWith10Active, roundRobinBalancingPolicy.getPool(pools, new HashSet<CassandraHost>(Arrays.asList(new CassandraHost("127.0.0.1:9160")))));
  }
  
}
