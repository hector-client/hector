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
public interface SubSliceQuery<SN, N, V> extends Query<ColumnSlice<N, V>>{

  SubSliceQuery<SN, N, V> setKey(String key);

  /**
   * Set the supercolumn to run the slice query on
   */
  SubSliceQuery<SN, N, V> setSuperColumn(SN superColumn);

  SubSliceQuery<SN, N, V> setColumnNames(N... columnNames);

  SubSliceQuery<SN, N, V> setRange(N start, N finish, boolean reversed, int count);

  SubSliceQuery<SN, N, V> setColumnFamily(String cf);

  Collection<N> getColumnNames();

}