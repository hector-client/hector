package me.prettyprint.hector.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.hector.api.beans.HColumn;

public interface HColumnFamily<K, N>  {

  HColumnFamily<K, N> setConsistencyLevelPolicy(ConsistencyLevelPolicy policy);
  
  HColumnFamily<K, N> addKey(K key);
  
  HColumnFamily<K, N> addKeys(Collection<K> keys);
  
  HColumnFamily<K, N> setStart(N name);
  
  HColumnFamily<K, N> setFinish(N name);
  
  HColumnFamily<K, N> setCount(int count);
  
  HColumnFamily<K, N> setReversed(boolean reversed);
  
  HColumnFamily<K, N> setColumnNames(Collection<N> columnNames);
  
  HColumnFamily<K, N> addColumnName(N columnName);
      
  List<HColumn<N, ?>> getColumns();
  
  HColumn<N,?> getColumn(N name);
  
  String getString(N name);
  
  int getInt(N name);
  
  long getLong(N name);
  
  UUID getUUID(N name);
  
  Date getDate(N name);
  
  double getDouble(N name);
  
}
