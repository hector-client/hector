package me.prettyprint.hector.api.beans;


import java.util.List;

/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns) and RangeSuperSlicesQuery (get_range_slices)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public interface SuperRows<K, SN, N, V> extends Iterable<SuperRow<K, SN, N, V>>{

  SuperRow<K, SN, N, V> getByKey(K key);

  int getCount();

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<SuperRow<K, SN, N, V>> getList();

  SuperRow<K, SN, N, V> peekLast();
}