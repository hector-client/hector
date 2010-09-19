package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.Rows;

/**
 * A query wrapper for the call multiget_slice for subcolumns  of supercolumns
 *
 * @author ran
 */
public interface MultigetSubSliceQuery<SN, N, V> extends Query<Rows<N, V>>{

  MultigetSubSliceQuery<SN, N, V> setKeys(String... keys);

  /**
   * Set the supercolumn to run the slice query on
   */
  MultigetSubSliceQuery<SN, N, V> setSuperColumn(SN superColumn);

  MultigetSubSliceQuery<SN, N, V> setColumnFamily(String cf);

  MultigetSubSliceQuery<SN, N, V> setRange(N start, N finish, boolean reversed, int count);

  Collection<N> getColumnNames();

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  MultigetSubSliceQuery<SN, N, V> setColumnNames(N... columnNames);

}