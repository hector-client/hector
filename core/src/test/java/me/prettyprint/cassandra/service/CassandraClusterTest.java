package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Ignore;
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
    List<KeyspaceDefinition> keyspaces = cassandraCluster.describeKeyspaces();
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
    assertEquals("19.4.0",cassandraCluster.describeThriftVersion());
  }

  @Test
  public void testDescribeRing() throws Exception {
    List<TokenRange> ring = cassandraCluster.describeRing("Keyspace1");
    assertEquals(1, ring.size());
  }



  @Test
  public void testDescribeKeyspace() throws Exception {
    KeyspaceDefinition keyspaceDetail = cassandraCluster.describeKeyspace("Keyspace1");
    assertNotNull(keyspaceDetail);
    assertEquals(7, keyspaceDetail.getCfDefs().size());
  }

  @Test
  public void testDescribePartitioner() throws Exception {
    String partitioner = cassandraCluster.describePartitioner();
    assertEquals("org.apache.cassandra.dht.OrderPreservingPartitioner",partitioner);
  }

  @Test
  public void testAddDropColumnFamily() throws Exception {
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("Keyspace1", "DynCf");
    cassandraCluster.addColumnFamily(cfDef);
    String cfid2 = cassandraCluster.dropColumnFamily("Keyspace1", "DynCf");
    assertNotNull(cfid2);
  }

  @Test
  public void testAddDropKeyspace() throws Exception {
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("DynKeyspace", "DynCf");
    cassandraCluster.addKeyspace(
        new ThriftKsDef("DynKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef)));

    String ksid2 = cassandraCluster.dropKeyspace("DynKeyspace");
    assertNotNull(ksid2);
  }
  
  @Test
  public void testEditKeyspace() throws Exception {
        
    BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
    columnFamilyDefinition.setKeyspaceName("DynKeyspace2");
    columnFamilyDefinition.setName("DynamicCF");    
    
    ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);
    
    KeyspaceDefinition keyspaceDefinition = 
        HFactory.createKeyspaceDefinition("DynKeyspace2", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef));
    
    cassandraCluster.addKeyspace(keyspaceDefinition);
    
    keyspaceDefinition = 
      HFactory.createKeyspaceDefinition("DynKeyspace2", "org.apache.cassandra.locator.SimpleStrategy", 2, null);
    
    cassandraCluster.updateKeyspace(keyspaceDefinition);
    
    KeyspaceDefinition fromCluster = cassandraCluster.describeKeyspace("DynKeyspace2");
    assertEquals(2,fromCluster.getReplicationFactor());
    cassandraCluster.dropKeyspace("DynKeyspace2");
  }
  
  @Test
  public void testEditColumnFamily() throws Exception {
        
    BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
    columnFamilyDefinition.setKeyspaceName("DynKeyspace3");
    columnFamilyDefinition.setName("DynamicCF");    
    
    ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);
    
    KeyspaceDefinition keyspaceDefinition = 
        HFactory.createKeyspaceDefinition("DynKeyspace3", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef));
    
    cassandraCluster.addKeyspace(keyspaceDefinition);
    
    
    KeyspaceDefinition fromCluster = cassandraCluster.describeKeyspace("DynKeyspace3");
    cfDef = fromCluster.getCfDefs().get(0);
    
    columnFamilyDefinition = new BasicColumnFamilyDefinition(cfDef);
    BasicColumnDefinition columnDefinition = new BasicColumnDefinition();
    columnDefinition.setName(StringSerializer.get().toByteBuffer("birthdate"));
    columnDefinition.setIndexType(ColumnIndexType.KEYS);
    columnDefinition.setValidationClass(ComparatorType.LONGTYPE.getClassName());
    columnFamilyDefinition.addColumnDefinition(columnDefinition);
    
    
    cassandraCluster.updateColumnFamily(new ThriftCfDef(columnFamilyDefinition));
    
    fromCluster = cassandraCluster.describeKeyspace("DynKeyspace3");
    
    assertEquals("birthdate",StringSerializer.get().fromByteBuffer(fromCluster.getCfDefs().get(0).getColumnMetadata().get(0).getName()));
  }
}
