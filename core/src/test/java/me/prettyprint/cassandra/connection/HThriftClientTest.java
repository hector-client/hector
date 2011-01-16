package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HThriftClientTest extends BaseEmbededServerSetupTest {

  private HThriftClient hThriftClient;
  // cassandraHostConfigurator = new CassandraHostConfigurator("127.0.0.1:9170");
  private CassandraHost cassandraHost;
  
  @Before
  public void doSetup() {
    cassandraHost = new CassandraHost("127.0.0.1:9170");
    hThriftClient = new HThriftClient(cassandraHost);
  }
  
  @After
  public void doTeardown() {
    hThriftClient.close();
  }
  
  @Test
  public void testOpenAndClose() {
    assertTrue(hThriftClient.open().isOpen());
    assertFalse(hThriftClient.close().isOpen());
  }

  @Test(expected=IllegalStateException.class)
  public void testFailOnDoubleOpen() {
    hThriftClient.open().open();
  }
  
  @Test(expected=IllegalStateException.class)
  public void testGetCassandraNotOpen() {
    hThriftClient.getCassandra();
  }
  
  @Test
  public void testGetCassandraWithKeyspace() {
    hThriftClient.open();
    hThriftClient.getCassandra("Keyspace1");
    assertTrue(hThriftClient.isOpen());
  }
  
  @Test
  public void testGetCassandraWithNullKeyspace() {
    hThriftClient.open();
    hThriftClient.getCassandra("Keyspace1");
    hThriftClient.getCassandra(null);
    assertTrue(hThriftClient.isOpen());
  }
  
}
