package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import org.junit.Test;


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
  
}
