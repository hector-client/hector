package me.prettyprint.hector.api.beans;


/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public interface SuperRows<SN, N, V> extends Iterable<SuperRow<SN, N, V>>{

  SuperRow<SN, N, V> getByKey(String key);

  int getCount();

}