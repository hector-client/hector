package me.prettyprint.hector.api.beans;

import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import me.prettyprint.cassandra.serializers.*;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.template.BaseColumnFamilyTemplateTest;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;
import org.junit.After;
import org.junit.Test;

public class DynamicCompositeTest extends BaseColumnFamilyTemplateTest {
	private static DynamicCompositeSerializer ds = DynamicCompositeSerializer.get();
	private static StringSerializer ss = StringSerializer.get();
	private static UUIDSerializer us = UUIDSerializer.get();
	private static Serializer<UUID> uss = UUIDSerializer.get();
	private static String columnFamily = "DynamicComposite1";
	private static String rowKey = "rowKey";

	@After
	public void after() {
		cluster.truncate(keyspace.getKeyspaceName(), columnFamily);
	}
	
	@Test
	public void allTypesSerialize() {
		DynamicComposite composite = new DynamicComposite();

		UUID lexUUID = UUID.randomUUID();
		com.eaio.uuid.UUID timeUUID = new com.eaio.uuid.UUID();


		//add all forward comparators
		composite.addComponent(0, "AsciiText", AsciiSerializer.get(), "AsciiType", ComponentEquality.EQUAL);
		composite.addComponent(1, new byte[]{0, 1, 2, 3}, BytesArraySerializer.get(), "BytesType", ComponentEquality.EQUAL);
		composite.addComponent(2, -1, IntegerSerializer.get(), "IntegerType", ComponentEquality.EQUAL);
		composite.addComponent(3,  lexUUID, UUIDSerializer.get(), "LexicalUUIDType", ComponentEquality.EQUAL);
		composite.addComponent(4, -1l, LongSerializer.get(), "LongType", ComponentEquality.EQUAL);
		composite.addComponent(5, timeUUID, TimeUUIDSerializer.get(), "TimeUUIDType", ComponentEquality.EQUAL);
		composite.addComponent(6, "UTF8Text", StringSerializer.get(), "UTF8Type", ComponentEquality.EQUAL);
		composite.addComponent(7,  lexUUID, UUIDSerializer.get(), "UUIDType", ComponentEquality.EQUAL);

		//add all reverse comparators
		composite.addComponent(8, "AsciiText", AsciiSerializer.get(), "AsciiType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(9, new byte[]{0, 1, 2, 3}, BytesArraySerializer.get(), "BytesType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(10, -1, IntegerSerializer.get(), "IntegerType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(11,  lexUUID, UUIDSerializer.get(), "LexicalUUIDType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(12, -1l, LongSerializer.get(), "LongType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(13, timeUUID, TimeUUIDSerializer.get(), "TimeUUIDType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(14, "UTF8Text", StringSerializer.get(), "UTF8Type(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(15,  lexUUID, UUIDSerializer.get(), "UUIDType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(16, "My element", ComponentEquality.EQUAL);

		//serialize to the native bytes value

		ByteBuffer buffer = DynamicCompositeSerializer.get().toByteBuffer(composite);


		//now deserialize and ensure the values are the same
		DynamicComposite parsed = DynamicCompositeSerializer.get().fromByteBuffer(buffer);

		assertEquals("AsciiText", parsed.get(0, AsciiSerializer.get()));
		assertArrayEquals(new byte[]{0, 1, 2, 3}, parsed.get(1, BytesArraySerializer.get()));
		assertEquals(Integer.valueOf(-1), parsed.get(2, IntegerSerializer.get()));
		assertEquals(lexUUID, parsed.get(3, UUIDSerializer.get()));
		assertEquals(Long.valueOf(-1l), parsed.get(4, LongSerializer.get()));
		assertEquals(timeUUID, parsed.get(5, TimeUUIDSerializer.get()));
		assertEquals("UTF8Text", parsed.get(6, StringSerializer.get()));
		assertEquals(lexUUID, parsed.get(7, UUIDSerializer.get()));

		//now test all the reversed values
		assertEquals("AsciiText", parsed.get(8, AsciiSerializer.get()));
		assertArrayEquals(new byte[]{0, 1, 2, 3}, parsed.get(9, BytesArraySerializer.get()));
		assertEquals(Integer.valueOf(-1), parsed.get(10, IntegerSerializer.get()));
		assertEquals(lexUUID, parsed.get(11, UUIDSerializer.get()));
		assertEquals(Long.valueOf(-1l), parsed.get(12, LongSerializer.get()));
		assertEquals(timeUUID, parsed.get(13, TimeUUIDSerializer.get()));
		assertEquals("UTF8Text", parsed.get(14, StringSerializer.get()));
		assertEquals(lexUUID, parsed.get(15, UUIDSerializer.get()));
		assertEquals("My element", parsed.get(16, StringSerializer.get()));


	}

	@Test
	public void testUUIDGetAll() {
		// Gets all columns in the row regardless of the column name		
		init();

		// Get all rows
		Set<UUID> results = new HashSet<UUID>();
		ColumnSliceIterator<String, DynamicComposite, String> iterator = getIterator(rowKey, null, null);
		while(iterator.hasNext()) {
			HColumn<DynamicComposite, String> column = iterator.next();
			DynamicComposite composite = column.getName();
			UUID component1 = composite.get(1, us);
			results.add(component1);
		}

		assertEquals("Failed to retrieve all columns", 8, results.size());
	}

	@Test
	public void testUUIDGetSlice() {
		// Gets all columns based on the first component in the column name
		Map<UUID, Set<UUID>> ids = init();

		for(Entry<UUID, Set<UUID>> entry : ids.entrySet()) {
			UUID component0 = entry.getKey();

			// start at first column who's single component == component0
			DynamicComposite start = new DynamicComposite();
			start.addComponent(component0, us);

			// up to and including any column whose first component == component0 regardless of remaining component values
			DynamicComposite end = new DynamicComposite();
			end.addComponent(component0, us, us.getComparatorType().getTypeName(), ComponentEquality.GREATER_THAN_EQUAL);

			ColumnSliceIterator<String, DynamicComposite, String> iterator = getIterator(rowKey, start, end);
			while(iterator.hasNext()) {
				HColumn<DynamicComposite, String> column = iterator.next();
				DynamicComposite composite = column.getName();

				assertEquals(component0, composite.get(0, us));
				assertTrue(ids.get(component0).contains(composite.get(1, us)));
			}
		}
	}

	@Test
	public void testStringGetSlice() {
		Mutator<String> mutator = createMutator(keyspace, ss);

		DynamicComposite composite = (DynamicComposite) new DynamicComposite().
						addComponent("a", ss).
						addComponent("ba", ss).
						addComponent("ca", ss).
						addComponent("da", ss);
		mutator.addInsertion(rowKey, columnFamily, HFactory.createColumn(composite, composite.toString(), ds, ss));

		composite = (DynamicComposite) new DynamicComposite().
						addComponent("a", ss).
						addComponent("bb", ss).
						addComponent("cb", ss).
						addComponent("db", ss);
		mutator.addInsertion(rowKey, columnFamily, HFactory.createColumn(composite, composite.toString(), ds, ss));

		composite = (DynamicComposite) new DynamicComposite().
						addComponent("b", ss).
						addComponent("ba", ss).
						addComponent("ca", ss).
						addComponent("da", ss);
		mutator.addInsertion(rowKey, columnFamily, HFactory.createColumn(composite, composite.toString(), ds, ss));

		composite = (DynamicComposite) new DynamicComposite().
						addComponent("b", ss).
						addComponent("bb", ss).
						addComponent("cb", ss).
						addComponent("db", ss);
		mutator.addInsertion(rowKey, columnFamily, HFactory.createColumn(composite, composite.toString(), ds, ss));

		mutator.execute();

		String compType = ss.getComparatorType().getTypeName();

		System.out.println("LESS_THAN_EQUAL");
		DynamicComposite end = (DynamicComposite) new DynamicComposite().
						addComponent("b", ss, compType).
						addComponent("ba", ss).
						addComponent("ca", ss).
						addComponent("da", ss, compType, ComponentEquality.LESS_THAN_EQUAL); // s@a:s@ba:s@ca:s@da thru s@a:s@bb:s@cb:s@db

		ColumnSliceIterator<String, DynamicComposite, String> iterator = getIterator(rowKey, null, end);
		while(iterator.hasNext()) {
			System.out.println(iterator.next().getName());
		}

		System.out.println("EQUAL");
		end = (DynamicComposite) new DynamicComposite().
						addComponent("b", ss, compType).
						addComponent("ba", ss).
						addComponent("ca", ss).
						addComponent("da", ss, compType, ComponentEquality.EQUAL); // s@a:s@ba:s@ca:s@da thru s@b:s@ba:s@ca:s@da

		iterator = getIterator(rowKey, null, end);
		while(iterator.hasNext()) {
			System.out.println(iterator.next().getName());
		}

		System.out.println("GREATER_THAN_EQUAL");
		end = (DynamicComposite) new DynamicComposite().
						addComponent("b", ss, compType).
						addComponent("bb", ss, compType, ComponentEquality.GREATER_THAN_EQUAL); // s@a:s@ba:s@ca:s@da thru s@b:s@bb:s@cb:s@db

		iterator = getIterator(rowKey, null, end);
		while(iterator.hasNext()) {
			System.out.println(iterator.next().getName());
		}
	}

	private ColumnSliceIterator<String, DynamicComposite, String> getIterator(String key, DynamicComposite start, DynamicComposite end) {
		SliceQuery<String, DynamicComposite, String> query = HFactory.createSliceQuery(keyspace, ss, ds, ss).
						setColumnFamily(columnFamily).
						setKey(key);
		return new ColumnSliceIterator<String, DynamicComposite, String>(query, start, end, false);
	}

	/**
	 * Initializes a row whose DynamicComposite column name components are two UUIDs.
	 * Sample row:
	 *
	 * RowKey: ROW_KEY
	 *		=> (column=u@77df3aa8-f8b2-4658-be4d-ad9401f4388a:u@222c532f-e310-4803-8119-3a96f770b763, value=[77df3aa8-f8b2-4658-be4d-ad9401f4388a, 222c532f-e310-4803-8119-3a96f770b763], timestamp=1353028069149000)
	 *		=> (column=u@77df3aa8-f8b2-4658-be4d-ad9401f4388a:u@acf45bbe-4b2d-4f90-80d3-3b2288ccb13a, value=[77df3aa8-f8b2-4658-be4d-ad9401f4388a, acf45bbe-4b2d-4f90-80d3-3b2288ccb13a], timestamp=1353028069149002)
	 *		=> (column=u@77df3aa8-f8b2-4658-be4d-ad9401f4388a:u@b1015157-e030-45dd-b5a3-67a11f7e6350, value=[77df3aa8-f8b2-4658-be4d-ad9401f4388a, b1015157-e030-45dd-b5a3-67a11f7e6350], timestamp=1353028069138000)
	 *		=> (column=u@77df3aa8-f8b2-4658-be4d-ad9401f4388a:u@f42dc238-7fab-48ec-b831-632bd1adaad9, value=[77df3aa8-f8b2-4658-be4d-ad9401f4388a, f42dc238-7fab-48ec-b831-632bd1adaad9], timestamp=1353028069149001)
	 *		=> (column=u@def2ce2c-a77d-48de-8072-1b5c06fb0e07:u@20624858-77fb-495b-9886-12b7ff230da9, value=[def2ce2c-a77d-48de-8072-1b5c06fb0e07, 20624858-77fb-495b-9886-12b7ff230da9], timestamp=1353028069150002)
	 *		=> (column=u@def2ce2c-a77d-48de-8072-1b5c06fb0e07:u@bff9acb8-2b61-420a-b6ad-4095bc87f32c, value=[def2ce2c-a77d-48de-8072-1b5c06fb0e07, bff9acb8-2b61-420a-b6ad-4095bc87f32c], timestamp=1353028069150001)
	 *		=> (column=u@def2ce2c-a77d-48de-8072-1b5c06fb0e07:u@c9b0bfc4-b756-43f8-bcd9-1ec6680b4bde, value=[def2ce2c-a77d-48de-8072-1b5c06fb0e07, c9b0bfc4-b756-43f8-bcd9-1ec6680b4bde], timestamp=1353028069150003)
	 *		=> (column=u@def2ce2c-a77d-48de-8072-1b5c06fb0e07:u@decaf265-1213-42e8-a489-a991d4d00d97, value=[def2ce2c-a77d-48de-8072-1b5c06fb0e07, decaf265-1213-42e8-a489-a991d4d00d97], timestamp=1353028069150000)
	 *
	 * @return Map of first component to a set of the corresponding second components
	 */
	private Map<UUID, Set<UUID>> init() {
		Mutator<String> mutator = createMutator(keyspace, ss);
		Map<UUID, Set<UUID>> ids = new HashMap<UUID, Set<UUID>>();
		ids.put(UUID.randomUUID(), new HashSet<UUID>());
		ids.put(UUID.randomUUID(), new HashSet<UUID>());

		for(Entry<UUID, Set<UUID>> entry : ids.entrySet()) {
			for(int i = 0; i < 4; i++) {
				UUID uuid = UUID.randomUUID();

				DynamicComposite composite = (DynamicComposite) new DynamicComposite().addComponent(entry.getKey(), uss).addComponent(uuid, uss);
				mutator.addInsertion(rowKey, columnFamily,	HFactory.createColumn(composite, composite.toString(), ds, ss));

				entry.getValue().add(uuid);
			}
		}

		mutator.execute();

		return ids;
	}
}
