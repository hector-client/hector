package me.prettyprint.hector.api.beans;


import java.util.List;

/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 * @author Ran Tavory
 *
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface Rows<K, N, V> extends Iterable<Row<K, N, V>> {

  Row<K, N, V> getByKey(K key);

  int getCount();

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<Row<K, N, V>> getList();

  Row<K, N, V> peekLast();
}