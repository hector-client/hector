package me.prettyprint.hector.api.beans;


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

}