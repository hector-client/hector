package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;

import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;


public class CassandraClusterTest extends BaseEmbededServerSetupTest { 
  
  private Cluster cassandraCluster;
  private CassandraHostConfigurator cassandraHostConfigurator;

  
  @Before
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
          NotFoundException, UnknownHostException, Exception {
    cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");        
    cassandraCluster = new Cluster("Test Cluster", cassandraHostConfigurator);
  }
  
  @Test
  public void testDescribeKeyspaces() throws Exception {
    List<KsDef> keyspaces = cassandraCluster.describeKeyspaces();
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
    assertEquals("10.0.0",cassandraCluster.describeThriftVersion());
  }

  @Test
  public void testDescribeRing() throws Exception {
    List<TokenRange> ring = cassandraCluster.describeRing("Keyspace1");
    assertEquals(1, ring.size());
  }
  
  
  
  @Test
  public void testDescribeKeyspace() throws Exception {
    KsDef keyspaceDetail = cassandraCluster.describeKeyspace("Keyspace1");
    assertNotNull(keyspaceDetail);
    assertEquals(6,keyspaceDetail.cf_defs.size());    
  }
  
}
