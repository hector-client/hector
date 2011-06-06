package me.prettyprint.hector.api.beans;


/**
 * A Row is a touple consisting of a Key and a Column Slice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N> Column name type
 * @param <V> Column value type
 *
 */
public interface Row<K, N, V> {

  K getKey();

  ColumnSlice<N, V> getColumnSlice();

}