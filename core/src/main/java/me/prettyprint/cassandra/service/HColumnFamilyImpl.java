package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.cassandra.cli.CliParser.keyspace_return;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.Cassandra.Client;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.model.thrift.ThriftConverter;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.DoubleSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HColumnFamily;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.exceptions.HectorException;

public class HColumnFamilyImpl<K,N> implements HColumnFamily<K, N> {

  private final ExecutingKeyspace keyspace;
  private final String columnFamilyName;
  private Collection<K> _keys;
  private HSlicePredicate<N> activeSlicePredicate;
  private HSlicePredicate<N> lastAppliedPredicate;
  private Serializer<K> keySerializer;
  private Serializer<N> columnNameSerializer;
  private ConsistencyLevelPolicy consistencyLevelPolicy;
  private Map<N, Column> columns;
  private ColumnParent columnParent;
  private ExceptionsTranslator exceptionsTranslator;
  // TODO consider a bounds on this
  private Set<N> columnNames;
  

  public HColumnFamilyImpl(Keyspace keyspace, String columnFamilyName, Serializer<K> keySerializer, Serializer<N> columnNameSerializer) {
    this.keyspace = (ExecutingKeyspace)keyspace;
    this.columnFamilyName = columnFamilyName;
    this._keys = new HashSet<K>();
    this.keySerializer = keySerializer;
    this.columnNameSerializer = columnNameSerializer;
    this.activeSlicePredicate = new HSlicePredicate<N>(columnNameSerializer);
    this.columnParent = new ColumnParent(columnFamilyName);
    exceptionsTranslator = new ExceptionsTranslatorImpl();
  }
  
  @Override
  public HColumnFamily<K, N> addKey(K key) {
    _keys.add(key);
    return this;
  }

  @Override
  public HColumnFamily<K, N> addKeys(Collection<K> keys) {
    _keys.addAll(keys);
    return this;
  }  

  @Override
  public HColumnFamily<K, N> setCount(int count) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public HColumnFamily<K, N> setFinish(N name) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public HColumnFamily<K, N> setReversed(boolean reversed) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public HColumnFamily<K, N> setStart(N name) {
    // TODO Auto-generated method stub
    return this;
  }   

  @Override
  public HColumnFamily<K, N> addColumnName(N columnName) {
    // TODO fixme
    activeSlicePredicate.setColumnNames(columnName);
    return this;
  }

  @Override
  public HColumnFamily<K, N> setColumnNames(Collection<N> columnNames) {
    activeSlicePredicate.setColumnNames(columnNames);
    return null;
  }

  @Override
  public HColumn<N, ?> getColumn(N name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<HColumn<N, ?>> getColumns() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getDate(N name) {
    return extractColumnValue(name, DateSerializer.get());
  }

  @Override
  public double getDouble(N name) {
    return extractColumnValue(name, DoubleSerializer.get());
  }

  @Override
  public int getInt(N name) {    
    return extractColumnValue(name, IntegerSerializer.get());
  }

  @Override
  public long getLong(N name) {
    return extractColumnValue(name, LongSerializer.get());
  }

  @Override
  public String getString(N name) {    
    return extractColumnValue(name, StringSerializer.get());
  }

  @Override
  public UUID getUUID(N name) {
    return extractColumnValue(name, UUIDSerializer.get());
  }

  @Override
  public HColumnFamily<K, N> setConsistencyLevelPolicy(ConsistencyLevelPolicy policy) {

    return this;
  }
  
  private <V> V extractColumnValue(N columnName, Serializer<V> valueSerializer) {
    maybeExecuteSlice(columnName);
    Column column = columns.get(columnName);
    if ( column != null )
      return valueSerializer.fromByteBuffer(column.value);
    return null;
  }
  
  private void maybeExecuteSlice(N columnName) {
    if ( columnNames == null ) {
      columnNames = new HashSet<N>();
    }
    if ( columns == null ) {
      columns = new HashMap<N, Column>();
    }
    if ( columns.get(columnName) == null ) {
      columnNames.add(columnName);
      activeSlicePredicate.setColumnNames(columnNames);
      doExecuteSlice();
    }    
  }
  
  private void doExecuteSlice() {
    keyspace.doExecuteOperation(new Operation<Column>(OperationType.READ) {
      @Override
      public Column execute(Cassandra.Client cassandra) throws HectorException {
        
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(keySerializer.toByteBuffer(_keys.iterator().next()), columnParent,
            activeSlicePredicate.toThrift(),
            ThriftConverter.consistencyLevel(consistencyLevelPolicy.get(operationType)));
          for (ColumnOrSuperColumn cosc : cosclist) {
            columns.put(columnNameSerializer.fromByteBuffer(cosc.getColumn().name), cosc.column);
          }
        } catch (Exception e) {
          throw exceptionsTranslator.translate(e);
        }

        return null;
      }
    });
  }
  
  private void doExecuteMultigetSlice() {
    keyspace.doExecuteOperation(new Operation<Column>(OperationType.READ) {
      @Override
      public Column execute(Cassandra.Client cassandra) throws HectorException {
        return null;
      }
    });
  }

}
