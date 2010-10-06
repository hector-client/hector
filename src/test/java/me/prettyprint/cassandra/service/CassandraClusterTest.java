package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.hector.api.ddl.HCfDef;
import me.prettyprint.hector.api.ddl.HKsDef;

import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;


public class CassandraClusterTest extends BaseEmbededServerSetupTest {

  private ThriftCluster cassandraCluster;
  private CassandraHostConfigurator cassandraHostConfigurator;


  @Before
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
          NotFoundException, UnknownHostException, Exception {
    cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraCluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator);
  }

  @Test
  public void testDescribeKeyspaces() throws Exception {
    List<HKsDef> keyspaces = cassandraCluster.describeKeyspaces();
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
    assertEquals("17.1.0",cassandraCluster.describeThriftVersion());
  }

  @Test
  public void testDescribeRing() throws Exception {
    List<TokenRange> ring = cassandraCluster.describeRing("Keyspace1");
    assertEquals(1, ring.size());
  }



  @Test
  public void testDescribeKeyspace() throws Exception {
    HKsDef keyspaceDetail = cassandraCluster.describeKeyspace("Keyspace1");
    assertNotNull(keyspaceDetail);
    assertEquals(7, keyspaceDetail.getCfDefs().size());
  }

  @Test
  public void testDescribePartitioner() throws Exception {
    String partitioner = cassandraCluster.describePartitioner();
    assertEquals("org.apache.cassandra.dht.OrderPreservingPartitioner",partitioner);
  }

  @Test
  public void testAddDropColdropumnFamily() throws Exception {
    HCfDef cfDef = new ThriftCfDef("Keyspace1", "DynCf");
    String cfid = cassandraCluster.addColumnFamily(cfDef);
    cassandraCluster.renameColumnFamily("DynCf", "MyDynCf");
    assertNotNull(cfid);
    String cfid2 = cassandraCluster.dropColumnFamily("Keyspace1", "MyDynCf");
    assertNotNull(cfid2);
  }

  @Test
  public void testAddDropKeyspace() throws Exception {
    HCfDef cfDef = new ThriftCfDef("DynKeyspace", "DynCf");
    cassandraCluster.addKeyspace(
        new ThriftKsDef("DynKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef)));
    cassandraCluster.renameKeyspace("DynKeyspace", "MyDynKeyspace");
    String ksid2 = cassandraCluster.dropKeyspace("MyDynKeyspace");
    assertNotNull(ksid2);
  }
}
