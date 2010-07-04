package me.prettyprint.cassandra.model;

import java.util.Collection;

// multiget_slice. returns Rows
public interface MultigetSliceQuery<N,V> extends AbstractSliceQuery<N,Rows<N,V>> {

  SliceQuery<N,V> setKeys(Collection<String> keys);

}
