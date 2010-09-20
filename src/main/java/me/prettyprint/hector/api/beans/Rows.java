package me.prettyprint.hector.api.beans;


/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 * @author Ran Tavory
 *
 * @param <N> type of the column names
 * @param <V> type of the column values
 */
public interface Rows<N, V> extends Iterable<Row<N, V>> {

  Row<N, V> getByKey(String key);

  int getCount();

}