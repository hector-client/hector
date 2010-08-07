package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.PoolExhaustedException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.thrift.transport.TFramedTransport;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientTest extends BaseEmbededServerSetupTest {

  private CassandraClient client;


  @Before
  public void setupCase() throws IllegalStateException, PoolExhaustedException, Exception {
    super.setupClient();
    client = new CassandraClientFactory(pools,
        new CassandraHost("127.0.0.1", 9170), JmxMonitor.getInstance().getCassandraMonitor()).create();    
  }

  @Test
  public void testGetKeySpaceString() throws HectorException {
    Keyspace k = client.getKeyspace("Keyspace1");
    assertNotNull(k);
    assertEquals(CassandraClient.DEFAULT_CONSISTENCY_LEVEL, k.getConsistencyLevel());

    // negative path
    try {
      k = client.getKeyspace("KeyspaceDoesntExist");
      fail("Should have thrown an exception IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // good
    }
  }

  @Test
  public void testGetKeySpaceConsistencyLevel() throws HectorException {
    Keyspace k = client.getKeyspace("Keyspace1", ConsistencyLevel.ALL,
        CassandraClient.DEFAULT_FAILOVER_POLICY);
    assertNotNull(k);
    assertEquals(ConsistencyLevel.ALL, k.getConsistencyLevel());

    k = client.getKeyspace("Keyspace1", ConsistencyLevel.ZERO,
        CassandraClient.DEFAULT_FAILOVER_POLICY);
    assertNotNull(k);
    assertEquals(ConsistencyLevel.ZERO, k.getConsistencyLevel());
  }

  @Test
  public void testGetKeySpaceFailoverPolicy() throws HectorException {
    Keyspace k = client.getKeyspace("Keyspace1", CassandraClient.DEFAULT_CONSISTENCY_LEVEL,
        FailoverPolicy.FAIL_FAST);
    assertNotNull(k);
    assertEquals(FailoverPolicy.FAIL_FAST, k.getFailoverPolicy());
  }

  @Test
  public void testGetKeyspaces() throws HectorException {
    List<String> spaces = client.getKeyspaces();
    assertNotNull(spaces);
    // There should be two spaces: Keyspace1 and system
    assertEquals(2, spaces.size());
    assertTrue("Keyspace1".equals(spaces.get(0)) || "Keyspace1".equals(spaces.get(1)));
  }

  @Test
  public void testGetClusterName() throws HectorException {
    String name = client.getClusterName();
    assertEquals("Default Cluster", name);
  }

  @Test 
  public void testFramedTransport() throws HectorException {
    CassandraHost cassandraHost = new CassandraHost("localhost", 9170);
    cassandraHost.setUseThriftFramedTransport(true);
    client = new CassandraClientFactory(pools, cassandraHost, JmxMonitor.getInstance().getCassandraMonitor()).create();
    assertTrue(client.getCassandra().getInputProtocol().getTransport() instanceof TFramedTransport);
  }
}
