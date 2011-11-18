package me.prettyprint.hector.api.beans;

import java.util.List;

/**
 * Return type from get_range_slices for simple columns
 * @author Ran Tavory
 *
 * @param <N> column name type
 * @param <V> column value type
 */
public interface OrderedRows<K, N, V> extends Rows<K, N, V>{

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<Row<K, N, V>> getList();

  Row<K, N, V> peekLast();

}