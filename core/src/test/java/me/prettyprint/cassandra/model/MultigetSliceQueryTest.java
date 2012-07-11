package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

public class MultigetSliceQueryTest extends BaseEmbededServerSetupTest {
  
  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private static final LongSerializer le = new LongSerializer();
  private Cluster cluster;
  private Keyspace keyspace;
  private String cf = "Standard1";

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace(KEYSPACE, cluster);
    createMutator(keyspace, se)
    .addInsertion("multigetSliceTest_key1", cf, createColumn("birthyear", 1974L, se, le))
    .addInsertion("multigetSliceTest_key1", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("multigetSliceTest_key2", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("multigetSliceTest_key2", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("multigetSliceTest_key3", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("multigetSliceTest_key3", cf, createColumn("birthmonth", 5L, se, le))
    .addInsertion("multigetSliceTest_key4", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("multigetSliceTest_key4", cf, createColumn("birthmonth", 6L, se, le))
    .addInsertion("multigetSliceTest_key5", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("multigetSliceTest_key5", cf, createColumn("birthmonth", 7L, se, le))
    .addInsertion("multigetSliceTest_key6", cf, createColumn("birthyear", 1976L, se, le))
    .addInsertion("multigetSliceTest_key6", cf, createColumn("birthmonth", 6L, se, le))
    .execute();
  }
  
  @Test
  public void testNullKeyInList() {
    MultigetSliceQuery<String, String, Long> msq = HFactory.createMultigetSliceQuery(keyspace, se, se, le);
    msq.setColumnFamily(cf);
    msq.setKeys("multigetSliceTest_key1",null);
    msq.setColumnNames("birthyear");
    QueryResult<Rows<String, String, Long>> result = msq.execute();
    assertEquals(1,result.get().getCount());
  }
}
