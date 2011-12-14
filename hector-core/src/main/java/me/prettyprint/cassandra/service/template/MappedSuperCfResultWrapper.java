package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;

public class MappedSuperCfResultWrapper<K, SN, N, V> extends SuperCfResultWrapper<K, SN, N> implements
    MappedSuperCfResult<K, SN, N, V> {
  
  private SuperCfRowMapper<K, SN, N, V> rowMapper;

  public MappedSuperCfResultWrapper(
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> subSerializer,
      ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> executionResult,
      SuperCfRowMapper<K, SN, N, V> mapper) {
    super(keySerializer, sNameSerializer, subSerializer, executionResult);
    this.rowMapper = mapper;
  }

  @Override
  public V getRow() { 
    return rowMapper.mapRow(this);
  }
  
  

}
