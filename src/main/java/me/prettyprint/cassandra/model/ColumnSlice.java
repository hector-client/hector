package me.prettyprint.cassandra.model;

import java.util.List;

// get_slice
/**
 * @param <K> Column Key type
 */
public class ColumnSlice<K,N,V> {

  public List<HColumn<N,V>> asColumns() {
    // TODO
    return null;
  }
  
  public <SN> List<HSuperColumn<SN,N,V>> asSuperColumns() {
    // TODO
    return null;

  }
  
  public HColumn<N,V> getColumnByName(K columnName) {
    // TODO
    return null;
  }
}
