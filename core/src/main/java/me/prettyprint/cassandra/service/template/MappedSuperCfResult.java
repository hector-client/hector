package me.prettyprint.cassandra.service.template;

public interface MappedSuperCfResult<K, SN, N, V> extends SuperCfResult<K, SN, N> {
  
  V getRow();
}
