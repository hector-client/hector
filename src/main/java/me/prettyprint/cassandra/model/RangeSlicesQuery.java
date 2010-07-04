package me.prettyprint.cassandra.model;

// get_range_slices. returns OrderedRows
public interface RangeSlicesQuery<N,V> extends AbstractSliceQuery<N,OrderedRows<N,V>> {

}
