package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.OrderedCounterRows;
import me.prettyprint.hector.api.beans.OrderedRows;

import java.util.Collection;

/**
 * A query for the call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N> type of the column names
 */
public interface RangeSlicesCounterQuery<K, N> extends Query<OrderedCounterRows<K, N>>{

  RangeSlicesCounterQuery<K, N> setKeys(K start, K end);

  RangeSlicesCounterQuery<K, N> setRowCount(int rowCount);

  RangeSlicesCounterQuery<K, N> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

  RangeSlicesCounterQuery<K, N> setColumnFamily(String cf);

  RangeSlicesCounterQuery<K, N> setRange(N start, N finish, boolean reversed, int count);
  
  RangeSlicesCounterQuery<K, N> setReturnKeysOnly();

}