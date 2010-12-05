package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.Rows;

/**
 * A query wrapper for the call multiget_slice for subcolumns  of supercolumns
 *
 * @author ran
 */
public interface MultigetSubSliceQuery<K, SN, N, V> extends Query<Rows<K, N, V>>{

  MultigetSubSliceQuery<K, SN, N, V> setKeys(K... keys);

  /**
   * Set the supercolumn to run the slice query on
   */
  MultigetSubSliceQuery<K, SN, N, V> setSuperColumn(SN superColumn);

  MultigetSubSliceQuery<K, SN, N, V> setColumnFamily(String cf);

  MultigetSubSliceQuery<K, SN, N, V> setRange(N start, N finish, boolean reversed, int count);

  Collection<N> getColumnNames();

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  MultigetSubSliceQuery<K, SN, N, V> setColumnNames(N... columnNames);

}