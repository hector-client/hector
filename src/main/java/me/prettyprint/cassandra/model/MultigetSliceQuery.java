package me.prettyprint.cassandra.model;

import java.util.Collection;

// multiget_slice. returns Rows
public interface MultigetSliceQuery<K,N,V> extends AbstractSliceQuery<K,N,Rows<K,N,V>> {

  SliceQuery<K,N,V> setKeys(Collection<K> keys);

}
