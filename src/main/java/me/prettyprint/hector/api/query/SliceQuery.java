package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.ColumnSlice;

/**
 * A query for the thrift call get_slice
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public interface SliceQuery<N, V> extends Query<ColumnSlice<N, V>> {

  SliceQuery<N, V> setKey(String key);

  SliceQuery<N, V> setColumnNames(N... columnNames);

  SliceQuery<N, V> setRange(N start, N finish, boolean reversed, int count);

  SliceQuery<N, V> setColumnFamily(String cf);

}