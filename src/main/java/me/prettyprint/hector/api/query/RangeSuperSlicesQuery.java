package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.OrderedSuperRows;

/**
 * A query for the call get_range_slices of supercolumns
 *
 * @author Ran Tavory
 *
 * @param <SN> type of the supercolumn names
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface RangeSuperSlicesQuery<K, SN, N, V> extends Query<OrderedSuperRows<K, SN,N, V>> {

  RangeSuperSlicesQuery<K, SN, N, V> setKeys(K start, K end);

  RangeSuperSlicesQuery<K, SN, N, V> setRowCount(int rowCount);

  RangeSuperSlicesQuery<K, SN, N, V> setColumnNames(SN... columnNames);

  RangeSuperSlicesQuery<K, SN, N, V> setRange(SN start, SN finish, boolean reversed, int count);

  RangeSuperSlicesQuery<K, SN, N, V> setColumnFamily(String cf);
}