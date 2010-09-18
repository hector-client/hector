package me.prettyprint.hector.api.query;

import me.prettyprint.cassandra.model.OrderedSuperRows;

/**
 * A query for the call get_range_slices of supercolumns
 *
 * @author Ran Tavory
 *
 * @param <SN> type of the supercolumn names
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface RangeSuperSlicesQuery<SN, N, V> extends Query<OrderedSuperRows<SN,N, V>>{

  RangeSuperSlicesQuery<SN, N, V> setKeys(String start, String end);

  RangeSuperSlicesQuery<SN, N, V> setRowCount(int rowCount);

  RangeSuperSlicesQuery<SN, N, V> setColumnNames(SN... columnNames);

  RangeSuperSlicesQuery<SN, N, V> setRange(SN start, SN finish, boolean reversed, int count);

  RangeSuperSlicesQuery<SN, N, V> setColumnFamily(String cf);
}