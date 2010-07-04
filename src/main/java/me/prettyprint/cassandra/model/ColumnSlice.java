package me.prettyprint.cassandra.model;

import java.util.List;

// get_slice
/**
 */
public class ColumnSlice<N,V> {

  public List<HColumn<N,V>> asColumns() {
    // TODO
    return null;
  }

  public <SN> List<HSuperColumn<SN,N,V>> asSuperColumns() {
    // TODO
    return null;

  }

  public HColumn<N,V> getColumnByName(N columnName) {
    // TODO
    return null;
  }
}
