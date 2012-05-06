package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.*;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;

/**
 * Wraps the results with as an Iterator. The underlying Iterator has already been advanced
 * to the first row upon construction.
 * 
 * @author zznate
 */
public class ColumnFamilyResultWrapper<K,N> extends AbstractResultWrapper<K,N> {
  
  private Map<N,HColumn<N,ByteBuffer>> columns;
  private Iterator<Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>>> rows;
  private Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry;
  private boolean hasEntries;
  
  public ColumnFamilyResultWrapper(Serializer<K> keySerializer,
      Serializer<N> columnNameSerializer,
      ExecutionResult<Map<ByteBuffer,List<ColumnOrSuperColumn>>> executionResult) {
    super(keySerializer, columnNameSerializer, executionResult);    
    this.rows = executionResult.get().entrySet().iterator();    
		if(hasNext()) {
			next();
		}

    hasEntries = getColumnNames() != null && getColumnNames().size() > 0;
  }
   
  /**
   * All the column names we know about in the current iterator position
   * @return
   */
  public Collection<N> getColumnNames() {
    return columns == null ? null : columns.keySet();
  }
  
  public ByteBuffer getColumnValue( N columnName) {
    HColumn<N,ByteBuffer> col = getColumn( columnName );
    return col != null ? col.getValue().duplicate() : null;
  }

  public HColumn<N,ByteBuffer> getColumn( N columnName ) {
    return columns.get( columnName );
  }
  

  private void applyToRow(List<ColumnOrSuperColumn> cosclist) {
    hasEntries = cosclist.size() > 0;
    for (Iterator<ColumnOrSuperColumn> iterator = cosclist.iterator(); iterator.hasNext();) {
      ColumnOrSuperColumn cosc = iterator.next();            
      if ( cosc.isSetSuper_column() ) {
        applySuper(cosc);
      } else {
        applyStandard(cosc.getColumn());        
      }
       
      iterator.remove();
    }
  }
  
  private void applySuper(ColumnOrSuperColumn cosc) {
    Iterator<Column> tcolumns = cosc.getSuper_column().getColumnsIterator();
    while ( tcolumns.hasNext() ) {
      applyStandard(tcolumns.next());
    }    
  }

  
  private void applyStandard(Column cosc) {
    N colName = columnNameSerializer.fromByteBuffer(cosc.name.duplicate());
    HColumn<N, ByteBuffer> column = columns.get(colName);
    
    if ( column == null ) {
      column = new HColumnImpl<N, ByteBuffer>(cosc, columnNameSerializer, ByteBufferSerializer.get());
    } else {
      ((HColumnImpl<N, ByteBuffer>)column).apply(cosc);
    }
    columns.put(colName, column);  
  }

  @Override
  public K getKey() {    
    return keySerializer.fromByteBuffer(entry.getKey().duplicate());
  }

  @Override
  public ColumnFamilyResult<K, N> next() {
    if ( !hasNext() ) {
      throw new NoSuchElementException("No more rows left on this HColumnFamily");
    }
    entry = rows.next();   
    columns = new LinkedHashMap<N,HColumn<N,ByteBuffer>>();
    applyToRow(entry.getValue());
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

  @Override
  public boolean hasResults() {    
    return hasEntries;
  }
  
  

}
