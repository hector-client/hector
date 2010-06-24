package me.prettyprint.cassandra.model;

// get_range_slices. returns OrderedRows
public interface RangeSlicesQuery<K,N,V> extends AbstractSliceQuery<K,N,OrderedRows<K,N,V>> {

}
