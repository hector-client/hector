package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Before;
import org.junit.Test;

public class ConcurrentHClientPoolTest extends BaseEmbededServerSetupTest {
    
  private CassandraHost cassandraHost;
  private ConcurrentHClientPool clientPool;
  
  @Before
  public void setupTest() {
    setupClient();
    cassandraHost = cassandraHostConfigurator.buildCassandraHosts()[0];
    clientPool = new ConcurrentHClientPool(cassandraHost);
  }
  
  @Test
  public void testSpinUp() {
    assertEquals(16, clientPool.getNumIdle());
    assertEquals(50, clientPool.getNumBeforeExhausted());
    assertEquals(0, clientPool.getNumBlockedThreads());
    assertEquals(0, clientPool.getNumActive());
  }
  
  @Test
  public void testShutdown() {
    clientPool.shutdown();
    assertEquals(0, clientPool.getNumIdle());
    assertEquals(0, clientPool.getNumBlockedThreads());
    assertEquals(0, clientPool.getNumActive());
  }
  
  @Test
  public void testBorrowRelease() {
    HThriftClient client = clientPool.borrowClient();
    assertEquals(1, clientPool.getNumActive());
    clientPool.releaseClient(client);
    assertEquals(0, clientPool.getNumActive());
  }
}
