package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.CounterRows;

/**
 * A query wrapper for the multiget_slice call
 *
 */
public interface MultigetSliceCounterQuery<K, N> extends Query<CounterRows<K, N>> {

  MultigetSliceCounterQuery<K, N> setKeys(K... keys);
  
  MultigetSliceCounterQuery<K, N> setKeys(Iterable<K> keys);

  /**
   * Sets the column names to be retrieved by this query
   * @param columnNames a list of column names
   */
  MultigetSliceCounterQuery<K, N> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

  MultigetSliceCounterQuery<K, N> setColumnFamily(String cf);

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   *
   * @param start Start key
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  MultigetSliceCounterQuery<K, N> setRange(N start, N finish, boolean reversed, int count);
}