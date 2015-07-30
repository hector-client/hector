package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Column;

import me.prettyprint.hector.api.Serializer;

/**
 * CASMutation is designed to contain the expected and update
 * columns of a CAS operation. CAS stands for Compare And
 * Swap. It is also known as "light weight transactions" in
 * Cassandra.
 * 
 * CAS can be used for Insert and Update. An example of insert
 * is insert a record if the key doesn't already exist.
 * 
 * @author peter lin
 *
 * @param <K>
 */
public final class CASMutation<K> {

	private final Serializer<K> keySerializer;
	private String columnFamilyName = null;
	private K key = null;
	private Map<ByteBuffer,Column> expected = new java.util.HashMap<ByteBuffer,Column>();
	private Map<ByteBuffer,Column> updates = new java.util.HashMap<ByteBuffer,Column>();
	
	public CASMutation(Serializer<K> serializer) {
		this.keySerializer = serializer;
	}

	public void setKey(K thekey) {
		this.key = thekey;
	}
	
	public K getKey() {
		return this.key;
	}
	
	public ByteBuffer getKeyBuffer() {
		return this.keySerializer.toByteBuffer(this.key);
	}

	public String getColumnFamily() {
		return columnFamilyName;
	}

	public CASMutation<K> setColumnFamily(String cfName) {
		this.columnFamilyName = cfName;
		return this;
	}

	public List<Column> getExpected() {
		return new java.util.ArrayList<Column>(expected.values());
	}

	public CASMutation<K> setExpected(List<Column> expected) {
		for (Column c: expected) {
			this.expected.put(c.name, c);
		}
		return this;
	}

	public CASMutation<K> addExpected(Column expected) {
		if (expected != null) {
			this.expected.put(expected.bufferForName(), expected);
		}
		return this;
	}
	
	public List<Column> getUpdates() {
		return new java.util.ArrayList<Column>(updates.values());
	}

	public CASMutation<K> setUpdates(List<Column> updates) {
		for (Column c: updates) {
			this.updates.put(c.bufferForName(), c);
		}
		return this;
	}
	
	public CASMutation<K> addUpdates(Column updates) {
		this.updates.put(updates.bufferForName(), updates);
		return this;
	}
}
