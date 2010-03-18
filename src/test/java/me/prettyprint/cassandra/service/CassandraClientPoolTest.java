package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientPoolTest {

  private CassandraClientPoolImpl store;
  private static EmbeddedServerHelper embedded;

  /**
   * Set embedded cassandra up and spawn it in a new thread.
   *
   * @throws TTransportException
   * @throws IOException
   * @throws InterruptedException
   */
  @BeforeClass
  public static void setup() throws TTransportException, IOException, InterruptedException {
    embedded = new EmbeddedServerHelper();
    embedded.setup();
  }

  @AfterClass
  public static void teardown() throws IOException {
    embedded.teardown();
  }

  @Before
  public void setupTest() {
    store = (CassandraClientPoolImpl) CassandraClientPoolFactory.INSTANCE.get();
  }

  @Test
  public void testGetPool() {
    CassandraClientPoolByHost pool = store.getPool("x", 1);
    assertNotNull(pool);
  }

  @Test
  public void testBorrowClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    assertEquals("localhost", client.getUrl());
    assertEquals(9170, client.getPort());
  }

  /**
   * test the url:port format
   */
  @Test
  public void testBorrowClient1() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost:9170");
    assertNotNull(client);
    assertEquals("localhost", client.getUrl());
    assertEquals(9170, client.getPort());
  }

  /**
   * test the url:port array format (load balanced)
   */
  @Test
  public void testBorrowLbClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient(new String[] {"localhost:9170"});
    assertNotNull(client);
    assertEquals("localhost", client.getUrl());
    assertEquals(9170, client.getPort());

    client = store.borrowClient(new String[] {"localhost:9170", "localhost:9171", "localhost:9172"});
    assertNotNull(client);
    assertEquals("localhost", client.getUrl());
    assertEquals(9170, client.getPort());

    try {
      client = store.borrowClient(new String[] {"localhost:9171", "localhost:9172"});
      fail("Should not have boon able to obtain a client");
    } catch (Exception e) {
      // ok
    }
}

  @Test
  public void testReleaseClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    store.releaseClient(client);
  }

  @Test
  public void testUpdateKnownHostsList()
      throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    Keyspace ks = client.getKeyspace("Keyspace1");
    assertNotNull(ks);
    assertTrue("127.0.0.1 is in not in knownHosts", store.getKnownHosts().contains("127.0.0.1"));
    store.updateKnownHosts();
    assertTrue("127.0.0.1 is in not in knownHosts", store.getKnownHosts().contains("127.0.0.1"));
  }

  @Test
  public void testInvalidateClient()
      throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client1 = store.borrowClient("localhost", 9170);
    assertNotNull(client1);
    CassandraClient client2 = store.borrowClient("localhost", 9170);
    assertNotNull(client2);
    assertNotSame(client1, client2);
    store.invalidateClient(client1);
    assertTrue(client1.hasErrors());
    
    // try to release a client after it's been invalidated
    store.releaseClient(client1);
    
    store.releaseClient(client2);
    assertFalse(client2.hasErrors());
    CassandraClient client3 = store.borrowClient("localhost", 9170);
    assertNotNull(client3);
    assertSame("client2 should have been reused", client2, client3);
    assertFalse("client1 is not in the liveClients set anymore",
        store.getPool("localhost", 9170).getLiveClients().contains(client1));
    assertTrue("client2 is in the liveClients set anymore",
        store.getPool("localhost", 9170).getLiveClients().contains(client2));
  }
}
