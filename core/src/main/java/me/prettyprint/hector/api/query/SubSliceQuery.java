package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.ColumnSlice;

/**
 * A query for the call get_slice on subcolumns of a supercolumns.
 *
 * @author Ran Tavory
 *
 * @param <SN> super column name type
 * @param <N> column name type
 * @param <V> column value type
 */
public interface SubSliceQuery<K, SN, N, V> extends Query<ColumnSlice<N, V>>{

  SubSliceQuery<K, SN, N, V> setKey(K key);

  /**
   * Set the supercolumn to run the slice query on
   */
  SubSliceQuery<K, SN, N, V> setSuperColumn(SN superColumn);

  SubSliceQuery<K, SN, N, V> setColumnNames(N... columnNames);

  SubSliceQuery<K, SN, N, V> setRange(N start, N finish, boolean reversed, int count);

  SubSliceQuery<K, SN, N, V> setColumnFamily(String cf);

  Collection<N> getColumnNames();

}