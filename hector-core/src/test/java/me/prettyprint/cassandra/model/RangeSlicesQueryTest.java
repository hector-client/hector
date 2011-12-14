package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.*;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

import org.junit.Before;
import org.junit.Test;

public class RangeSlicesQueryTest extends BaseEmbededServerSetupTest {

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
    .addInsertion("rangeSlicesTest_key1", cf, createColumn("birthyear", 1974L, se, le))
    .addInsertion("rangeSlicesTest_key1", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("rangeSlicesTest_key2", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("rangeSlicesTest_key2", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("rangeSlicesTest_key3", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("rangeSlicesTest_key3", cf, createColumn("birthmonth", 5L, se, le))
    .addInsertion("rangeSlicesTest_key4", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("rangeSlicesTest_key4", cf, createColumn("birthmonth", 6L, se, le))
    .addInsertion("rangeSlicesTest_key5", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("rangeSlicesTest_key5", cf, createColumn("birthmonth", 7L, se, le))
    .addInsertion("rangeSlicesTest_key6", cf, createColumn("birthyear", 1976L, se, le))
    .addInsertion("rangeSlicesTest_key6", cf, createColumn("birthmonth", 6L, se, le))
    .execute();
  }
  
  @Test
  public void testKeysOnlyPredicate() {
    RangeSlicesQuery<String, String, Long> rangeSlicesQuery = HFactory.createRangeSlicesQuery(keyspace, se, se, le);
    QueryResult<OrderedRows<String, String, Long>> result = 
      rangeSlicesQuery.setColumnFamily(cf).setKeys("", "").setReturnKeysOnly().execute();
    OrderedRows<String, String, Long> orderedRows = result.get();
    Row<String, String, Long> row = orderedRows.iterator().next();
    assertNotNull(row.getKey());
    assertEquals(0,row.getColumnSlice().getColumns().size());
    
    result = rangeSlicesQuery.setColumnNames("birthyear","birthmonth").setRowCount(5).execute();
    orderedRows = result.get();
    row = orderedRows.iterator().next();
    assertNotNull(row.getKey());
    assertEquals(2,row.getColumnSlice().getColumns().size());
  }
}
