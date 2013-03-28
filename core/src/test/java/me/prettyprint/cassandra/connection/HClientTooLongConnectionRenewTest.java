package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.connection.factory.HThriftClientFactoryImpl;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.exceptions.HInactivePoolException;

import org.junit.Before;
import org.junit.Test;

public class HClientTooLongConnectionRenewTest extends BaseEmbededServerSetupTest {
    
  private CassandraHost cassandraHost;
  private ConcurrentHClientPool clientPool;
  private CassandraClientMonitor monitor;
  
  @Before
  public void setupTest() {
    setupClient();
    cassandraHost = cassandraHostConfigurator.buildCassandraHosts()[0];
    HClientFactory factory = new HThriftClientFactoryImpl();
    monitor = new CassandraClientMonitor(connectionManager);
    clientPool = new ConcurrentHClientPool(factory, cassandraHost, monitor);
  } 
  
  protected void configure(CassandraHostConfigurator configurator) {
    configurator.setMaxActive(1);
    configurator.setMaxConnectTimeMillis(3 * 1000);
  }
  
  @Test
  public void testBorrowAndRenew() {
    HClient client1 = clientPool.borrowClient();
    assertEquals(1, clientPool.getNumActive());
	client1.updateLastSuccessTime();
    clientPool.releaseClient(client1);
    assertEquals(0, clientPool.getNumActive());
    int count = monitor.getNumRenewedTooLongConnections();
	try {
	  Thread.sleep(4 * 1000);
    } catch(InterruptedException ex) {
	  fail();
	}
    HClient client2 = clientPool.borrowClient();
    assertEquals(1, clientPool.getNumActive());
    assertEquals(count + 1, monitor.getNumRenewedTooLongConnections());
	assertNotSame(client1, client2);
  }
}
