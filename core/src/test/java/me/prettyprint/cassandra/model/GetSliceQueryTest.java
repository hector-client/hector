package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.junit.Before;
import org.junit.Test;

public class GetSliceQueryTest extends BaseEmbededServerSetupTest {
  
  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private static final LongSerializer le = new LongSerializer();
  private Cluster cluster;
  private Keyspace keyspace;
  private String cf = "Standard1";

  @Before
  public void setupCase() {
    CassandraHostConfigurator chc = new CassandraHostConfigurator("127.0.0.1:9170");
    chc.setMaxActive(2);
    cluster = getOrCreateCluster("MyCluster", chc);
    keyspace = createKeyspace(KEYSPACE, cluster);

    createMutator(keyspace, se)
    .addInsertion("getSliceTest_key3", cf, createColumn("birthyear1", 1974L, se, le))
    .addInsertion("getSliceTest_key3", cf, createColumn("birthyear2", 1975L, se, le))
    .addInsertion("getSliceTest_key3", cf, createColumn("birthyear3", 1976L, se, le))
    .addInsertion("getSliceTest_key3", cf, createColumn("birthyear4", 1977L, se, le))
    .addInsertion("getSliceTest_key3", cf, createColumn("birthyear5", 1978L, se, le))
    .addInsertion("getSliceTest_key3", cf, createColumn("birthyear6", 1979L, se, le))
    .execute();
  }

  @Test
  public void testNullKeyInvalidQuery() {
    SliceQuery<String, String, Long> sq = HFactory.createSliceQuery(keyspace, se, se, le);
    sq.setColumnFamily(cf);
    sq.setRange("birthyear1", "birthyear4", false, 100);
    // we are missing sq.setKey(...);

    try {
      sq.execute();
      fail();
    } catch (HInvalidRequestException he) {
      // ok!
    }

    sq.setKey("getSliceTest_key3");
    QueryResult<ColumnSlice<String, Long>> result = sq.execute();
    assertEquals(4,result.get().getColumns().size());
  }
}
