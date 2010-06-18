package me.prettyprint.cassandra.model;


// get_slice. returns ColumnSlice
public interface SliceQuery extends AbstractSliceQuery {
  SliceQuery setKey(String key);
}
