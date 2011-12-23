package me.prettyprint.cassandra.service;

import java.util.Iterator;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.SliceQuery;

/**
 * Iterates over the column slice, refreshing until all qualifing columns are retrieved.
 *
 * @author thrykol
 */
public class ColumnSliceIterator<K, N, V> implements Iterator {

	private SliceQuery<K, N, V> query;
	private Iterator<HColumn<N, V>> iterator;
	private N start;
	private ColumnSliceFinish<N> finish;
	private boolean reversed;
	private int count = 100;
	private int columns = 0;

	/**
	 * Constructor
	 *
	 * @param query Base SliceQuery to execute
	 * @param start Starting point of the range
	 * @param finish Finish point of the range.
	 * @param reversed  Whether or not the columns should be reversed
	 */
	public ColumnSliceIterator(SliceQuery<K, N, V> query, N start, final N finish, boolean reversed) {
		this(query, start, new ColumnSliceFinish<N>() {

			@Override
			public N function() {
				return finish;
			}
		}, reversed);
	}

	/**
	 * Constructor
	 *
	 * @param query Base SliceQuery to execute
	 * @param start Starting point of the range
	 * @param finish Finish point of the range.  Allows for a dynamically determined point
	 * @param reversed  Whether or not the columns should be reversed
	 */
	public ColumnSliceIterator(SliceQuery<K, N, V> query, N start, ColumnSliceFinish<N> finish, boolean reversed) {
		this.query = query;
		this.start = start;
		this.finish = finish;
		this.reversed = reversed;

		this.query.setRange(this.start, this.finish.function(), this.reversed, count);
	}

	@Override
	public boolean hasNext() {
		if (iterator == null) {
			iterator = query.execute().get().getColumns().iterator();
		} else if (!iterator.hasNext() && columns == count) {  // only need to do another query if maximum columns were retrieved
			query.setRange(start, finish.function(), reversed, count);
			iterator = query.execute().get().getColumns().iterator();
			columns = 0;

			// First element is start which was the last element on the previous query result - skip it
			if (iterator.hasNext()) {
				next();
			}
		}

		return iterator.hasNext();
	}

	@Override
	public HColumn<N, V> next() {
		HColumn<N, V> column = iterator.next();
		start = column.getName();
		columns++;

		return column;
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	/**
	 * When iterating over a ColumnSlice, it may be desirable to move the finish point for each query.  This interface
	 * allows for a user defined function which will return the new finish point.  This is especially useful for column
	 * families which have a TimeUUID as the column name.
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
