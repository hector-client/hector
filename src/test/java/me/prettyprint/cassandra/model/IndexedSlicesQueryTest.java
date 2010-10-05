package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.query.QueryResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexedSlicesQueryTest extends BaseEmbededServerSetupTest {

  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private static final LongSerializer le = new LongSerializer();
  private Cluster cluster;
  private Keyspace keyspace;
  private String cf = "Indexed1";

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace(KEYSPACE, cluster);
    createMutator(keyspace, se)
    .addInsertion("indexedSlicesTest_key1", cf, createColumn("birthyear", 1974L, se, le))
    .addInsertion("indexedSlicesTest_key1", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("indexedSlicesTest_key2", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("indexedSlicesTest_key2", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("indexedSlicesTest_key3", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("indexedSlicesTest_key3", cf, createColumn("birthmonth", 5L, se, le))
    .addInsertion("indexedSlicesTest_key4", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("indexedSlicesTest_key4", cf, createColumn("birthmonth", 6L, se, le))
    .addInsertion("indexedSlicesTest_key5", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("indexedSlicesTest_key5", cf, createColumn("birthmonth", 7L, se, le))
    .addInsertion("indexedSlicesTest_key6", cf, createColumn("birthyear", 1976L, se, le))
    .addInsertion("indexedSlicesTest_key6", cf, createColumn("birthmonth", 6L, se, le))
    .execute();
  }

  @After
  public void teardownCase() {
    keyspace = null;
    cluster = null;
  }

  @Test
  public void testInsertGetRemove() {        

    IndexedSlicesQuery<String, String, Long> indexedSlicesQuery = new IndexedSlicesQuery<String, String, Long>(keyspace, se, se, le);
    indexedSlicesQuery.addEqualsExpression("birthyear", 1975L);
    indexedSlicesQuery.setColumnNames("birthyear");
    indexedSlicesQuery.setColumnFamily(cf);
    indexedSlicesQuery.setStartKey("");
    QueryResult<OrderedRows<String, String, Long>> result = indexedSlicesQuery.execute();
    assertEquals(4, result.get().getList().size());


  }
  
  @Test
  public void testMultiClause() {        

    IndexedSlicesQuery<String, String, Long> indexedSlicesQuery = new IndexedSlicesQuery<String, String, Long>(keyspace, se, se, le);
    indexedSlicesQuery.addEqualsExpression("birthyear", 1975L);
    indexedSlicesQuery.addGteExpression("birthmonth", 4L);
    indexedSlicesQuery.addLteExpression("birthmonth", 6L);
    indexedSlicesQuery.setColumnNames("birthyear");
    indexedSlicesQuery.setColumnFamily(cf);
    indexedSlicesQuery.setStartKey("");
    QueryResult<OrderedRows<String, String, Long>> result = indexedSlicesQuery.execute();
    assertEquals(3, result.get().getList().size());


  }


}
