package me.prettyprint.cassandra.model;

import java.util.List;

// get_slice
/**
 * @param <K> Column Key type
 */
public class ColumnSlice<K> {

  public List<Column> asColumns() {
    // TODO
    return null;
  }
  
  public List<SuperColumn> asSuperColumns() {
    // TODO
    return null;

  }
  
  public Column getColumnByName(K columnName) {
    // TODO
    return null;
  }
}
