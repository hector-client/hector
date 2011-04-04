package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;

/**
 * Provides access to the current row of data during super column queries. 
 * 
 * @author zznate
 * @param <N> the super column's sub column name type
 */
public class SuperCfResultWrapper<K,SN,N> extends ColumnFamilyResultWrapper<K,N> implements SuperCfResult<K,SN,N> {



  private Serializer<SN> sNameSerializer;
  private SN sColumnName;
  
  public SuperCfResultWrapper(Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> subSerializer,
      ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> executionResult,
      SN sColumnName) {
    super(keySerializer, subSerializer, executionResult);
    this.sNameSerializer = sNameSerializer;
    this.sColumnName = sColumnName;
    
  }
  

  /**
   * Provides access to the current super column name from the mapper objects. This may be
   * needed when the super columm name may be part of the object being mapped into.
   */
  public SN getSuperName() {
    return sColumnName; 
  }

}
