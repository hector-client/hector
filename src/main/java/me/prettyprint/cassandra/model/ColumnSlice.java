package me.prettyprint.cassandra.model;

import java.util.List;

// get_slice
public interface ColumnSlice {

  List<Column> asColumns();
  List<SuperColumn> asSuperColumns();
  
}
