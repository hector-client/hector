package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;


public class CassandraClusterTest extends BaseEmbededServerSetupTest {

  private CassandraClient cassandraClient;
  private JmxMonitor jmxMonitor;
  private Keyspace keyspace;
  private CassandraClientPool cassandraClientPool;
  private CassandraClientMonitor cassandraClientMonitor;
  
  @Before
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
          NotFoundException, UnknownHostException {
    jmxMonitor = JmxMonitor.getInstance();
    cassandraClientMonitor = jmxMonitor.getCassandraMonitor();
    cassandraClientPool = CassandraClientPoolFactory.INSTANCE.createDefault();
    cassandraClient = new CassandraClientFactory(cassandraClientPool,
        new CassandraHost("localhost", 9170), cassandraClientMonitor).create();
    keyspace = cassandraClient.getKeyspace("Keyspace1", ConsistencyLevel.ONE,
        CassandraClient.DEFAULT_FAILOVER_POLICY);
  }
  
  @Test
  public void testDescribeKeyspaces() throws TTransportException, TException, UnknownHostException {
    CassandraCluster cassandraCluster = new CassandraClusterFactory(cassandraClient).create();
    assertEquals(2,cassandraCluster.describeKeyspaces().size());
  }
  
  @Test
  public void testDescribeClusterName() throws TTransportException, TException, UnknownHostException {
    CassandraCluster cassandraCluster = new CassandraClusterFactory(cassandraClient).create();
    assertEquals("Test Cluster",cassandraCluster.describeClusterName());
  }
  
  /**
   * This will need to be updated as we update the Thrift API, but probably a good sanity check
   * 
   */
  @Test
  public void testDescribeVersion() throws TTransportException, TException, UnknownHostException {
    CassandraCluster cassandraCluster = new CassandraClusterFactory(cassandraClient).create();
    assertEquals("2.1.0",cassandraCluster.describeVersion());
  }

  @Test
  public void testDescribeRing() throws TTransportException, TException, UnknownHostException {
    CassandraCluster cassandraCluster = new CassandraClusterFactory(cassandraClient).create();
    List<TokenRange> ring = cassandraCluster.describeRing(keyspace);
    assertEquals(1, ring.size());
  }
  
  @Test
  public void testGetHostNames() throws TTransportException, TException, UnknownHostException {
    CassandraCluster cassandraCluster = new CassandraClusterFactory(cassandraClient).create();
    Set<String> hosts = cassandraCluster.getHostNames();
    assertEquals(1, hosts.size());
  }
  
}
