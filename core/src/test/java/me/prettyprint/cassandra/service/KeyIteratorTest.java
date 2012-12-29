package me.prettyprint.cassandra.service;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.mutation.Mutator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KeyIteratorTest extends BaseEmbededServerSetupTest {

  private static final StringSerializer se = new StringSerializer();
  private static final IntegerSerializer is = IntegerSerializer.get();
  
  private static final String CF = "Standard1";

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
  public void testIterator() {
    // Insert 100 rows
    Mutator<String> m = createMutator(keyspace, se);
    for (int i = 1; i <= 9; i++) {
      m.addInsertion("k" + i, CF, createColumn(new Integer(i), new Integer(i), is, is));
    }
    m.execute();

    assertKeys(5, "k5", null);
    assertKeys(9, null, null);
    assertKeys(7, null, "k7");

    assertStringKeys(5, "k5", null);
    assertStringKeys(9, null, null);
    assertStringKeys(7, null, "k7");

    assertKeys(5, "k5", 1);
    assertKeys(9, null, 2);
    assertKeys(9, null, 5);
    assertKeys(8, "k2", 7);
    assertKeys(7, "k3", 10);
  }

  private void assertKeys(int expected, String start, String end) {
    Iterable<String> it = new KeyIterator.Builder<String>(keyspace, CF, se).start(start).end(end).build();

    int tot = 0;
    for (String key : it)
      tot++;

    assertEquals(expected, tot);
  }

  private void assertStringKeys(int expected, String start, String end) {
    StringKeyIterator sk = new StringKeyIterator.Builder(keyspace, CF).start(start).end(end).build();

    int tot = 0;
    for (String key : sk)
      tot++;

    assertEquals(expected, tot);
  }

  private void assertKeys(int expected, String start, int count) {
    Iterable<String> it = new KeyIterator.Builder<String>(keyspace, CF, se).start(start).maxRowCount(count).build();

    int tot = 0;
    for (String key : it)
      tot++;

    assertEquals(expected, tot);
  }

}
