package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;

public class MappedColumnFamilyResultWrapper<K, N, V> extends ColumnFamilyResultWrapper<K, N> implements MappedColumnFamilyResult<K, N, V>{

  private ColumnFamilyRowMapper<K, N, V> rowMapper;
  
  public MappedColumnFamilyResultWrapper(Serializer<K> keySerializer,
      Serializer<N> columnNameSerializer,
      Map<ByteBuffer, List<ColumnOrSuperColumn>> rows, ColumnFamilyRowMapper mapper) {
    super(keySerializer, columnNameSerializer, rows);    
  }

  @Override
  public V getRow() {  
    return rowMapper.mapRow(this);
  }

}
