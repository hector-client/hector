package me.prettyprint.cassandra.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import me.prettyprint.cassandra.service.CassandraClientPool.ExhaustedPolicy;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientPoolTest {

  private CassandraClientPool pool;
  private CassandraClientFactory factory;
  private CassandraClientPoolStore poolStore;

  @Before
  public void setupTest() throws Exception {
    factory = mock(CassandraClientFactory.class);
    poolStore = mock(CassandraClientPoolStore.class);
    CassandraClient createdClient = mock(CassandraClient.class);
    when(factory.makeObject()).thenReturn(createdClient);

    pool = new CassandraClientPoolImpl("url", 1111, poolStore, 50 /*maxActive*/,
        10000 /*maxWait*/, 5 /*maxIdle*/, ExhaustedPolicy.WHEN_EXHAUSTED_FAIL,
        factory);
  }

  @Test
  public void testCounters() throws IllegalStateException, PoolExhaustedException, Exception {

    assertEquals(0, pool.getNumIdle());
    assertEquals(50, pool.getNumBeforeExhausted());
    assertEquals(0, pool.getNumActive());

    // now borrow one client
    CassandraClient c = pool.borrowClient();
    assertEquals(0, pool.getNumIdle());
    assertEquals(49, pool.getNumBeforeExhausted());
    assertEquals(1, pool.getNumActive());

    // And release it
    pool.releaseClient(c);
    assertEquals(1, pool.getNumIdle());
    assertEquals(50, pool.getNumBeforeExhausted());
    assertEquals(0, pool.getNumActive());
  }

  @Test
  public void testBorrowClient() throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClient client = pool.borrowClient();
    assertNotNull(client);
    pool.releaseClient(client);

    // now try to exhaust a pool
    for (int i = 0; i < 51; ++i) {
      try {
        client = pool.borrowClient();
        assertNotNull("After iteration " + i + " the returned client is null", client);
        if (i > 49) {
          fail("Pool should have been exhausted at 49. Now i=" + i);
        }
      } catch (PoolExhaustedException e) {
        assertEquals("Shoudld be exhausted at 50", 50, i);
      }
    }
  }

  @Test
  public void testReleaseClient() throws IllegalStateException, PoolExhaustedException, Exception {
    //  borrow one client
    CassandraClient c = pool.borrowClient();
    assertEquals(0, pool.getNumIdle());
    assertEquals(49, pool.getNumBeforeExhausted());
    assertEquals(1, pool.getNumActive());

    // And release it
    pool.releaseClient(c);
    assertEquals(1, pool.getNumIdle());
    assertEquals(50, pool.getNumBeforeExhausted());
    assertEquals(0, pool.getNumActive());

    // And borrow again
    c = pool.borrowClient();
    assertEquals(0, pool.getNumIdle());
    assertEquals(49, pool.getNumBeforeExhausted());
    assertEquals(1, pool.getNumActive());
    // and again
    c = pool.borrowClient();
    assertEquals(0, pool.getNumIdle());
    assertEquals(48, pool.getNumBeforeExhausted());
    assertEquals(2, pool.getNumActive());
    // And release one
    pool.releaseClient(c);
    assertEquals(1, pool.getNumIdle());
    assertEquals(49, pool.getNumBeforeExhausted());
    assertEquals(1, pool.getNumActive());
  }

  @Test
  public void testClose() throws PoolExhaustedException, Exception {
    pool.close();
    // This should not throw an exception
    pool.close();

    // This should throw an exception
    try {
      pool.borrowClient();
      fail("The borrowClient should have failed with IllegalStateException");
    } catch (IllegalStateException e) {
      // OK
    }
  }
}
