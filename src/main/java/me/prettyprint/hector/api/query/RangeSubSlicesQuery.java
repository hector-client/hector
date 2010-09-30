package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.OrderedRows;

/**
 * A query for the call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public interface RangeSubSlicesQuery<K, SN, N, V> extends Query<OrderedRows<K,N,V>> {

  RangeSubSlicesQuery<K, SN, N, V> setKeys(K start, K end);

  RangeSubSlicesQuery<K, SN, N, V> setRowCount(int rowCount);

  RangeSubSlicesQuery<K, SN, N, V> setSuperColumn(SN sc);

  RangeSubSlicesQuery<K, SN, N, V> setColumnNames(N... columnNames);

  RangeSubSlicesQuery<K, SN, N, V> setRange(N start, N finish, boolean reversed, int count);

  RangeSubSlicesQuery<K, SN, N, V> setColumnFamily(String cf);
}