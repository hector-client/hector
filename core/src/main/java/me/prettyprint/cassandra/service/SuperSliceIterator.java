package me.prettyprint.cassandra.service;

import java.util.Iterator;
import java.util.List;

import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.query.SuperSliceQuery;

/**
 * Iterates over the super-column slice, refreshing until all qualifing columns are
 * retrieved.
 * If super-column deletion can occur synchronously with calls to {@link #hasNext hasNext()},
 * the super-column name object type must override Object.equals().
 *
 * @author thrykol
 * @author pescuma
 */
public class SuperSliceIterator<K, SN, N, V> implements Iterator<HSuperColumn<SN, N, V>> {

	private static final int DEFAULT_COUNT = 100;
	private SuperSliceQuery<K, SN, N, V> query;
	private Iterator<HSuperColumn<SN, N, V>> iterator;
	private SN start;
	private ColumnSliceFinish<SN> finish;
	private boolean reversed;
	private int count = DEFAULT_COUNT;
	private int columns = 0;

	/**
	 * Constructor
	 *
	 * @param query Base SliceQuery to execute
	 * @param start Starting point of the range
	 * @param finish Finish point of the range.
	 * @param reversed Whether or not the columns should be reversed
	 */
	public SuperSliceIterator(SuperSliceQuery<K, SN, N, V> query, SN start, final SN finish, boolean reversed) {
		this(query, start, finish, reversed, DEFAULT_COUNT);
	}

	/**
	 * Constructor
	 *
	 * @param query Base SliceQuery to execute
	 * @param start Starting point of the range
	 * @param finish Finish point of the range.
	 * @param reversed Whether or not the columns should be reversed
	 * @param count the amount of columns to retrieve per batch
	 */
	public SuperSliceIterator(SuperSliceQuery<K, SN, N, V> query, SN start, final SN finish, boolean reversed, int count) {
		this(query, start, new ColumnSliceFinish<SN>() {

			@Override
			public SN function() {
				return finish;
			}
		}, reversed, count);
	}

	/**
	 * Constructor
	 *
	 * @param query Base SliceQuery to execute
	 * @param start Starting point of the range
	 * @param finish Finish point of the range. Allows for a dynamically
	 * determined point
	 * @param reversed Whether or not the columns should be reversed
	 */
	public SuperSliceIterator(SuperSliceQuery<K, SN, N, V> query, SN start, ColumnSliceFinish<SN> finish, boolean reversed) {
		this(query, start, finish, reversed, DEFAULT_COUNT);
	}

	/**
	 * Constructor
	 *
	 * @param query Base SliceQuery to execute
	 * @param start Starting point of the range
	 * @param finish Finish point of the range. Allows for a dynamically
	 * determined point
	 * @param reversed Whether or not the columns should be reversed
	 * @param count the amount of columns to retrieve per batch
	 */
	public SuperSliceIterator(SuperSliceQuery<K, SN, N, V> query, SN start, ColumnSliceFinish<SN> finish, boolean reversed, int count) {
		this.query = query;
		this.start = start;
		this.finish = finish;
		this.reversed = reversed;
		this.count = count;
		this.query.setRange(this.start, this.finish.function(), this.reversed, this.count);
	}

	@Override
	public boolean hasNext() {
		if (iterator == null) {
			iterator = query.execute().get().getSuperColumns().iterator();
		} else if (!iterator.hasNext() && columns == count) {  // only need to do another query if maximum columns were retrieved
			query.setRange(start, finish.function(), reversed, count);
			columns = 0;
			List<HSuperColumn<SN, N, V>> list = query.execute().get().getSuperColumns();
			iterator = list.iterator();

			if (iterator.hasNext()) {
				// The lower bound column may have been removed prior to the query executing,
				// so check to see if the first column returned by the current query is the same
				// as the lower bound column.  If both columns are the same, skip the column
				SN first = list.get(0).getName();
				if (first.equals(start)) {
					iterator.next();
				}
			}
		}

		return iterator.hasNext();
	}

	@Override
	public HSuperColumn<SN, N, V> next() {
		HSuperColumn<SN, N, V> column = iterator.next();
		start = column.getName();
		columns++;

		return column;
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	/**
	 * When iterating over a ColumnSlice, it may be desirable to move the finish
	 * point for each query. This interface allows for a user defined function
	 * which will return the new finish point. This is especially useful for
	 * column families which have a TimeUUID as the column name.
	 */
	public interface ColumnSliceFinish<N> {

		/**
		 * Generic function for deriving a new finish point.
		 *
		 * @return New finish point
		 */
		N function();
	}
}
