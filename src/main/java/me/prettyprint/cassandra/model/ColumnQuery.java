package me.prettyprint.cassandra.model;

// like a simple get operation
// may return a Column or a SuperColumn
public interface ColumnQuery extends Query<Column> {
  ColumnQuery setKey(String key);
  ColumnQuery setName(String name);
}
