package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.CounterSuperRows;

/**
 * A query wrapper for the call multiget_slice for a slice of supercolumns
 *
 */
public interface MultigetSuperSliceCounterQuery<K, SN, N> extends Query<CounterSuperRows<K, SN, N>>{

  MultigetSuperSliceCounterQuery<K, SN, N> setKeys(K... keys);

  MultigetSuperSliceCounterQuery<K, SN, N> setKeys(Collection<K> keys);

  MultigetSuperSliceCounterQuery<K, SN, N> setColumnFamily(String cf);

  MultigetSuperSliceCounterQuery<K, SN, N> setRange(SN start, SN finish, boolean reversed, int count);

  Collection<SN> getColumnNames();

  /**
   * Sets the column names to be retrieved by this query
   * @param columnNames a list of column names
   */
  MultigetSuperSliceCounterQuery<K, SN, N> setColumnNames(SN... columnNames);

  MultigetSuperSliceCounterQuery<K, SN, N> setColumnNames(Collection<SN> columnNames);

}