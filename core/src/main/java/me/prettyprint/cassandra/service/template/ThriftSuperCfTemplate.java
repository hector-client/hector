package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.model.thrift.ThriftConverter;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;

public class ThriftSuperCfTemplate<K, SN, N> extends SuperCfTemplate<K, SN, N> {

  public ThriftSuperCfTemplate(Keyspace keyspace, String columnFamily,
      Serializer<K> keySerializer, Serializer<SN> topSerializer,
      Serializer<N> subSerializer) {
    super(keyspace, columnFamily, keySerializer, topSerializer, subSerializer);
  }
  
  protected SuperCfResult<K,SN,N> doExecuteSlice(K key, SN sColumnName, HSlicePredicate<SN> predicate) {
    ColumnParent workingColumnParent = columnParent.deepCopy();
    workingColumnParent.setSuper_column(topSerializer.toByteBuffer(sColumnName));
    SuperCfResultWrapper<K, SN, N> wrapper = 
      new SuperCfResultWrapper<K, SN, N>(keySerializer, topSerializer, subSerializer, 
          sliceInternal(key, workingColumnParent, predicate), 
          sColumnName);
    return wrapper;
  } 
  
  private ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> sliceInternal(final K key,
      final ColumnParent workingColumnParent,
      final HSlicePredicate<SN> workingSlicePredicate) {
    return ((ExecutingKeyspace)keyspace).doExecuteOperation(new Operation<Map<ByteBuffer,List<ColumnOrSuperColumn>>>(OperationType.READ) {
      @Override
      public Map<ByteBuffer,List<ColumnOrSuperColumn>> execute(Cassandra.Client cassandra) throws HectorException {
        Map<ByteBuffer,List<ColumnOrSuperColumn>> cosc = new LinkedHashMap<ByteBuffer, List<ColumnOrSuperColumn>>();
        try {          

          ByteBuffer sKey = keySerializer.toByteBuffer(key);
          cosc.put(sKey, cassandra.get_slice(sKey, workingColumnParent,
              workingSlicePredicate.toThrift(), 
              ThriftConverter.consistencyLevel(consistencyLevelPolicy.get(operationType))));

        } catch (Exception e) {
          throw exceptionsTranslator.translate(e);
        }        

        return cosc;
      }
    });
  }

}
