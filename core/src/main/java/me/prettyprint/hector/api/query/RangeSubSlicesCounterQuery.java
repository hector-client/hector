package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.OrderedCounterRows;

/**
 * A query for the call get_range_slices on subcolumns of a supercolumns.
 *
 */
public interface RangeSubSlicesCounterQuery<K, SN, N> extends Query<OrderedCounterRows<K, N>> {

  RangeSubSlicesCounterQuery<K, SN, N> setKeys(K start, K end);

  RangeSubSlicesCounterQuery<K, SN, N> setRowCount(int rowCount);

  RangeSubSlicesCounterQuery<K, SN, N> setSuperColumn(SN superColumn);

  RangeSubSlicesCounterQuery<K, SN, N> setColumnNames(N... columnNames);

  RangeSubSlicesCounterQuery<K, SN, N> setRange(N start, N finish, boolean reversed, int count);

  RangeSubSlicesCounterQuery<K, SN, N> setColumnFamily(String cf);

  Collection<N> getColumnNames();

}