package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultigetCountQueryTest extends BaseEmbededServerSetupTest {

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
  public void testMultigetCount() {
    String cf = "Standard1";

    Mutator<String> m = createMutator(keyspace, se);
    List<String> keys = new ArrayList<String>();
    for (int i = 0; i < 5; i++) {
      String key = "k"+i;
      m.addInsertion(key, cf, createColumn("name", "value" + i, se, se));
      keys.add(key);
    }
    m.execute();

    MultigetCountQuery<String, String> mcq = new MultigetCountQuery<String, String>(keyspace, se, se);
    mcq.setColumnFamily(cf);
    mcq.setColumnNames("name");
    mcq.setKeys(keys.toArray(new String[]{}));
    QueryResult<Map<String, Integer>> result = mcq.execute();
    assertEquals(5,result.get().size());
  }

  public void testMultigetSuperCount() {

  }

  public void testMultigetSubCount() {

  }

}
