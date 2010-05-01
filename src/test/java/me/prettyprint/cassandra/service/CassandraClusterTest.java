package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CassandraClusterTest extends BaseEmbededServerSetupTest { 
  
  private CassandraCluster cassandraCluster;
  private CassandraHostConfigurator cassandraHostConfigurator;
  private CassandraClientPool cassandraClientPool;
  
  @Before
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
          NotFoundException, UnknownHostException, Exception {
    cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraClientPool = CassandraClientPoolFactory.INSTANCE.createNew(cassandraHostConfigurator);
    
    cassandraCluster = CassandraClusterFactory.getInstance().create(cassandraClientPool);    
  }
  
  @Test
  public void testDescribeKeyspaces() throws Exception {
    Set<String> keyspaces = cassandraCluster.describeKeyspaces();
    assertEquals(2,keyspaces.size());
  }
  
  @Test
  public void testDescribeClusterName() throws Exception {
    assertEquals("Test Cluster",cassandraCluster.describeClusterName());
  }
  
  /**
   * This will need to be updated as we update the Thrift API, but probably a good sanity check
   * 
   */
  @Test
  public void testDescribeThriftVersion() throws Exception {
    assertEquals("2.1.0",cassandraCluster.describeThriftVersion());
  }

  @Test
  public void testDescribeRing() throws Exception {
    List<TokenRange> ring = cassandraCluster.describeRing("Keyspace1");
    assertEquals(1, ring.size());
  }
  
  @Test
  public void testGetHostNames() throws Exception {
    Set<String> hosts = cassandraCluster.getHostNames();
    assertEquals(1, hosts.size());
  }
  
  @Test
  public void testDescribeKeyspace() throws Exception {
    Map<String, Map<String, String>> keyspaceDetail = cassandraCluster.describeKeyspace("Keyspace1");
    assertNotNull(keyspaceDetail);
    assertEquals(4,keyspaceDetail.size());    
  }
  
}
