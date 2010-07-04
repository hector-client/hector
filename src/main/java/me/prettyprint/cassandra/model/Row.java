package me.prettyprint.cassandra.model;

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
 * Not implemented yet:
 * @param <K> Row Key type. In 0.7.0 this can be a byte[]. In previous versions this can only be a
 * String
 */
public interface Row<N,V> {

  // String will become byte[] on 0.7.0
  String getKey();

  ColumnSlice<N,V> getColumnSlice();
}
