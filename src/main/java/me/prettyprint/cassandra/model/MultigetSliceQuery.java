package me.prettyprint.cassandra.model;

import java.util.Collection;

// multiget_slice. returns Rows
public interface MultigetSliceQuery<R, K> extends AbstractSliceQuery<Rows<R, K>> {

  SliceQuery<K> setKeys(Collection<String> keys);

}
