package me.prettyprint.cassandra.service;

import java.util.UUID;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.*;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class RowCopyTest extends BaseEmbededServerSetupTest {

	private static final UUIDSerializer ue = new UUIDSerializer();
	private static final StringSerializer se = new StringSerializer();
	private static final String CF = "Standard1";
	private static final String SOURCE_KEY = "source";
	private static final String DESTINATION_KEY = "destination";
	private Cluster cluster;
	private Keyspace keyspace;
	private UUID first;
	private UUID last;
	private int columns = 1000;

	@Before
	public void setUp() {
		cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
		keyspace = createKeyspace("Keyspace1", cluster);

		Mutator<String> m = createMutator(keyspace, se);
		first = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
		for (int i = 0; i < columns; i++) {
			if(i == 0) {
				m.addInsertion(SOURCE_KEY, CF, createColumn(first, String.valueOf(i), ue, se));
			}
			
			last = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
			m.addInsertion(SOURCE_KEY, CF, createColumn(last, String.valueOf(i), ue, se));
		}
		m.execute();
	}

	@After
	public void tearDown() {
		Mutator<String> m = createMutator(keyspace, se);
		m.addDeletion(SOURCE_KEY, CF);
		m.addDeletion(DESTINATION_KEY, CF);
		m.execute();
	}

	@Test
	public void testCopy() {
		new RowCopy<String, UUID, String>(keyspace, se, ue, se).setColumnFamily(CF).
						setRowKey(SOURCE_KEY).
						setDestinationKey(DESTINATION_KEY).
						setMutateInterval(150).copy();

		SliceQuery<String, UUID, String> query = HFactory.createSliceQuery(keyspace, se, ue, se).
						setKey(DESTINATION_KEY).setColumnFamily(CF);
		ColumnSliceIterator<String, UUID, String> it = new ColumnSliceIterator<String, UUID, String>(query, ue.
						fromBytes(new byte[0]), ue.fromBytes(new byte[0]), false, 100);

		int total = 0;
		UUID firstCopy = null;
		UUID lastCopy = null;
		while (it.hasNext()) {
			HColumn<UUID, String> hcolumn = it.next();
			if(++total == 1) {
				firstCopy = hcolumn.getName();
			} else if(total == columns) {
				lastCopy = hcolumn.getName();
			}
		}
		assertEquals(columns, total);
		assertEquals(first, firstCopy);
		assertEquals(last, lastCopy);
	}
}
