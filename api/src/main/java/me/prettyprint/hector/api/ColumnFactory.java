package me.prettyprint.hector.api;

import me.prettyprint.hector.api.beans.HColumn;

public interface ColumnFactory {
  <N, V> HColumn<N, V> createColumn(N name, V value, Serializer<N> nameSerializer, Serializer<V> valueSerializer);
  
  <N, V> HColumn<N, V> createColumn(N name, V value, long clock, Serializer<N> nameSerializer, Serializer<V> valueSerializer);
  
  HColumn<String, String> createStringColumn(String name, String value);
}
