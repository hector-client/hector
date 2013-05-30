package me.prettyprint.cassandra.service;

import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;


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
	// System
	// Keyspace1
	// system_auth
	// system_traces
    assertEquals(4,keyspaces.size());
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
  @Ignore
  public void testDescribeThriftVersion() throws Exception {
    assertEquals("19.35.0",cassandraCluster.describeThriftVersion());
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
    assertEquals(22, keyspaceDetail.getCfDefs().size());
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

    // Let's wait for agreement
    cassandraCluster.addColumnFamily(cfDef, true);
    cfid2 = cassandraCluster.dropColumnFamily("Keyspace1", "DynCf", true);
    assertNotNull(cfid2);
  }

  @Test
  public void testTruncateColumnFamily() throws Exception {
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("Keyspace1", "TruncateableCf");
    cassandraCluster.addColumnFamily(cfDef);
    Keyspace workingKeyspace = HFactory.createKeyspace("Keyspace1", cassandraCluster);
    Mutator<String> mutator = HFactory.createMutator(workingKeyspace, StringSerializer.get());
    mutator.insert("mykey", "TruncateableCf", HFactory.createStringColumn("mycolname", "myval"));
    ColumnQuery<String,String,String> q = HFactory.createColumnQuery(workingKeyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
    q.setKey("mykey").setName("mycolname").setColumnFamily("TruncateableCf");
    assertEquals("myval",q.execute().get().getValue());
    cassandraCluster.truncate("Keyspace1", "TruncateableCf");
    assertNull(q.execute().get());
  }

  @Test
  public void testAddDropKeyspace() throws Exception {
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("DynKeyspace", "DynCf");
    cassandraCluster.addKeyspace(
        new ThriftKsDef("DynKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef)));

    String ksid2 = cassandraCluster.dropKeyspace("DynKeyspace");
    assertNotNull(ksid2);

    // Now let's wait for schema agreement.
    cassandraCluster.addKeyspace(new ThriftKsDef("DynKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef)), true);
    ksid2 = cassandraCluster.dropKeyspace("DynKeyspace", true);
    assertNotNull(ksid2);
  }

    @Test
  public void testAddKeyspaceNTS() throws Exception {
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("DynKeyspaceNTS", "DynCf");
    cassandraCluster.addKeyspace(
        new ThriftKsDef("DynKeyspaceNTS", "org.apache.cassandra.locator.NetworkTopologyStrategy", 1, Arrays.asList(cfDef)));

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
  public void testAddColumnDefinitionWhenNoneOnConstructor() {
		ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("blah-ks", "blah-cf", ComparatorType.BYTESTYPE);

		assertSame( Collections.emptyList(), cfDef.getColumnMetadata());

		//
		// column defs are not required but are nice for validating data and displaying meaningful values in
		// cassandra-cli
		//

		BasicColumnDefinition cd = new BasicColumnDefinition();
		cd.setName(ByteBuffer.wrap("colname".getBytes()));
		cd.setValidationClass("org.apache.cassandra.db.marshal.UTF8Type");
		cfDef.addColumnDefinition(cd);

		assertEquals( 1, cfDef.getColumnMetadata().size());
		assertEquals( cd, cfDef.getColumnMetadata().get(0));
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
    columnDefinition.setIndexName("birthdate_idx");
    columnDefinition.setIndexType(ColumnIndexType.KEYS);
    columnDefinition.setValidationClass(ComparatorType.LONGTYPE.getClassName());
    columnFamilyDefinition.addColumnDefinition(columnDefinition);

    columnDefinition = new BasicColumnDefinition();
    columnDefinition.setName(StringSerializer.get().toByteBuffer("nonindexed_field"));
    columnDefinition.setValidationClass(ComparatorType.LONGTYPE.getClassName());
    columnFamilyDefinition.addColumnDefinition(columnDefinition);

    cassandraCluster.updateColumnFamily(new ThriftCfDef(columnFamilyDefinition));

    fromCluster = cassandraCluster.describeKeyspace("DynKeyspace3");

    assertEquals("birthdate",StringSerializer.get().fromByteBuffer(fromCluster.getCfDefs().get(0).getColumnMetadata().get(0).getName()));
    assertEquals("birthdate_idx",fromCluster.getCfDefs().get(0).getColumnMetadata().get(0).getIndexName());
    assertEquals("nonindexed_field",StringSerializer.get().fromByteBuffer(fromCluster.getCfDefs().get(0).getColumnMetadata().get(1).getName()));
  }

  @Test
  public void testAddEmptyKeyspace() throws Exception {

    cassandraCluster.addKeyspace(new ThriftKsDef("DynKeyspaceEmpty"));
    assertNotNull(cassandraCluster.describeKeyspace("DynKeyspaceEmpty"));
    String ksid2 = cassandraCluster.dropKeyspace("DynKeyspaceEmpty");
    assertNotNull(ksid2);
  }

  @Test
  public void testAddEmptyBasicKeyspaceDefinition() throws Exception {
    BasicKeyspaceDefinition ksDef = new BasicKeyspaceDefinition();
    ksDef.setName("DynKeyspaceEmpty");
    ksDef.setReplicationFactor(1);
    ksDef.setStrategyClass("SimpleStrategy");

    cassandraCluster.addKeyspace(ksDef);
    assertNotNull(cassandraCluster.describeKeyspace("DynKeyspaceEmpty"));
    String ksid2 = cassandraCluster.dropKeyspace("DynKeyspaceEmpty");
    assertNotNull(ksid2);
  }

  @Test
  public void testEditBasicKeyspaceDefinition() throws Exception {
    BasicKeyspaceDefinition ksDef = new BasicKeyspaceDefinition();
    ksDef.setName("DynKeyspace4");
    ksDef.setReplicationFactor(1);
    ksDef.setStrategyClass("SimpleStrategy");

    cassandraCluster.addKeyspace(ksDef);
    assertNotNull(cassandraCluster.describeKeyspace("DynKeyspace4"));

    ksDef.setReplicationFactor(2);

    cassandraCluster.updateKeyspace(ksDef);

    KeyspaceDefinition fromCluster = cassandraCluster.describeKeyspace("DynKeyspace4");
    assertEquals(2, fromCluster.getReplicationFactor());
    cassandraCluster.dropKeyspace("DynKeyspace4");
  }

  @Test
  public void testAddDropBasicColumnFamilyDefinition() throws Exception {
    BasicColumnFamilyDefinition cfDef = new BasicColumnFamilyDefinition();
    cfDef.setName("DynCf");
    cfDef.setKeyspaceName("Keyspace1");

    cassandraCluster.addColumnFamily(cfDef);
    String cfid2 = cassandraCluster.dropColumnFamily("Keyspace1", "DynCf");
    assertNotNull(cfid2);
  }

  @Test
  public void testEditBasicColumnFamilyDefinition() throws Exception {
    BasicKeyspaceDefinition ksDef = new BasicKeyspaceDefinition();
    ksDef.setName("Keyspace2");
    ksDef.setReplicationFactor(1);
    ksDef.setStrategyClass("SimpleStrategy");

    cassandraCluster.addKeyspace(ksDef);

    BasicColumnFamilyDefinition cfDef = new BasicColumnFamilyDefinition();
    cfDef.setName("DynCf2");
    cfDef.setKeyspaceName("Keyspace2");

    cassandraCluster.addColumnFamily(cfDef);

    KeyspaceDefinition fromCluster = cassandraCluster.describeKeyspace("Keyspace2");
    cfDef = new BasicColumnFamilyDefinition(fromCluster.getCfDefs().get(0));

    cfDef.setDefaultValidationClass(ComparatorType.LONGTYPE.getClassName());
    cassandraCluster.updateColumnFamily(cfDef);

    String cfid2 = cassandraCluster.dropColumnFamily("Keyspace2", "DynCf2");
    assertNotNull(cfid2);
  }
}
