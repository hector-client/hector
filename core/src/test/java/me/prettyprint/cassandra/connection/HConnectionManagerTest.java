package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

public class HConnectionManagerTest extends BaseEmbededServerSetupTest {

  
  @Before
  public void setupTest() {
    setupClient();
  }
  
  @Test
  public void testRemoveHost() {
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9170);
    connectionManager.removeCassandraHost(cassandraHost);
    assertEquals(0,connectionManager.getActivePools().size());
    assertTrue(connectionManager.addCassandraHost(cassandraHost));
    assertEquals(1,connectionManager.getActivePools().size());
  }
  
  @Test 
  public void testAddCassandraHostFail() {
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9180);
    assertFalse(connectionManager.addCassandraHost(cassandraHost));
  }
}
