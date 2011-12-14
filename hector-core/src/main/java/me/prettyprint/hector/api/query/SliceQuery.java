package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.ColumnSlice;

/**
 * A query for the thrift call get_slice
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public interface SliceQuery<K, N, V> extends Query<ColumnSlice<N, V>> {

  SliceQuery<K, N, V> setKey(K key);

  SliceQuery<K, N, V> setColumnNames(N... columnNames);

  SliceQuery<K, N, V> setRange(N start, N finish, boolean reversed, int count);

  SliceQuery<K, N, V> setColumnFamily(String cf);

}