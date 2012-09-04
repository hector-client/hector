package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import me.prettyprint.cassandra.model.ExecutionResult;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.HSuperColumnImpl;
import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.DoubleSerializer;
import me.prettyprint.cassandra.serializers.FloatSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides access to the current row of data during super column queries. 
 * 
 * @author zznate
 * @param <N> the super column's sub column name type
 */
public class SuperCfResultWrapper<K,SN,N> extends AbstractResultWrapper<K,N> implements SuperCfResult<K,SN,N> {
  private static final Logger log = LoggerFactory.getLogger(SuperCfResultWrapper.class);
  
  private Map<SN,Map<N,HColumn<N,ByteBuffer>>> columns = new LinkedHashMap<SN,Map<N,HColumn<N,ByteBuffer>>>();
  private Iterator<Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>>> rows;
  private Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry;
  private List<SN> superColumns;
  private Map<N,HColumn<N,ByteBuffer>> subColumns = new LinkedHashMap<N,HColumn<N,ByteBuffer>>();
  private SN currentSuperColumn;
  private boolean hasEntries;

  private Serializer<SN> sNameSerializer;
  
  public SuperCfResultWrapper(Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> subSerializer,
      ExecutionResult<Map<ByteBuffer, List<ColumnOrSuperColumn>>> executionResult) {
    super(keySerializer, subSerializer, executionResult);
    this.sNameSerializer = sNameSerializer;
    this.rows = executionResult.get().entrySet().iterator();    
    next();
    hasEntries = getSuperColumns() != null && getSuperColumns().size() > 0;
  }    

  @Override
  public SuperCfResult<K, SN, N> next() {
    if ( !hasNext() ) {
      throw new NoSuchElementException("No more rows left on this HColumnFamily");
    }
    entry = rows.next();    
    log.debug("found entry {} with value {}", getKey(), entry.getValue());
    applyToRow(entry.getValue());
    return this;
  }
  
  private void applyToRow(List<ColumnOrSuperColumn> cosclist) {
    superColumns = new ArrayList<SN>(cosclist.size());
    for (Iterator<ColumnOrSuperColumn> iterator = cosclist.iterator(); iterator.hasNext();) {
      ColumnOrSuperColumn cosc = iterator.next();
      SN sColName = sNameSerializer.fromByteBuffer(cosc.super_column.name.duplicate());
      log.debug("cosc {}", cosc.super_column);
       
      superColumns.add(sColName);
      Iterator<Column> tcolumns = cosc.getSuper_column().getColumnsIterator();
      Map<N,HColumn<N,ByteBuffer>> subColMap = new LinkedHashMap<N, HColumn<N,ByteBuffer>>();
      while ( tcolumns.hasNext() ) {
        Column col = tcolumns.next();
        subColMap.put(columnNameSerializer.fromByteBuffer(col.name.duplicate()), new HColumnImpl<N, ByteBuffer>(col, columnNameSerializer, ByteBufferSerializer.get()));
      }
      columns.put(sColName, subColMap);
    }
  }
  
  
  public List<SN> getSuperColumns() {
    return superColumns;
  }
  
  
  @Override
  public ByteBuffer getColumnValue(N columnName) {
    HColumn<N,ByteBuffer> col = getColumn( columnName );
    return col != null ? col.getValue() : null; 
  }


  @Override
  public K getKey() {
    return keySerializer.fromByteBuffer(entry.getKey().duplicate());
  }


  @Override
  public HColumn<N, ByteBuffer> getColumn(N columnName) {
    return subColumns == null ? null : subColumns.get( columnName );
  }


  @Override
  public Collection<N> getColumnNames() {
    return subColumns != null ? subColumns.keySet() : new ArrayList<N>();
  }


  @Override
  public boolean hasNext() {
    return rows.hasNext();
  }

  @Override
  public void remove() {
    rows.remove();    
  }

  private <V> V extractType(SN sColumnName, N columnName, Serializer<V> valueSerializer) {
    Map<N, HColumn<N, ByteBuffer>> map = columns.get(sColumnName);
    if ( map != null && map.get(columnName) != null )
      return valueSerializer.fromByteBuffer(map.get(columnName).getValue());
    return null;
  }
  
  @Override
  public Boolean getBoolean(SN sColumnName, N columnName) {    
    return extractType(sColumnName, columnName, BooleanSerializer.get());
  }


  @Override
  public byte[] getByteArray(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, BytesArraySerializer.get());
  }


  @Override
  public Date getDate(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, DateSerializer.get());
  }


  @Override
  public Integer getInteger(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, IntegerSerializer.get());
  }


  @Override
  public Long getLong(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, LongSerializer.get());
  }

  @Override
  public Float getFloat(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, FloatSerializer.get());
  }
  
  @Override
  public Double getDouble(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, DoubleSerializer.get());
  }


  @Override
  public String getString(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, StringSerializer.get());
  }



  @Override
  public UUID getUUID(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, UUIDSerializer.get());
  }

  @Override
  public ByteBuffer getByteBuffer(SN sColumnName, N columnName) {
    return extractType(sColumnName, columnName, ByteBufferSerializer.get());
  }

  
  @Override
  public SN getActiveSuperColumn() {
    return currentSuperColumn;
  }
    

  @Override
  public HSuperColumn<SN, N, ByteBuffer> getSuperColumn(SN sColumnName) {
    Map<N, HColumn<N, ByteBuffer>> subCols = columns.get(sColumnName);
    HSuperColumnImpl<SN, N, ByteBuffer> scol = new HSuperColumnImpl<SN, N, ByteBuffer>(sColumnName, new ArrayList<HColumn<N,ByteBuffer>>(subCols.values()), 
        HFactory.createClock(), sNameSerializer, columnNameSerializer, ByteBufferSerializer.get());
    return scol;
  }

  @Override
  public void applySuperColumn(SN sColumnName) {
    this.currentSuperColumn = sColumnName;
    this.subColumns = columns.get(currentSuperColumn);

  }

  @Override
  public boolean hasResults() {
    return hasEntries;
  }
  
  

}
