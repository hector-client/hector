package me.prettyprint.cassandra.service;

import java.util.Iterator;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.hector.api.beans.Row;

/**
 * This class will soon be removed, use {@link RangeSlicesIterator} instead.<br>
 * <br>
 * Iterates over the index slices, automatically refreshing the query until all matching rows are returned.
 *
 * @author thrykol
 */
@Deprecated
public class IndexedSlicesIterator<K, N, V> implements Iterator<Row<K, N, V>> {

	private IndexedSlicesQuery<K, N, V> query;
	private K startKey;
	private Iterator<Row<K, N, V>> iterator;
	private int rows = 0;

	public IndexedSlicesIterator(IndexedSlicesQuery<K, N, V> query, K startKey) {
		this.query = query;
		this.startKey = startKey;

		this.query.setStartKey(startKey);
	}

	@Override
	public boolean hasNext() {
		if (iterator == null) {
			// First time through
			iterator = query.execute().get().getList().iterator();
		} else if (!iterator.hasNext() && rows == query.getRowCount()) {  // only need to do another query if maximum rows were retrieved
			query.setStartKey(startKey);
			iterator = query.execute().get().getList().iterator();
			rows = 0;
			
			if (iterator.hasNext()) {
				// First element is startKey which was the last element on the previous query result - skip it
				next();
			}
		}

		return iterator.hasNext();
	}

	@Override
	public Row<K, N, V> next() {
		Row<K, N, V> row = iterator.next();
		startKey = row.getKey();
		rows++;

		return row;
	}

	@Override
	public void remove() {
		iterator.remove();
	}
}
