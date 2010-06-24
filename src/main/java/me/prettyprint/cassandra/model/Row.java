package me.prettyprint.cassandra.model;

/**
 * 
 * @author Ran Tavory
 *
 * @param <K> Row Key type. In 0.7.0 this can be a byte[]. In previous versions this can only be a 
 * String
 * @param <N> Column name type
 * @param <V> Column value type
 */
public interface Row<K,N,V> {

  // String will become byte[] on 0.7.0
  K getKey();
   
  ColumnSlice<K,N,V> getColumnSlice();
}
