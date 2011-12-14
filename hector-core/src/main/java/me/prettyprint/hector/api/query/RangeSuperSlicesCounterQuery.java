package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.OrderedCounterSuperRows;

/**
 * A query for the call get_range_slices of supercolumns
 *
 *
 * @param <SN> type of the supercolumn names
 * @param <N> type of the column names
 */
public interface RangeSuperSlicesCounterQuery<K, SN, N> extends Query<OrderedCounterSuperRows<K, SN,N>> {

  RangeSuperSlicesCounterQuery<K, SN, N> setKeys(K start, K end);

  RangeSuperSlicesCounterQuery<K, SN, N> setRowCount(int rowCount);

  RangeSuperSlicesCounterQuery<K, SN, N> setColumnNames(SN... columnNames);

  RangeSuperSlicesCounterQuery<K, SN, N> setRange(SN start, SN finish, boolean reversed, int count);

  RangeSuperSlicesCounterQuery<K, SN, N> setColumnFamily(String cf);
}