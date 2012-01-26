package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.CounterSlice;

/**
 * A query for the call get_slice on subcolumns of a supercolumns.
 *
 * @param <SN> super column name type
 * @param <N> column name type
 */
public interface SubSliceCounterQuery<K, SN, N> extends Query<CounterSlice<N>>{

  SubSliceCounterQuery<K, SN, N> setKey(K key);

  /**
   * Set the supercolumn to run the slice query on
   */
  SubSliceCounterQuery<K, SN, N> setSuperColumn(SN superColumn);

  SubSliceCounterQuery<K, SN, N> setColumnNames(N... columnNames);

  SubSliceCounterQuery<K, SN, N> setRange(N start, N finish, boolean reversed, int count);

  SubSliceCounterQuery<K, SN, N> setColumnFamily(String cf);

  Collection<N> getColumnNames();

}