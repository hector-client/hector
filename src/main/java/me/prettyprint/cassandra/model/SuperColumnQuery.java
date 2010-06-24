package me.prettyprint.cassandra.model;

public interface SuperColumnQuery<SN,N,V> extends Query<HSuperColumn<SN,N,V>> {
  
  SuperColumnQuery<SN,N,V> setKey(String key);
  SuperColumnQuery<SN,N,V> setName(String name);
  
}
