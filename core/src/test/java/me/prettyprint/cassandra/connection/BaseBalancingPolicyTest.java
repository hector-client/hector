package me.prettyprint.cassandra.connection;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Before;
import org.mockito.Mockito;

public abstract class BaseBalancingPolicyTest {
  protected List<HClientPool> pools = new ArrayList<HClientPool>();
  
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
    
    Mockito.when(poolWith5Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.1:9160"));
    Mockito.when(poolWith7Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.2:9161"));
    Mockito.when(poolWith10Active.getCassandraHost()).thenReturn(new CassandraHost("127.0.0.3:9162"));
    
    pools.add(poolWith5Active);        
    pools.add(poolWith7Active);
    pools.add(poolWith10Active);
  }
  
}
