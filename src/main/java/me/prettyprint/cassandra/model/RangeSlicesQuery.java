package me.prettyprint.cassandra.model;

// get_range_slices. returns OrderedRows
public interface RangeSlicesQuery<R, K> extends AbstractSliceQuery<OrderedRows<R, K>> {

}
