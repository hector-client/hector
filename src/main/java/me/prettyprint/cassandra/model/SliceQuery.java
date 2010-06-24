package me.prettyprint.cassandra.model;


// get_slice. returns ColumnSlice
public interface SliceQuery<K,N,V> extends AbstractSliceQuery<K,N,ColumnSlice<K,N,V>> {
  
  SliceQuery<K,N,V> setKey(K key);
}
