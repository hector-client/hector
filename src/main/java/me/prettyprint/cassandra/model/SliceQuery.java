package me.prettyprint.cassandra.model;


// get_slice. returns ColumnSlice
public interface SliceQuery<K> extends AbstractSliceQuery<ColumnSlice<K>> {
  
  SliceQuery<K> setKey(String key);
}
