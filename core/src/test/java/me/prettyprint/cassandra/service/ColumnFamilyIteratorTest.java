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
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.beans.HColumn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColumnFamilyIteratorTest extends BaseEmbededServerSetupTest {

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
    // Insert 10 rows
    Mutator<String> m = createMutator(keyspace, se);
    for (int i = 1; i <= 10; i++) {
        // For each row insert 1000 columns
        for (int j = 1; j <= 1000; j++) {
            m.addInsertion("k" + i, CF, createColumn("c" + j, new Integer(j), se, is));
        }
    }
    m.execute();

    ColumnFamilyIterator<String, String, Integer> it =
      new ColumnFamilyIterator<String, String, Integer>(keyspace, CF, se,
                                                         HFactory.createSliceQuery(keyspace, se, se, is),
                                                         2, 100);
    while(it.hasNext()) {
      it.next();
    }
    assertEquals(it.getTotalRowsCount(), 10);
    assertEquals(it.getTotalColumnsCount(), 10000);
  }

}
