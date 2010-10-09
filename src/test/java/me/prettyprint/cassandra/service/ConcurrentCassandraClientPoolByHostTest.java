package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;

public class ConcurrentCassandraClientPoolByHostTest extends BaseEmbededServerSetupTest {
    
  private CassandraHost cassandraHost;
  private ConcurrentCassandraClientPoolByHost clientPool;
  
  @Before
  public void setupTest() {
    setupClient();
    cassandraHost = cassandraHostConfigurator.buildCassandraHosts()[0];
    clientPool = new ConcurrentCassandraClientPoolByHost(cassandraHost, 
        pools, 
        JmxMonitor.INSTANCE.getCassandraMonitor());
  }
  
  @Test
  public void testSpinUp() {
    assertEquals(16, clientPool.getNumIdle());
    assertEquals(50, clientPool.getNumBeforeExhausted());
    assertEquals(0, clientPool.getNumBlockedThreads());
    assertEquals(0, clientPool.getNumActive());
  }
  
  @Test
  public void testInvalidateAll() {
    clientPool.invalidateAll();
    assertEquals(0, clientPool.getNumIdle());
    assertEquals(0, clientPool.getNumBlockedThreads());
    assertEquals(0, clientPool.getNumActive());
  }
  
  @Test
  public void testBorrowRelease() {
    CassandraClient client = clientPool.borrowClient();
    assertEquals(1, clientPool.getNumActive());
    clientPool.releaseClient(client);
    assertEquals(0, clientPool.getNumActive());
  }
}
