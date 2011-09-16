package me.prettyprint.cassandra.service;

import java.util.Iterator;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.hector.api.beans.Row;

/**
 *
 * @author thrykol
 */
public class IndexedSlicesIterator<K, N, V> implements Iterator {

	private IndexedSlicesQuery<K, N, V> query;
	private K startKey;
	private Iterator<Row<K, N, V>> iterator;

	public IndexedSlicesIterator(IndexedSlicesQuery<K, N, V> query, K startKey) {
		this.query = query;
		this.startKey = startKey;

		this.query.setStartKey(startKey);
	}

	public boolean hasNext() {
		if (iterator == null) {
			// First time through
			iterator = query.execute().get().getList().iterator();
		} else if (!iterator.hasNext()) {
			query.setStartKey(startKey);
			iterator = query.execute().get().getList().iterator();

			if (iterator.hasNext()) {
				// First element is startKey which was the last element on the previous query result - skip it
				iterator.next();
			}
		}

		return iterator.hasNext();
	}

	public Row<K, N, V> next() {
		Row<K, N, V> row = iterator.next();
		startKey = row.getKey();

		return row;
	}

	public void remove() {
		iterator.remove();
	}
}
