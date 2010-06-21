package me.prettyprint.cassandra.model;

public interface SuperColumnQuery extends Query<SuperColumn> {
  
  SuperColumnQuery setKey(String key);
  SuperColumnQuery setName(String name);
}
