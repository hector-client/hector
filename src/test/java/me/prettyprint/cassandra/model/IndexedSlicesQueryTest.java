package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.Cluster;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexedSlicesQueryTest extends BaseEmbededServerSetupTest {

  private static final Logger log = LoggerFactory.getLogger(IndexedSlicesQueryTest.class);
  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private static final LongSerializer le = new LongSerializer();
  private Cluster cluster;
  private KeyspaceOperator ko;

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    ko = createKeyspaceOperator(KEYSPACE, cluster);
  }

  @After
  public void teardownCase() {
    ko = null;
    cluster = null;
  }

  @Test
  public void testInsertGetRemove() {
    String cf = "Indexed1";

    MutationResult mr = createMutator(ko, se)
      .addInsertion("indexedSlicesTest_key1", cf, createColumn("birthdate", 1L, se, le))
      .addInsertion("indexedSlicesTest_key2", cf, createColumn("birthdate", 2L, se, le))
      .addInsertion("indexedSlicesTest_key3", cf, createColumn("birthdate", 3L, se, le))
      .addInsertion("indexedSlicesTest_key4", cf, createColumn("birthdate", 4L, se, le))
      .addInsertion("indexedSlicesTest_key5", cf, createColumn("birthdate", 5L, se, le))
      .execute();

    IndexedSlicesQuery<String, String, Long> indexedSlicesQuery = new IndexedSlicesQuery<String, String, Long>(ko, se, se, le);
    indexedSlicesQuery.addEqualsExpression("birthdate", 4L);
    indexedSlicesQuery.setColumnNames("birthdate");
    indexedSlicesQuery.setColumnFamily(cf);
    indexedSlicesQuery.setStartKey("");
    Result<OrderedRows<String, String, Long>> result = indexedSlicesQuery.execute();
    assertEquals(1, result.get().getList().size());


  }


}
