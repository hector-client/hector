package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.connection.factory.HThriftClientFactoryImpl;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.hector.api.exceptions.HInactivePoolException;

import org.junit.Before;
import org.junit.Test;

public class ConcurrentHClientPoolTest extends BaseEmbededServerSetupTest {
    
  private CassandraHost cassandraHost;
  private ConcurrentHClientPool clientPool;
  
  @Before
  public void setupTest() {
    setupClient();
    cassandraHost = cassandraHostConfigurator.buildCassandraHosts()[0];
    HClientFactory factory = new HThriftClientFactoryImpl();
    clientPool = new ConcurrentHClientPool(factory, cassandraHost, new CassandraClientMonitor(connectionManager));
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
    
    try {
      clientPool.borrowClient();
      fail();
    } catch (HInactivePoolException e) {
      // Good !
    }
  }
  
  @Test
  public void testBorrowRelease() {
    HClient client = clientPool.borrowClient();
    assertEquals(1, clientPool.getNumActive());
    clientPool.releaseClient(client);
    assertEquals(0, clientPool.getNumActive());
  }
}
