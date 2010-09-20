package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.SuperRows;

/**
 * A query wrapper for the call multiget_slice for a slice of supercolumns
 *
 * @author ran
 */
public interface MultigetSuperSliceQuery<K, SN, N, V> extends Query<SuperRows<K, SN, N, V>>{

  MultigetSuperSliceQuery<K, SN, N, V> setKeys(K... keys);

  MultigetSuperSliceQuery<K, SN, N, V> setColumnFamily(String cf);

  MultigetSuperSliceQuery<K, SN, N, V> setRange(SN start, SN finish, boolean reversed, int count);

  Collection<SN> getColumnNames();

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  MultigetSuperSliceQuery<K, SN, N, V> setColumnNames(SN... columnNames);

}