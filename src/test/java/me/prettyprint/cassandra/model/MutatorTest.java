package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.createSuperColumn;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.utils.StringUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;

import org.apache.cassandra.thrift.ColumnPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MutatorTest extends BaseEmbededServerSetupTest {

  private static final StringSerializer se = new StringSerializer();

  private Cluster cluster;
  private Keyspace keyspace;

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
    keyspace = createKeyspace("Keyspace1", cluster);
  }

  @After
  public void teardownCase() {
    keyspace = null;
    cluster = null;
  }

  @Test
  public void testInsert() {
    Mutator<String> m = createMutator(keyspace, se);
    MutationResult mr = m.insert("k", "Standard1", createColumn("name", "value", se, se));
    assertTrue("Execution time on single insert should be > 0",mr.getExecutionTimeMicro() > 0);
    assertTrue("Should have operated on a host", mr.getHostUsed() != null);
    assertColumnExists("Keyspace1", "Standard1", "k", "name");
  }

  @Test
  public void testInsertSuper() {
    Mutator<String> m = createMutator(keyspace, se);
    List<HColumn<String, String>> columnList = new ArrayList<HColumn<String,String>>();
    columnList.add(createColumn("name","value",se,se));
    HSuperColumn<String, String, String> superColumn =
        createSuperColumn("super_name", columnList, se, se, se);
    MutationResult r = m.insert("sk", "Super1", superColumn);
    assertTrue("Execute time should be > 0", r.getExecutionTimeMicro() > 0);
    assertTrue("Should have operated on a host", r.getHostUsed() != null);
  }

  @Test
  public void testBatchMutationManagement() {
    String cf = "Standard1";

    Mutator<String> m = createMutator(keyspace, se);
    for (int i = 0; i < 5; i++) {
      m.addInsertion("k" + i, cf, createColumn("name", "value" + i, se, se));
    }
    MutationResult r = m.execute();
    assertTrue("Execute time should be > 0", r.getExecutionTimeMicro() > 0);
    assertTrue("Should have operated on a host", r.getHostUsed() != null);

    for (int i = 0; i < 5; i++) {
      assertColumnExists("Keyspace1", "Standard1", "k"+i, "name");
    }
    // Execute an empty mutation
    r = m.execute();
    assertEquals("Execute time should be 0", 0, r.getExecutionTimeMicro());

    // Test discard and then exec an empty mutation
    for (int i = 0; i < 5; i++) {
      m.addInsertion("k" + i, cf, createColumn("name", "value" + i, se, se));
    }
    m.discardPendingMutations();
    r = m.execute();
    assertEquals("Execute time should be 0", 0, r.getExecutionTimeMicro());
    assertTrue("Should have operated with a null host", r.getHostUsed() == null);

    // cleanup
    for (int i = 0; i < 5; i++) {
      m.addDeletion("k" + i, cf, "name", se);
    }
    m.execute();
  }

  @Test
  public void testRowDeletion() {
    String cf = "Standard1";

    Mutator<String> m = createMutator(keyspace, se);
    for (int i = 0; i < 5; i++) {
      m.addInsertion("k" + i, cf, createColumn("name", "value" + i, se, se));
    }
    MutationResult r = m.execute();

    for (int i = 0; i < 5; i++) {
      m.addDeletion("k" + i, cf, null, se);
    }
    m.execute();
  }

  private void assertColumnExists(String keyspace, String cf, String key, String column) {
    ColumnPath cp = new ColumnPath(cf);
    cp.setColumn(StringUtils.bytes(column));
    CassandraClient client = cluster.borrowClient();
    assertNotNull(String.format("Should have value for %s.%s[%s][%s]", keyspace, cf, key, column),
        client.getKeyspace(keyspace).getColumn(key, cp));
    cluster.releaseClient(client);
  }

}
