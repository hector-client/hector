package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.UUID;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.*;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ColumnFamilyRowCopyTest extends BaseEmbededServerSetupTest {

	private static final UUIDSerializer us = new UUIDSerializer();
	private static final ByteBufferSerializer bs = ByteBufferSerializer.get();
	private static final StringSerializer ss = new StringSerializer();
	private static final String CF = "Standard1";
	private static final String SOURCE_KEY = "source";
	private static final String DESTINATION_KEY = "destination";
	private Cluster cluster;
	private Keyspace keyspace;
	private int columns = 1000;

	@Before
	public void setUp() {
		cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
		keyspace = createKeyspace("Keyspace1", cluster);

		Mutator<String> m = createMutator(keyspace, ss);
		for (int i = 0; i < columns; i++) {
			m.addInsertion(SOURCE_KEY, CF, createColumn(UUID.randomUUID(), String.valueOf(i), us, ss));
		}
		m.execute();
	}

	@After
	public void tearDown() {
		Mutator<String> m = createMutator(keyspace, ss);
		m.addDeletion(SOURCE_KEY, CF);
		m.addDeletion(DESTINATION_KEY, CF);
		m.execute();
	}

	@Test
	public void testCopy() {
		new ColumnFamilyRowCopy<String, UUID>(keyspace, ss).setColumnFamily(CF).
						setRowKey(SOURCE_KEY).
						setDestinationKey(DESTINATION_KEY).
						setMutateInterval(150).copy();

		SliceQuery<String, ByteBuffer, String> query = HFactory.createSliceQuery(keyspace, ss, bs, ss).
						setKey(DESTINATION_KEY).setColumnFamily(CF);
		ColumnSliceIterator<String, ByteBuffer, String> it = new ColumnSliceIterator<String, ByteBuffer, String>(query, bs.
						fromBytes(new byte[0]), bs.fromBytes(new byte[0]), false, 100);

		int total = 0;
		while (it.hasNext()) {
			it.next();
			total++;
		}
		assertEquals(columns, total);
	}
}