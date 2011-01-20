package me.prettyprint.hector.api;

import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftColumnDef;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class KeyspaceCreationTest extends BaseEmbededServerSetupTest {

  private static final Logger log = LoggerFactory
      .getLogger(KeyspaceCreationTest.class);
  private final static String KEYSPACE = "Keyspace2";
  private static final StringSerializer se = new StringSerializer();
  private Cluster cluster;

  private static final int colCount = 6;

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
  }

  @After
  public void teardownCase() {
    cluster = null;
  }

  @Test
  public void testCreateKeyspace() throws InvalidRequestException, TException {

    List<ColumnFamilyDefinition> cf_defs = new ArrayList<ColumnFamilyDefinition>();

    List<ColumnDef> columns = new ArrayList<ColumnDef>();
    for (int i = 0; i < colCount; i++) {
      String cName = "col" + i;
      log.info("Creating column " + cName);
      columns.add(newIndexedColumnDef(cName, "BytesType"));
    }

    List<ColumnDefinition> columnMetadata = ThriftColumnDef
        .fromThriftList(columns);

    ColumnFamilyDefinition cf_def = HFactory.createColumnFamilyDefinition(
        KEYSPACE, "TEST_CF", ComparatorType.BYTESTYPE, columnMetadata);

    cf_defs.add(cf_def);

    makeKeyspace(KEYSPACE, cf_defs);

    checkKeyspaces();

    log.info("Done, all errors to console after this point likely due to shutdown");

  }

  public void checkKeyspaces() {

    List<KeyspaceDefinition> ksDefs = null;
    try {
      ksDefs = cluster.describeKeyspaces();
    } catch (Exception e) {
      log.error("Unable to describe keyspaces", e);
    }

    if (ksDefs != null) {
      for (KeyspaceDefinition ksDef : ksDefs) {
        log.info(ksDef.getName().toString());
      }
    }

  }

  ColumnDef newIndexedColumnDef(String column_name, String comparer) {
    ColumnDef cd = new ColumnDef(se.toByteBuffer(column_name), comparer);
    cd.setIndex_name(column_name);
    cd.setIndex_type(IndexType.KEYS);
    return cd;
  }

  public void makeKeyspace(String keyspace, List<ColumnFamilyDefinition> cf_defs)
      throws InvalidRequestException, TException {

    log.info("Creating keyspace: " + keyspace);
    try {
      KeyspaceDefinition ks_def = HFactory.createKeyspaceDefinition(keyspace,
          "org.apache.cassandra.locator.SimpleStrategy", 1, cf_defs);

      cluster.addKeyspace(ks_def);

      log.info("Created keyspace: " + keyspace);
    } catch (Exception e) {
      log.error("Unable to create keyspace " + keyspace, e);
    }
  }

}
