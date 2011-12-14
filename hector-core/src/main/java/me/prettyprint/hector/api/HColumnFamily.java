package me.prettyprint.hector.api;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import me.prettyprint.hector.api.beans.HColumn;

public interface HColumnFamily<K, N> extends Iterator<HColumnFamily<K,N>>,ResultStatus {

  HColumnFamily<K, N> setReadConsistencyLevel(HConsistencyLevel readLevel);
  
  HColumnFamily<K, N> setWriteConsistencyLevel(HConsistencyLevel writeLevel);
  
  HColumnFamily<K, N> addKey(K key);
  
  HColumnFamily<K, N> addKeys(Collection<K> keys);
  
  HColumnFamily<K, N> removeKeys();  
  
  HColumnFamily<K, N> clear();
  
  HColumnFamily<K, N> setStart(N name);
  
  HColumnFamily<K, N> setFinish(N name);
  
  HColumnFamily<K, N> setCount(int count);
  
  HColumnFamily<K, N> setReversed(boolean reversed);
  
  HColumnFamily<K, N> setColumnNames(Collection<N> columnNames);
  
  HColumnFamily<K, N> addColumnName(N columnName);
      
  Collection<HColumn<N, ByteBuffer>> getColumns();
  
  HColumn<N,?> getColumn(N name);
  
  String getString(N name);
  
  int getInt(N name);
  
  long getLong(N name);
  
  UUID getUUID(N name);
  
  Date getDate(N name);
  
  double getDouble(N name);
  
  <V> V getValue(N name, Serializer<V> valueSerializer);
  
}
