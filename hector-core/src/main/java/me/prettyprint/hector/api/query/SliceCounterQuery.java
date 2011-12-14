package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.CounterSlice;

/**
 * A query for the thrift call get_counter_slice
 * 
 * @author patricioe (Patricio Echague)
 *
 * @param <N> Name Type
 */
public interface SliceCounterQuery<K, N> extends Query<CounterSlice<N>> {

  SliceCounterQuery<K, N> setKey(K key);

  SliceCounterQuery<K, N> setColumnNames(N... columnNames);

  SliceCounterQuery<K, N> setRange(N start, N finish, boolean reversed, int count);

  SliceCounterQuery<K, N> setColumnFamily(String cf);

}