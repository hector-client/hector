package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientPoolTest extends BaseEmbededServerSetupTest {

  private CassandraClientPoolImpl store;

  @Before
  public void setupTest() {
    store = (CassandraClientPoolImpl) CassandraClientPoolFactory.INSTANCE.get();
  }

  @Test
  public void testGetPool() {
    CassandraClientPoolByHost pool = store.getPool(new CassandraHost("x", 1));
    assertNotNull(pool);
  }

  @Test
  public void testBorrowClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    assertEquals("localhost:9170", client.getCassandraHost().getUrl());
  }

  /**
   * test the url:port format
   */
  @Test
  public void testBorrowClient1() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost:9170");
    assertNotNull(client);
    assertEquals("localhost:9170", client.getCassandraHost().getUrl());
  }

  /**
   * test the url:port array format (load balanced)
   */
  @Test
  public void testBorrowLbClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient(new String[] {"localhost:9170"});
    assertNotNull(client);
    assertEquals("localhost:9170", client.getCassandraHost().getUrl()); 


    client = store.borrowClient(new String[] {"localhost:9170", "127.0.0.1:9170", "localhost:9170"});
    assertNotNull(client);
    assertEquals("localhost:9170", client.getCassandraHost().getUrl()); 
    
    client = store.borrowClient(new String[] {"localhost:9170", "127.0.0.1:9170"});
    assertEquals("localhost:9170", client.getCassandraHost().getUrl());
  }

  @Test
  public void testBorrowExistingClient() throws IllegalStateException, PoolExhaustedException, Exception {
    // make sure we always have at least one good existing client
    CassandraClient client = store.borrowClient(new String[] {"localhost:9170"});
    store.releaseClient(client);
    client = store.borrowClient();
    assertNotNull(client);
    assertEquals("localhost:9170", client.getCassandraHost().getUrl());

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
    CassandraHost cassandraHost = client.getCassandraHost();
    assertNotNull(client);
    KeyspaceService ks = client.getKeyspace("Keyspace1");
    assertNotNull(ks);
    System.out.print(store.getKnownHosts());
    assertTrue("127.0.0.1 is in not in knownHosts", store.getKnownHosts().contains(cassandraHost));    
    store.updateKnownHosts();
    assertTrue("127.0.0.1 is in not in knownHosts", store.getKnownHosts().contains(cassandraHost));
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
    CassandraHost cassandraHost = new CassandraHost("localhost", 9170);
    assertFalse("client1 is not in the liveClients set anymore",
        store.getPool(cassandraHost).getLiveClients().contains(client1));
    assertTrue("client2 is in the liveClients set anymore",
        store.getPool(cassandraHost).getLiveClients().contains(client2));
  }

  @Test
  public void testInvalidateAll()
      throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client1 = store.borrowClient("localhost", 9170);
    assertNotNull(client1);
    CassandraClient client2 = store.borrowClient("localhost", 9170);
    assertNotNull(client2);
    assertNotSame(client1, client2);
    store.invalidateAllConnectionsToHost(client1);
    assertTrue(client1.hasErrors());
    assertTrue(client2.hasErrors());

    // now borrow a new client and make sure it's not one of the old clients
    CassandraClient client3 = store.borrowClient("localhost", 9170);
    assertNotNull(client3);
    assertNotSame(client1, client3);
    assertNotSame(client2, client3);

    CassandraHost cassandraHost = new CassandraHost("localhost", 9170);
    assertFalse("client1 should not be in the liveClients set anymore",
        store.getPool(cassandraHost).getLiveClients().contains(client1));
    assertFalse("client2 should not be in the liveClients set anymore",
        store.getPool(cassandraHost).getLiveClients().contains(client2));
    assertTrue("client3 should be in the liveClients set ",
        store.getPool(cassandraHost).getLiveClients().contains(client3));
  }
}
