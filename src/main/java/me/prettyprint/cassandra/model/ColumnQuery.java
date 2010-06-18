package me.prettyprint.cassandra.model;

// like a simple get operation
// may return a Column or a SuperColumn
public interface ColumnQuery extends Query {
  ColumnQuery setKey(String key);
  ColumnQuery setName(String name);
}
