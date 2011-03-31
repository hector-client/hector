package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.HSuperColumnImpl;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;

/**
 * Provides access to the current row of data during super column queries. 
 * 
 * This class is a non-static inner class which inherits the Java generic parameters
 * of it's containing CassandraTemplate instance. This allows it to inherit the 
 * <KEY> and <COL> parameter and adds the <SUBCOL> type passed in from whichever
 * query***() method was called.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <N> the super column's sub column name type
 */
public class SuperCfResultWrapper<K,SN,N> extends AbstractResultWrapper<K,N> implements SuperCfResult<K,SN,N> {

  private Map<SN,HSuperColumn<SN,N,ByteBuffer>> columns = new LinkedHashMap<SN,HSuperColumn<SN,N,ByteBuffer>>();
  private Iterator<Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>>> rows;
  private Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry;
  private ExecutionResult<Map<ByteBuffer,List<ColumnOrSuperColumn>>> executionResult;
  private Serializer<N> subSerializer;
  protected Serializer<SN> columnNameSerializer;
  
  public SuperCfResultWrapper(Serializer<K> keySerializer,
      Serializer<SN> topSerializer,
      Serializer<N> subSerializer,
      ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> executionResult) {
    super(keySerializer, null, executionResult);
    this.columnNameSerializer = topSerializer;
    this.subSerializer = subSerializer;
    this.rows = executionResult.get().entrySet().iterator();    
    next();
  }

  @Override
  public SuperCfResult<K, SN, N> next() {
    if ( !hasNext() ) {
      throw new NoSuchElementException("No more rows left on this HColumnFamily");
    }
    entry = rows.next();    
    applyToRow(keySerializer.fromByteBuffer(entry.getKey()), entry.getValue());
    return this;
  }
  
  @Override
  public boolean hasNext() {
    return rows.hasNext();
  }

  @Override
  public void remove() {
    rows.remove();
  }

  private void applyToRow(K key, List<ColumnOrSuperColumn> cosclist) {
    HSuperColumn<SN, N, ByteBuffer> column;
    SN colName;
    for (Iterator<ColumnOrSuperColumn> iterator = cosclist.iterator(); iterator.hasNext();) {
      ColumnOrSuperColumn cosc = iterator.next();            

      colName = columnNameSerializer.fromByteBuffer(cosc.getSuper_column().name.duplicate());
      column = new HSuperColumnImpl(cosc.getSuper_column(), columnNameSerializer, subSerializer, ByteBufferSerializer.get());
      // TODO cache columns
      // TODO add clear() on HSuperColumnImpl
      columns.put(colName, column); 
      iterator.remove();
    }
  }

  @Override
  public K getKey() {    
    return keySerializer.fromByteBuffer(entry.getKey());
  }
  

  /**
   * Provides access to the current super column name from the mapper objects. This may be
   * needed when the super columm name may be part of the object being mapped into.
   */
  public SN getSuperName() {
    return columnNameSerializer.fromByteBuffer(entry.getKey());
  }


  private HSuperColumn<SN,N,ByteBuffer> getColumn( SN columnName ) {
    return columns.get(columnName);
  }


  @Override
  public long getExecutionTimeMicro() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public CassandraHost getHostUsed() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ByteBuffer getColumnValue(N columnName) {
    // TODO Auto-generated method stub
    return null;
  }
}
