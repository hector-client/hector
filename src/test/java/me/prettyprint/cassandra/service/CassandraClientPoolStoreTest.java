package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import me.prettyprint.cassandra.model.Keyspace;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientPoolStoreTest {

  private CassandraClientPoolStore store;
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
    store = new CassandraClientPoolStoreImpl();
  }

  @Test
  public void testGetPool() {
    CassandraClientPool pool = store.getPool("x", 1);
    assertNotNull(pool);
  }

  @Test
  public void testBorrowClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    assertEquals("localhost", client.getUrl());
    assertEquals(9170, client.getPort());
  }

  @Test
  public void testReleaseClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    store.releaseClient(client);
    assertTrue("Client should be closed", client.isClosed());
  }

  @Test
  public void testUpdateKnownHostsList()
      throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = store.borrowClient("localhost", 9170);
    assertNotNull(client);
    Keyspace ks = client.getKeySpace("Keyspace1");
    assertNotNull(ks);
    assertTrue("127.0.0.1 is in not in knownHosts", store.getKnownHosts().contains("127.0.0.1"));
    store.updateKnownHosts();
    assertTrue("127.0.0.1 is in not in knownHosts", store.getKnownHosts().contains("127.0.0.1"));
  }
}
