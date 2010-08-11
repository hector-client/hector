package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.createSuperColumn;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.extractors.StringExtractor;
import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.utils.StringUtils;

import org.apache.cassandra.thrift.ColumnPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MutatorTest extends BaseEmbededServerSetupTest {

  private static final StringExtractor se = new StringExtractor();

  private Cluster cluster;
  private KeyspaceOperator keyspaceOperator;

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
    keyspaceOperator = createKeyspaceOperator("Keyspace1", cluster);
  }

  @After
  public void teardownCase() {
    keyspaceOperator = null;
    cluster = null;
  }

  @Test
  public void testInsert() {
    Mutator m = createMutator(keyspaceOperator);
    MutationResult mr = m.insert("k".getBytes(), "Standard1", createColumn("name", "value", se, se));
    assertTrue("Execution time on single insert should be > 0",mr.getExecutionTimeMicro() > 0);
    assertColumnExists("Keyspace1", "Standard1", "k", "name");
  }

  @Test
  public void testInsertSuper() {
    Mutator m = createMutator(keyspaceOperator);
    List<HColumn<String, String>> columnList = new ArrayList<HColumn<String,String>>();
    columnList.add(createColumn("name","value",se,se));
    HSuperColumn<String, String, String> superColumn =
        createSuperColumn("super_name", columnList, se, se, se);
    MutationResult r = m.insert("sk".getBytes(), "Super1", superColumn);
    assertTrue("Execute time should be > 0", r.getExecutionTimeMicro() > 0);
  }

  @Test
  public void testBatchMutationManagement() {
    String cf = "Standard1";

    Mutator m = createMutator(keyspaceOperator);
    for (int i = 0; i < 5; i++) {
      m.addInsertion(("k" + i).getBytes(), cf, createColumn("name", "value" + i, se, se));
    }
    MutationResult r = m.execute();
    assertTrue("Execute time should be > 0", r.getExecutionTimeMicro() > 0);

    for (int i = 0; i < 5; i++) {
      assertColumnExists("Keyspace1", "Standard1", "k"+i, "name");
    }
    // Execute an empty mutation
    r = m.execute();
    assertEquals("Execute time should be 0", 0, r.getExecutionTimeMicro());

    // Test discard and then exec an empty mutation
    for (int i = 0; i < 5; i++) {
      m.addInsertion(("k" + i).getBytes(), cf, createColumn("name", "value" + i, se, se));
    }
    m.discardPendingMutations();
    r = m.execute();
    assertEquals("Execute time should be 0", 0, r.getExecutionTimeMicro());

    // cleanup
    for (int i = 0; i < 5; i++) {
      m.addDeletion(("k" + i).getBytes(), cf, "name", se);
    }
    m.execute();
  }

  private void assertColumnExists(String keyspace, String cf, String key, String column) {
    ColumnPath cp = new ColumnPath(cf);
    cp.setColumn(StringUtils.bytes(column));
    CassandraClient client = cluster.borrowClient();
    assertNotNull(String.format("Should have value for %s.%s[%s][%s]", keyspace, cf, key, column),
        client.getKeyspace(keyspace).getColumn(key.getBytes(), cp));
    cluster.releaseClient(client);
  }

}
