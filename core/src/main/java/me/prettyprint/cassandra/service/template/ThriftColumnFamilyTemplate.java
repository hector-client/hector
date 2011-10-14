package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.model.thrift.ThriftConverter;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.mutation.Mutator;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;

import com.google.common.collect.Iterators;

/**
 * Thrift specific implementation of {@link ColumnFamilyTemplate} 
 * @author nate
 *
 * @param <K>
 * @param <N>
 */
public class ThriftColumnFamilyTemplate<K, N> extends ColumnFamilyTemplate<K, N> {
  
  
  public ThriftColumnFamilyTemplate(Keyspace keyspace, String columnFamily,
      Serializer<K> keySerializer, Serializer<N> topSerializer) {
    super(keyspace, columnFamily, keySerializer, topSerializer);
  }
  
  public <T> T doExecuteSlice(K key, HSlicePredicate<N> predicate, ColumnFamilyRowMapper<K, N, T> mapper) {
    return mapper.mapRow(doExecuteSlice(key,predicate));
  }
    
  public ColumnFamilyResult<K, N> doExecuteSlice(final K key, final HSlicePredicate<N> workingSlicePredicate) {    
    return new ColumnFamilyResultWrapper<K, N>(keySerializer, topSerializer, 
        sliceInternal(key, workingSlicePredicate));
  }
    
  public ColumnFamilyResult<K, N> doExecuteMultigetSlice(final Iterable<K> keys, final HSlicePredicate<N> workingSlicePredicate) {    
    return new ColumnFamilyResultWrapper<K, N>(keySerializer, topSerializer, 
        multigetSliceInternal(keys, workingSlicePredicate));
  }

  public <V> MappedColumnFamilyResult<K, N, V> doExecuteMultigetSlice(final Iterable<K> keys, 
      final HSlicePredicate<N> workingSlicePredicate,
      final ColumnFamilyRowMapper<K, N, V> mapper) {    
    return new MappedColumnFamilyResultWrapper<K,N,V>(keySerializer, topSerializer, 
        multigetSliceInternal(keys, workingSlicePredicate), mapper);    
  }

  
  private ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> sliceInternal(final K key,
      final HSlicePredicate<N> workingSlicePredicate) {
    return ((ExecutingKeyspace)keyspace).doExecuteOperation(new Operation<Map<ByteBuffer,List<ColumnOrSuperColumn>>>(OperationType.READ) {
      @Override
      public Map<ByteBuffer,List<ColumnOrSuperColumn>> execute(Cassandra.Client cassandra) throws HectorException {
        Map<ByteBuffer,List<ColumnOrSuperColumn>> cosc = new LinkedHashMap<ByteBuffer, List<ColumnOrSuperColumn>>();
        try {          

          ByteBuffer sKey = keySerializer.toByteBuffer(key);
          cosc.put(sKey, cassandra.get_slice(sKey, columnParent,
              workingSlicePredicate.toThrift(), 
              ThriftConverter.consistencyLevel(consistencyLevelPolicy.get(operationType))));

        } catch (Exception e) {
          throw exceptionsTranslator.translate(e);
        }        

        return cosc;
      }
    });
  }
  
  private ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> multigetSliceInternal(final Iterable<K> keys,
      final HSlicePredicate<N> workingSlicePredicate) {
    return ((ExecutingKeyspace)keyspace).doExecuteOperation(new Operation<Map<ByteBuffer,List<ColumnOrSuperColumn>>>(OperationType.READ) {
      @Override
      public Map<ByteBuffer,List<ColumnOrSuperColumn>> execute(Cassandra.Client cassandra) throws HectorException {
        Map<ByteBuffer,List<ColumnOrSuperColumn>> cosc = new LinkedHashMap<ByteBuffer, List<ColumnOrSuperColumn>>();
        try {          
          List<K> keyList = new ArrayList<K>();
          Iterators.addAll(keyList, keys.iterator());
          cosc = cassandra.multiget_slice(keySerializer.toBytesList(keyList), columnParent,
              (workingSlicePredicate == null ? activeSlicePredicate.setColumnNames(columnValueSerializers.keySet()).toThrift() : workingSlicePredicate.toThrift()),              
            ThriftConverter.consistencyLevel(consistencyLevelPolicy.get(operationType)));
        } catch (Exception e) {
          throw exceptionsTranslator.translate(e);
        }        

        return cosc;
      }
    });
  }
}
