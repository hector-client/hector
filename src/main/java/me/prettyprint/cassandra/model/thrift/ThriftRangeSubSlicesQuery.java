package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.OrderedRowsImpl;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query for the thrift call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public final class ThriftRangeSubSlicesQuery<K,SN,N,V> extends AbstractSliceQuery<K,N,V,OrderedRows<K,N,V>>
  implements RangeSubSlicesQuery<K, SN, N, V>{

  private final Serializer<SN> sNameSerializer;
  private final HKeyRange<K> keyRange;
  private SN superColumn;

  public ThriftRangeSubSlicesQuery(Keyspace k, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
    keyRange = new HKeyRange<K>(keySerializer);
  }


  @Override
  public RangeSubSlicesQuery<K,SN,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSubSlicesQuery<K,SN,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public  RangeSubSlicesQuery<K,SN,N,V> setSuperColumn(SN sc) {
    Assert.notNull(sc, "sc can't be null");
    superColumn = sc;
    return this;
  }

  @Override
  public QueryResult<OrderedRows<K,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new QueryResultImpl<OrderedRows<K,N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedRowsImpl<K,N,V>((LinkedHashMap<K, List<Column>>) thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesQuery<K, SN, N, V> setColumnNames(N... columnNames) {
    return (RangeSubSlicesQuery<K, SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesQuery<K, SN, N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (RangeSubSlicesQuery<K, SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesQuery<K, SN, N, V> setColumnFamily(String cf) {
    return (RangeSubSlicesQuery<K, SN, N, V>) super.setColumnFamily(cf);
  }

}
