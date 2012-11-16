package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.*;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceCounterQuery;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ClearCounterRowTest extends BaseEmbededServerSetupTest {

	private static final StringSerializer ss = new StringSerializer();
	private static final String cf = "Counter1";
	private static final String key = "key";
	private Cluster cluster;
	private Keyspace keyspace;

	@Before
	public void setUp() {
    cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
    keyspace = createKeyspace("Keyspace1", cluster);

		Mutator<String> m = createMutator(keyspace, ss);
	}

	@After
	public void tearDown() {
    keyspace = null;
    cluster = null;
	}

	@Test
	public void testClear() {
    Mutator<String> mutator = createMutator(keyspace, ss);
    for (int i = 1; i <= 10; i++) {
    	mutator.addCounter(key, cf, createCounterColumn("" + i, i));
    }
    mutator.execute();

		SliceCounterQuery query = HFactory.createCounterSliceQuery(keyspace, ss, ss);
		query.setColumnFamily(cf);
		query.setKey(key);

		SliceCounterIterator<String, String> iterator = new SliceCounterIterator<String, String>(query, null, (String) null, false, 10);
		while(iterator.hasNext()) {
			HCounterColumn<String> column = iterator.next();

			long expected = Long.parseLong(column.getName());
			assertEquals(expected, (long) column.getValue());
		}

		new ClearCounterRow(keyspace, ss, ss).setColumnFamily(cf).setRowKey(key).clear();

		iterator = new SliceCounterIterator<String, String>(query, null, (String) null, false, 10);
		while(iterator.hasNext()) {
			HCounterColumn<String> column = iterator.next();

			long expected = 0;
			assertEquals(expected, (long) column.getValue());
		}
	}
}
