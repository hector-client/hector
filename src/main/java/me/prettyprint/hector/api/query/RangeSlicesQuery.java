package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.cassandra.model.OrderedRows;

/**
 * A query for the call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface RangeSlicesQuery<N, V> extends Query<OrderedRows<N,V>>{

  RangeSlicesQuery<N, V> setKeys(String start, String end);

  RangeSlicesQuery<N, V> setRowCount(int rowCount);

  RangeSlicesQuery<N, V> setColumnNames(N... columnNames);

  Collection<N> getColumnNames();

  RangeSlicesQuery<N, V> setColumnFamily(String cf);

  RangeSlicesQuery<N, V> setRange(N start, N finish, boolean reversed, int count);

}