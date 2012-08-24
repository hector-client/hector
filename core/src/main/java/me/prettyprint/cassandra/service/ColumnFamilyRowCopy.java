package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
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
public class ColumnFamilyRowCopy<K, N> {

	private Keyspace keyspace;
	private Serializer<K> keySerializer;
	private ByteBufferSerializer bs = ByteBufferSerializer.get();
	private K rowKey;
	private K destinationKey;
	private String cf;
	private int mutateInterval = 100;

	public ColumnFamilyRowCopy(Keyspace keyspace, Serializer<K> keySerializer) {
		this.keyspace = keyspace;
		this.keySerializer = keySerializer;
	}

	/**
	 * Column family the row is part of.
	 *
	 * @param cf Column family name
	 * @return <code>this</code>
	 */
	public ColumnFamilyRowCopy setColumnFamily(String cf) {
		this.cf = cf;
		return this;
	}

	/**
	 * Key of the source row.
	 *
	 * @param rowKey Row key
	 * @return <code>this</code>
	 */
	public ColumnFamilyRowCopy setRowKey(K rowKey) {
		this.rowKey = rowKey;
		return this;
	}

	/**
	 * Key of the destination row
	 *
	 * @param destinationKey Destination row key
	 * @return <code>this</code>
	 */
	public ColumnFamilyRowCopy setDestinationKey(K destinationKey) {
		this.destinationKey = destinationKey;
		return this;
	}

	/**
	 * Set the mutate interval.
	 *
	 * @param interval Mutation interval
	 * @return <code>this</code>
	 */
	public ColumnFamilyRowCopy setMutateInterval(int interval) {
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

		ColumnFamilyTemplate<K, ByteBuffer> template = new ThriftColumnFamilyTemplate<K, ByteBuffer>(this.keyspace, this.cf, this.keySerializer, this.bs);
		Mutator<K> mutator = HFactory.createMutator(this.keyspace, this.keySerializer, new BatchSizeHint(1, this.mutateInterval));
		ColumnFamilyUpdater<K, ByteBuffer> updater = template.createUpdater(this.destinationKey, mutator);

		SliceQuery<K, ByteBuffer, ByteBuffer> query = HFactory.createSliceQuery(this.keyspace, this.keySerializer, this.bs, this.bs).
						setColumnFamily(this.cf).
						setKey(this.rowKey);

		ColumnSliceIterator<K, ByteBuffer, ByteBuffer> iterator = new ColumnSliceIterator<K, ByteBuffer, ByteBuffer>(query, this.bs.fromBytes(new byte[0]), this.bs.fromBytes(new byte[0]), false);
		while (iterator.hasNext()) {
			HColumn<ByteBuffer, ByteBuffer> column = iterator.next();
			updater.setValue(column.getName(), column.getValue(), this.bs);
		}

		template.update(updater);
	}
}
