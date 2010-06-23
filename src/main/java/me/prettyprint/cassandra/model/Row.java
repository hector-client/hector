package me.prettyprint.cassandra.model;

/**
 * 
 * @author Ran Tavory
 *
 * @param <R> Row key type. In 0.7.0 this can be a byte[]. In previous versions this can only be a 
 * String
 */
public interface Row<R, K> {

  // String will become byte[] on 0.7.0
  R getKey();
   
  ColumnSlice<K> getColumnSlice();
}
