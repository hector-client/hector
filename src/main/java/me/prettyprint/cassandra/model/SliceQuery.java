package me.prettyprint.cassandra.model;


// get_slice. returns ColumnSlice
public interface SliceQuery<N,V> extends AbstractSliceQuery<N,ColumnSlice<N,V>> {

  SliceQuery<N,V> setKey(String key);
}
