package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.cassandra.model.SuperRows;

/**
 * A query wrapper for the call multiget_slice for a slice of supercolumns
 *
 * @author ran
 */
public interface MultigetSuperSliceQuery<SN, N, V> extends Query<SuperRows<SN, N, V>>{

  MultigetSuperSliceQuery<SN, N, V> setKeys(String... keys);

  MultigetSuperSliceQuery<SN, N, V> setColumnFamily(String cf);

  MultigetSuperSliceQuery<SN, N, V> setRange(SN start, SN finish, boolean reversed, int count);

  Collection<SN> getColumnNames();

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  MultigetSuperSliceQuery<SN, N, V> setColumnNames(SN... columnNames);

}