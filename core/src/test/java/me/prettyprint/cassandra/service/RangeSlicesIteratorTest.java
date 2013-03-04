package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.SliceFilter;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.*;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RangeSlicesIteratorTest extends BaseEmbededServerSetupTest {

  private static final StringSerializer se = new StringSerializer();
  private static final IntegerSerializer is = IntegerSerializer.get();
  
  private static final String CF = "Standard1";

  private static Cluster cluster;
  private static Keyspace keyspace;

	@BeforeClass
	public static void setupClass() {
    cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
    keyspace = createKeyspace("Keyspace1", cluster);
	}

  @AfterClass
  public static void teardownClass() {
    keyspace = null;
    cluster = null;
  }

  @Before
  public void setupCase() {
    // Insert 21 rows
    Mutator<String> m = createMutator(keyspace, se);
    for (int i = 1; i <= 21; i++) {
      m.addInsertion("k" + i, CF, createColumn(new Integer(i), new Integer(i), is, is));
    }
    m.execute();
  }

  @Test
  public void testIterator() {

		RangeSlicesQuery<String, Integer, Integer> query = HFactory.createRangeSlicesQuery(keyspace, se, is, is);
		query.setColumnFamily(CF);
		query.setRange(null, null, false, 10);

		assertKeys(query, 3, null, "k11", null);
		assertKeys(query, 10, "k2", null, null);
		assertKeys(query, 21, null, null, null);
  }

  @Test
	public void testFilter() {
		RangeSlicesQuery<String, Integer, Integer> query = HFactory.createRangeSlicesQuery(keyspace, se, is, is);
		query.setColumnFamily(CF);
		query.setRange(null, null, false, 10);

		SliceFilter<Row<String, Integer, Integer>> filter = new SliceFilter<Row<String, Integer, Integer>>() {

			@Override
			public boolean accept(Row<String, Integer, Integer> row)
			{
				return Integer.parseInt(row.getKey().replaceAll("^k", "")) < 10;
			}
		};

		assertKeys(query, 9, null, null, filter);
	}
	
  private void assertKeys(RangeSlicesQuery query, int expected, String start, String end, SliceFilter<Row<String, Integer, Integer>> filter) {
		RangeSlicesIterator<String, Integer, Integer> iterator = new RangeSlicesIterator<String, Integer, Integer>(query, start, end).setFilter(filter);

    int total = 0;
		while(iterator.hasNext()) {
			iterator.next().getKey();
			total ++;
		}

    assertEquals(expected, total);
  }
}
