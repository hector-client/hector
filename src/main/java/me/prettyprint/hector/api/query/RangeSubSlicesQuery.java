package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.OrderedRows;

/**
 * A query for the call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public interface RangeSubSlicesQuery<SN, N, V> extends Query<OrderedRows<N,V>> {

  RangeSubSlicesQuery<SN, N, V> setKeys(String start, String end);

  RangeSubSlicesQuery<SN, N, V> setRowCount(int rowCount);

  RangeSubSlicesQuery<SN, N, V> setSuperColumn(SN sc);

  RangeSubSlicesQuery<SN, N, V> setColumnNames(N... columnNames);

  RangeSubSlicesQuery<SN, N, V> setRange(N start, N finish, boolean reversed, int count);

  RangeSubSlicesQuery<SN, N, V> setColumnFamily(String cf);
}