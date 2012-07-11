package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.OrderedCounterRows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesCounterQuery;

import org.junit.Before;
import org.junit.Test;

import static me.prettyprint.hector.api.factory.HFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RangeSlicesCounterQueryTest extends BaseEmbededServerSetupTest {

  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private Cluster cluster;
  private Keyspace keyspace;
  private String cf = "Counter2";

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace(KEYSPACE, cluster);
    createMutator(keyspace, se)
    .addCounter("ranageSlicesCounterTest_key1", cf, createCounterColumn("alpha", 1L))
    .addCounter("ranageSlicesCounterTest_key1", cf, createCounterColumn("beta", 2L))
    .addCounter("ranageSlicesCounterTest_key2", cf, createCounterColumn("alpha", 1L))
    .addCounter("ranageSlicesCounterTest_key2", cf, createCounterColumn("beta", 2L))
    .addCounter("ranageSlicesCounterTest_key3", cf, createCounterColumn("alpha", 1L))
    .addCounter("ranageSlicesCounterTest_key3", cf, createCounterColumn("beta", 2L))
    .addCounter("ranageSlicesCounterTest_key4", cf, createCounterColumn("alpha", 1L))
    .addCounter("ranageSlicesCounterTest_key4", cf, createCounterColumn("beta", 2L))
    .addCounter("ranageSlicesCounterTest_key5", cf, createCounterColumn("alpha", 1L))
    .addCounter("ranageSlicesCounterTest_key5", cf, createCounterColumn("beta", 2L))
    .addCounter("ranageSlicesCounterTest_key6", cf, createCounterColumn("alpha", 1L))
    .addCounter("ranageSlicesCounterTest_key6", cf, createCounterColumn("beta", 2L))
    .execute();
  }
  
  @Test
  public void testKeysOnlyPredicate() {
    RangeSlicesCounterQuery<String, String> rangeSlicesQuery = HFactory.createRangeSlicesCounterQuery(keyspace, se, se);
    QueryResult<OrderedCounterRows<String, String>> result =
      rangeSlicesQuery.setColumnFamily(cf).setKeys("", "").setReturnKeysOnly().execute();

    OrderedCounterRows<String, String> orderedRows = result.get();
    CounterRow<String, String> row = orderedRows.iterator().next();

    assertNotNull(row.getKey());
    assertEquals(0,row.getColumnSlice().getColumns().size());
    
    result = rangeSlicesQuery.setColumnNames("alpha","beta").setRowCount(5).execute();
    orderedRows = result.get();

      row = orderedRows.iterator().next();
    assertNotNull(row.getKey());
    assertEquals(2,row.getColumnSlice().getColumns().size());
  }
}
