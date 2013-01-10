package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.template.SliceFilter;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
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
import static org.junit.Assert.*;

public class SliceCounterIteratorTest extends BaseEmbededServerSetupTest {

	private static final UUIDSerializer us = UUIDSerializer.get();
	private static final StringSerializer se = new StringSerializer();
	private static final String CF = "Counter1";
	private static final String KEY = "key";
	private static final SliceCounterIterator.SliceCounterFinish<UUID> FINISH = new SliceCounterIterator.SliceCounterFinish<UUID>() {

		@Override
		public UUID function() {
			return TimeUUIDUtils.getUniqueTimeUUIDinMillis();
		}
	};
	private Cluster cluster;
	private Keyspace keyspace;

	@Before
	public void setUp() {
		cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
		keyspace = createKeyspace("Keyspace1", cluster);

		Mutator<String> m = createMutator(keyspace, se);
		for (int i = 0; i < 1000; i++) {
			m.addCounter(KEY, CF, createCounterColumn(TimeUUIDUtils.getUniqueTimeUUIDinMillis(), 1, us));
		}
		m.execute();
	}

	@After
	public void tearDown() {
		Mutator<String> m = createMutator(keyspace, se);
		m.addDeletion(KEY, CF);
		m.execute();
	}

	@Test
	public void testIterator() {
		SliceCounterQuery<String, UUID> query = HFactory.createCounterSliceQuery(keyspace, se, us).setKey(KEY).setColumnFamily(CF);
		SliceCounterIterator<String, UUID> it = new SliceCounterIterator<String, UUID>(query, null, FINISH, false, 100);

		Map<UUID, Long> results = new HashMap<UUID, Long>();
		while (it.hasNext()) {
			HCounterColumn<UUID> c = it.next();
			results.put(c.getName(), c.getValue());
		}
		assertEquals(1000, results.size());
	}

	@Test
	public void testModificationIterator() {
		Mutator mutator = HFactory.createMutator(keyspace, se);
		SliceCounterQuery<String, UUID> query = HFactory.createCounterSliceQuery(keyspace, se, us).setKey(KEY).setColumnFamily(CF);
		SliceCounterIterator<String, UUID> it = new SliceCounterIterator<String, UUID>(query, null, FINISH, false, 100);

		Map<UUID, Long> results = new HashMap<UUID, Long>();
		while (it.hasNext()) {
			HCounterColumn<UUID> c = it.next();
			results.put(c.getName(), c.getValue());
			mutator.addDeletion(KEY, CF, c.getName(), us);
			mutator.execute();
		}
		assertEquals(1000, results.size());
	}

	@Test
	public void testFilter() {
		cluster.truncate(keyspace.getKeyspaceName(), CF);

		Mutator<String> m = createMutator(keyspace, se);
		for (int i = 0; i < 500; i++) {
			m.addCounter(KEY, CF, createCounterColumn("a" + i, 1, se));
			m.addCounter(KEY, CF, createCounterColumn("b" + i, 1, se));
			m.addCounter(KEY, CF, createCounterColumn("c" + i, 1, se));
		}
		m.execute();

		SliceCounterQuery<String, String> query = HFactory.createCounterSliceQuery(keyspace, se, se).setKey(KEY).setColumnFamily(CF);
		SliceCounterIterator<String, String> it = new SliceCounterIterator<String, String>(query, "a", "d", false, 100).setFilter(new SliceFilter<HCounterColumn<String>>() {

			@Override
			public boolean accept(HCounterColumn<String> column)
			{
				return !column.getName().startsWith("b");
			}

		});

		List<String> results = new ArrayList<String>(1000);
		while(it.hasNext()) {
			HCounterColumn<String> c = it.next();
			String name = c.getName();

			assertFalse(name.equals("b"));
			results.add(name);
		}

		assertEquals(1000, results.size());
	}
}
