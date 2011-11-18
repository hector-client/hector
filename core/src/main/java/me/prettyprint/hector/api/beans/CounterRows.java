package me.prettyprint.hector.api.beans;


/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 *
 * @param <N> type of the column names
 */
public interface CounterRows<K, N> extends Iterable<CounterRow<K, N>> {

  CounterRow<K, N> getByKey(K key);

  int getCount();

}