package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.OrderedRows;

/**
 * A query for the call get_range_slices.<br>
 * <br>
 * In order to use comparison expressions (i.e addEqualsExpression) your CF must be configured to use secondary indexes.
 *
 * @author Ran Tavory
 * @author Javier A. Sotelo
 *
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface RangeSlicesQuery<K, N, V> extends Query<OrderedRows<K, N,V>>{

  RangeSlicesQuery<K, N, V> setKeys(K start, K end);

  RangeSlicesQuery<K, N, V> setTokens(K startKey, String startToken, String endToken);

  RangeSlicesQuery<K, N, V> setRowCount(int rowCount);

  RangeSlicesQuery<K, N, V> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

	int getRowCount();
	
  RangeSlicesQuery<K, N, V> setColumnFamily(String cf);

  RangeSlicesQuery<K, N, V> setRange(N start, N finish, boolean reversed, int count);
  
  RangeSlicesQuery<K, N, V> setReturnKeysOnly();
  
  RangeSlicesQuery<K, N, V> addEqualsExpression(N columnName, V columnValue);

  RangeSlicesQuery<K, N, V> addLteExpression(N columnName, V columnValue);

  RangeSlicesQuery<K, N, V> addGteExpression(N columnName, V columnValue);

  RangeSlicesQuery<K, N, V> addLtExpression(N columnName, V columnValue);

  RangeSlicesQuery<K, N, V> addGtExpression(N columnName, V columnValue);

}