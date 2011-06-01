package me.prettyprint.hector.api.beans;


/**
 * A Row is a touple consisting of a Key and a Column Slice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * 
 *
 * @param <N> Column name type
 *
 */
public interface CounterRow<K, N> {

  K getKey();

  CounterSlice<N> getColumnSlice();

}