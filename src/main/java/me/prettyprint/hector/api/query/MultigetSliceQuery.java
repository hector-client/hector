package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.cassandra.model.Rows;

/**
 * A query wrapper for the multiget_slice call
 *
 * @author ran
 */
public interface MultigetSliceQuery<N, V> extends Query<Rows<N,V>> {

  MultigetSliceQuery<N, V> setKeys(String... keys);

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  public MultigetSliceQuery<N, V> setColumnNames(N... columnNames);

  public Collection<N> getColumnNames();

  public MultigetSliceQuery<N, V> setColumnFamily(String cf);

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   *
   * @param start Start key
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  public MultigetSliceQuery<N, V> setRange(N start, N finish, boolean reversed, int count);
}