package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspaceOperator;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.Cluster;

public class MultigetCountQueryTest extends BaseEmbededServerSetupTest {
  
  private static final StringSerializer se = new StringSerializer();

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
  public void testMultigetCount() {
    String cf = "Standard1";

    Mutator<String> m = createMutator(keyspaceOperator, se);
    List<String> keys = new ArrayList<String>();
    for (int i = 0; i < 5; i++) {
      String key = "k"+i;
      m.addInsertion(key, cf, createColumn("name", "value" + i, se, se));
      keys.add(key);
    }
    m.execute();
    
    MultigetCountQuery<String, String> mcq = new MultigetCountQuery<String, String>(keyspaceOperator, se, se);
    mcq.setColumnFamily(cf);
    mcq.setColumnNames("name");
    mcq.setKeys(keys.toArray(new String[]{}));
    Result<Map<String, Integer>> result = mcq.execute();
    assertEquals(5,result.get().size());
  }
  
  public void testMultigetSuperCount() {
    
  }
  
  public void testMultigetSubCount() {
    
  }

}
