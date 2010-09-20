package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.OrderedRows;

/**
 * A query for the call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface RangeSlicesQuery<K, N, V> extends Query<OrderedRows<K, N,V>>{

  RangeSlicesQuery<K, N, V> setKeys(K start, K end);

  RangeSlicesQuery<K, N, V> setRowCount(int rowCount);

  RangeSlicesQuery<K, N, V> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

  RangeSlicesQuery<K, N, V> setColumnFamily(String cf);

  RangeSlicesQuery<K, N, V> setRange(N start, N finish, boolean reversed, int count);

}