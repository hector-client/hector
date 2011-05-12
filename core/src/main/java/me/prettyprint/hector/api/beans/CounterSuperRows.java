package me.prettyprint.hector.api.beans;


/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns)
 *
 * @author Ran Tavory
 *
 * @param <N>
 */
public interface CounterSuperRows<K, SN, N> extends Iterable<CounterSuperRow<K, SN, N>>{

  CounterSuperRow<K, SN, N> getByKey(K key);

  int getCount();

}