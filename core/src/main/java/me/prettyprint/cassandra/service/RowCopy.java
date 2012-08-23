package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;

/**
 * Copys all the columns in the source row to the destination row.
 *
 * @author thrykol
 */
public class RowCopy<K, N, V> {

	private Keyspace keyspace;
	private Serializer<K> keySerializer;
	private Serializer<N> nameSerializer;
	private Serializer<V> valueSerializer;
	private K rowKey;
	private K destinationKey;
	private String cf;
	private int mutateInterval = 100;

	public RowCopy(Keyspace keyspace, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
		this.keyspace = keyspace;
		this.keySerializer = keySerializer;
		this.nameSerializer = nameSerializer;
		this.valueSerializer = valueSerializer;
	}

	/**
	 * Column family the row is part of.
	 *
	 * @param cf Column family name
	 * @return <code>this</code>
	 */
	public RowCopy setColumnFamily(String cf) {
		this.cf = cf;
		return this;
	}

	/**
	 * Key of the source row.
	 *
	 * @param rowKey Row key
	 * @return <code>this</code>
	 */
	public RowCopy setRowKey(K rowKey) {
		this.rowKey = rowKey;
		return this;
	}

	/**
	 * Key of the destination row
	 *
	 * @param destinationKey Destination row key
	 * @return <code>this</code>
	 */
	public RowCopy setDestinationKey(K destinationKey) {
		this.destinationKey = destinationKey;
		return this;
	}

	/**
	 * Set the mutate interval.
	 *
	 * @param interval Mutation interval
	 * @return <code>this</code>
	 */
	public RowCopy setMutateInterval(int interval) {
		this.mutateInterval = interval;
		return this;
	}

	/**
	 * Copy the source row to the destination row.
	 *
	 * @throws HectorException if any required fields are not set
	 */
	public void copy() throws HectorException {
		if (this.cf == null) {
			throw new HectorException("Unable to clone row with null column family");
		}
		if (this.rowKey == null) {
			throw new HectorException("Unable to clone row with null row key");
		}
		if (this.destinationKey == null) {
			throw new HectorException("Unable to clone row with null clone key");
		}

		ColumnFamilyTemplate<K, N> template = new ThriftColumnFamilyTemplate<K, N>(this.keyspace, this.cf, this.keySerializer, this.nameSerializer);
		Mutator<K> mutator = HFactory.createMutator(this.keyspace, this.keySerializer, new BatchSizeHint(1, this.mutateInterval));
		ColumnFamilyUpdater<K, N> updater = template.createUpdater(this.destinationKey, mutator);

		SliceQuery<K, N, V> query = HFactory.createSliceQuery(this.keyspace, this.keySerializer, this.nameSerializer, this.valueSerializer).
						setColumnFamily(this.cf).
						setKey(this.rowKey);

		ColumnSliceIterator<K, N, V> iterator = new ColumnSliceIterator<K, N, V>(query, nameSerializer.
						fromBytes(new byte[0]), nameSerializer.fromBytes(new byte[0]), false);
		while (iterator.hasNext()) {
			HColumn<N, V> column = iterator.next();
			updater.setValue(column.getName(), column.getValue(), this.valueSerializer);
		}

		template.update(updater);
	}
}
