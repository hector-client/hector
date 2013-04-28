package me.prettyprint.cassandra.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.Iterator;
import me.prettyprint.cassandra.service.template.SliceFilter;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

/**
 * Iterates over the range slices, automatically refreshing the query until all matching rows are returned.
 *
 * @author thrykol
 */
public class RangeSlicesIterator<K, N, V> implements Iterator<Row<K, N, V>> {

	private RangeSlicesQuery<K, N, V> query;
	private K startKey;
	private K endKey;
	private PeekingIterator<Row<K, N, V>> iterator;
	private SliceFilter<Row<K, N, V>> filter = null;
	private int rows = 0;

	public RangeSlicesIterator(RangeSlicesQuery<K, N, V> query, K startKey, K endKey) {
		this.query = query;
		this.startKey = startKey;
		this.endKey = endKey;

		this.query.setKeys(startKey, endKey);
	}

	@Override
	public boolean hasNext() {
		if (iterator == null) {
			// First time through
			iterator = Iterators.peekingIterator(query.execute().get().getList().iterator());
		} else if (!iterator.hasNext() && rows == query.getRowCount()) {  // only need to do another query if maximum rows were retrieved
			query.setKeys(startKey, endKey);
			iterator = Iterators.peekingIterator(query.execute().get().getList().iterator());
			rows = 0;

			if (iterator.hasNext()) {
				// First element is startKey which was the last element on the previous query result - skip it
				next();
			}
		}

		while(filter != null && iterator != null && iterator.hasNext() && !filter.accept(iterator.peek())) {
			next();

			if(!iterator.hasNext() && rows == query.getRowCount()) {
				refresh();
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

	public RangeSlicesIterator<K, N, V> setFilter(SliceFilter<Row<K, N, V>> filter) {
		this.filter = filter;

		return this;
	}
	
	private void refresh() {
		query.setKeys(startKey, endKey);
		iterator = Iterators.peekingIterator(query.execute().get().getList().iterator());
		rows = 0;

		if (iterator.hasNext()) {
			// First element is startKey which was the last element on the previous query result - skip it
			next();
		}
	}
}
