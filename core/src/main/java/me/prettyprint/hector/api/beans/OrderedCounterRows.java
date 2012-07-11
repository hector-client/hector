package me.prettyprint.hector.api.beans;

import java.util.List;

/**
 * Return type from get_range_slices for simple columns
 * 
 *
 * @param <N> column name type
 */
public interface OrderedCounterRows<K, N> extends CounterRows<K, N>{

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<CounterRow<K, N>> getList();

  CounterRow<K, N> peekLast();

}