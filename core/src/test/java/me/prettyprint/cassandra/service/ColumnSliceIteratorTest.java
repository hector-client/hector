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
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.*;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ColumnSliceIteratorTest extends BaseEmbededServerSetupTest {

	private static final UUIDSerializer us = UUIDSerializer.get();
	private static final StringSerializer se = new StringSerializer();
	private static final String CF = "Standard1";
	private static final String KEY = "key";
	private static final ColumnSliceIterator.ColumnSliceFinish<UUID> FINISH = new ColumnSliceIterator.ColumnSliceFinish<UUID>() {

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
			m.addInsertion(KEY, CF, createColumn(TimeUUIDUtils.getUniqueTimeUUIDinMillis(), String.valueOf(i), us, se));
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
		SliceQuery<String, UUID, String> query = HFactory.createSliceQuery(keyspace, se, us, se).setKey(KEY).setColumnFamily(CF);
		ColumnSliceIterator<String, UUID, String> it = new ColumnSliceIterator<String, UUID, String>(query, null, FINISH, false, 100);

		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			HColumn<UUID, String> c = it.next();
			results.put(c.getName(), c.getValue());
		}
		assertEquals(1000, results.size());
	}

	@Test
	public void testModificationIterator() {
		Mutator mutator = HFactory.createMutator(keyspace, se);
		SliceQuery<String, UUID, String> query = HFactory.createSliceQuery(keyspace, se, us, se).setKey(KEY).setColumnFamily(CF);
		ColumnSliceIterator<String, UUID, String> it = new ColumnSliceIterator<String, UUID, String>(query, null, FINISH, false, 100);

		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			HColumn<UUID, String> c = it.next();
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
			m.addInsertion(KEY, CF, createColumn("a" + i, String.valueOf(i), se, se));
			m.addInsertion(KEY, CF, createColumn("b" + i, String.valueOf(i), se, se));
			m.addInsertion(KEY, CF, createColumn("c" + i, String.valueOf(i), se, se));
		}
		m.execute();

		SliceQuery<String, String, String> query = HFactory.createSliceQuery(keyspace, se, se, se)
						.setKey(KEY)
						.setColumnFamily(CF);
		ColumnSliceIterator<String, String, String> it = new ColumnSliceIterator<String, String, String>(query, "a", "d", false, 2).
						setFilter(new SliceFilter<HColumn<String, String>>() {

			@Override
			public boolean accept(HColumn<String, String> column) {
				return !column.getName().startsWith("b");
			}
		});

		List<String> results = new ArrayList<String>(1000);
		while (it.hasNext()) {
			HColumn<String, String> c = it.next();
			String name = c.getName();

			assertFalse(name.equals("b"));
			results.add(name);
		}
		assertEquals(1000, results.size());

	}
}
