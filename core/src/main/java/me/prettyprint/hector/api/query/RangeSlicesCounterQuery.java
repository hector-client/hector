package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.OrderedCounterRows;

/**
 * A query for the call get_range_slices.
 *
 * 
 *
 * @param <N> type of the column names
 */
public interface RangeSlicesCounterQuery<K, N> extends Query<OrderedCounterRows<K, N>>{

  RangeSlicesCounterQuery<K, N> setKeys(K start, K end);

  RangeSlicesCounterQuery<K, N> setTokens(K startKey, String startToken, String endToken);

  RangeSlicesCounterQuery<K, N> setRowCount(int rowCount);

  RangeSlicesCounterQuery<K, N> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

  RangeSlicesCounterQuery<K, N> setColumnFamily(String cf);

  RangeSlicesCounterQuery<K, N> setRange(N start, N finish, boolean reversed, int count);
  
  RangeSlicesCounterQuery<K, N> setReturnKeysOnly();

}