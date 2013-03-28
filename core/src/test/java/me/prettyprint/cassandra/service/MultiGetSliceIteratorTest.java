package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.*;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MultiGetSliceIteratorTest extends BaseEmbededServerSetupTest {

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
		
		for (int j = 0; j < 100; j++) {
			
			for (int i = 0; i < 1000; i++) {
				m.addInsertion(KEY+j, CF, createColumn(TimeUUIDUtils.getUniqueTimeUUIDinMillis(), String.valueOf(i), us, se));
			}
		}
		m.execute();
	}

	@After
	public void tearDown() {
		Mutator<String> m = createMutator(keyspace, se);
		for (int j = 0; j < 100; j++) {
			m.addDeletion(KEY+j, CF);
		}
		m.execute();
	}

	@Test
	public void testIterator() {

		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,keyspace, se,us,se, CF, KEYS, null,null,1,10,1000);// Retrieve all columns from row -> 1000	
		
		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
			}
			
		}
		assertEquals(100*1000, results.size());
	}

	@Test
	public void testModificationIterator() {
		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		
		Mutator mutator = HFactory.createMutator(keyspace, se);
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,keyspace, se,us,se, CF, KEYS, null,null,1,10,1000);// Retrieve all columns from row -> 1000	
	

		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
				mutator.addDeletion(r.getKey(), CF, c.getName(), us);
				mutator.execute();
			}
			
		}
		
		assertEquals(100*1000, results.size());
	}
	@Test
	public void testGetHostsUsed() {

		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,keyspace, se,us,se, CF, KEYS, null,null,1,10,10);// Retrieve all columns from row -> 1000	
		
		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
			}
			
		}
		assertEquals("127.0.0.1(127.0.0.1):9170", it.getHostsUsed());
	}
	@Test
	public void testGetThreadCountUsed() {

		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		Random randomGen= new Random();
		int threadCount=randomGen.nextInt(8),
				maxRowCountPerQuery=randomGen.nextInt(50), 
				maxColumnCountPerRow=100;
		
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,keyspace, se,us,se, CF, KEYS, null,null,
						threadCount,maxRowCountPerQuery,maxColumnCountPerRow);// Retrieve all columns from row -> 1000	
		
		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
			}
			
		}
		if(it.getThreadCountUsed() <= threadCount){
			assertTrue("Threads used:"+it.getThreadCountUsed(),true);
		}
		else {
			assertFalse("Threads used count is more than maxThreadCount specified",true);
		}
	
	}
	@Test
	public void testGetRowCountPerQueryUsed() {

		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		Random randomGen= new Random();
		int threadCount=randomGen.nextInt(8)+1,
			maxRowCountPerQuery=randomGen.nextInt(50)+1, 
			maxColumnCountPerRow=100;
		
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,keyspace, se,us,se, CF, KEYS, null,null,
						threadCount,maxRowCountPerQuery,maxColumnCountPerRow);// Retrieve all columns from row -> 1000	
		
		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
			}
			
		}
		if(it.getRowCountPerQueryUsed() <= maxRowCountPerQuery){
			assertTrue("RowCountPerQueryUsed:"+it.getRowCountPerQueryUsed(),true);
		}
		else {
			assertFalse("getRowCountPerQueryUsed() is more than specified parameter maxRowCountPerQuery",true);
		}
	
	}
	@Test
	public void testIteratorWithoutRowKeyLimit() {

		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		
		
		int maxColumnCountPerRow=100;
		
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,keyspace, se,us,se, CF, KEYS, null,null,maxColumnCountPerRow);
							
		
		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
			}
			
		}
		if(it.getRowCountPerQueryUsed() == KEYS.size()){
			assertTrue("Queried all rowkeys at once",true);
		}
		else {
			assertFalse("Did not query all keys at once when no maxRowCountPerQuery is specified",true);
		}
	
	}
	public void testIteratorWithoutThreading() {

		LinkedList<String> KEYS= new LinkedList<String>();
		for (int j = 0; j < 100; j++) {
			KEYS.add(KEY+j);
		}
		
		Random randomGen= new Random();
		int maxRowCountPerQuery=randomGen.nextInt(50)+1, 
			maxColumnCountPerRow=100;
		
		MultigetSliceIterator<String, UUID, String> it= 
				new MultigetSliceIterator<String, UUID, String>(false,maxRowCountPerQuery,keyspace, se,us,se, CF, KEYS, null,null,maxColumnCountPerRow);
							
		
		Map<UUID, String> results = new HashMap<UUID, String>();
		while (it.hasNext()) {
			Row<String,UUID, String> r = it.next();
			for (HColumn<UUID, String> c : r.getColumnSlice().getColumns()) {
				results.put(c.getName(), c.getValue());	
			}
			
		}
		if(it.getThreadCountUsed() == 0 && it.getRowCountPerQueryUsed()<=maxRowCountPerQuery){
			assertTrue("No Threading",true);
		}
		else {
			assertFalse("Either using threads when no thread count is specified or not respecting " +
					"maxRowCountPerQuery parameter",true);
		}
	
	}
}
