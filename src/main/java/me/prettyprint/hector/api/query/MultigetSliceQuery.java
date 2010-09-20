package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.Rows;

/**
 * A query wrapper for the multiget_slice call
 *
 * @author ran
 */
public interface MultigetSliceQuery<K, N, V> extends Query<Rows<K, N, V>> {

  MultigetSliceQuery<K, N, V> setKeys(K... keys);

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  MultigetSliceQuery<K, N, V> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

  MultigetSliceQuery<K, N, V> setColumnFamily(String cf);

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   *
   * @param start Start key
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  MultigetSliceQuery<K, N, V> setRange(N start, N finish, boolean reversed, int count);
}