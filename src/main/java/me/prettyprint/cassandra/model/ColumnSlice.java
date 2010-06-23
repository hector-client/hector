package me.prettyprint.cassandra.model;

import java.util.List;

// get_slice
/**
 * @param <K> Column Key type
 */
public interface ColumnSlice<K> {

  List<Column> asColumns();
  List<SuperColumn> asSuperColumns();
  
  Column getColumnByName(K columnName);
}
