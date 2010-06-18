package me.prettyprint.cassandra.model;

public interface Row {

  // String will become byte[] on 0.7.0
  String getKey();
  
  Column asColumn();
  SuperColumn asSuperColumn();
  ColumnSlice asColumnSlice();
}
