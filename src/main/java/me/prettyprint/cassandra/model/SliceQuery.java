package me.prettyprint.cassandra.model;


// get_slice. returns ColumnSlice
public interface SliceQuery extends AbstractSliceQuery<ColumnSlice> {
  
  SliceQuery setKey(String key);
}
