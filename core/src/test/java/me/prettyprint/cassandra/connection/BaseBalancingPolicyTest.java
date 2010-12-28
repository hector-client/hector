package me.prettyprint.cassandra.connection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.Mockito;

public abstract class BaseBalancingPolicyTest {
  protected List<ConcurrentHClientPool> pools = new ArrayList<ConcurrentHClientPool>();
  
  protected ConcurrentHClientPool poolWith5Active;
  protected ConcurrentHClientPool poolWith7Active;
  protected ConcurrentHClientPool poolWith10Active;
  
  
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
  
}
