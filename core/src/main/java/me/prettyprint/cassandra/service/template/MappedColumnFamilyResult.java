package me.prettyprint.cassandra.service.template;
public interface MappedColumnFamilyResult<K,N,V> extends ColumnFamilyResult<K, N>, Iterable<V>{

  V getRow();
  
}
