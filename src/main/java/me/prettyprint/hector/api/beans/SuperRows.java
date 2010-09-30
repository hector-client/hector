package me.prettyprint.hector.api.beans;


/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public interface SuperRows<K, SN, N, V> extends Iterable<SuperRow<K, SN, N, V>>{

  SuperRow<K, SN, N, V> getByKey(K key);

  int getCount();

}