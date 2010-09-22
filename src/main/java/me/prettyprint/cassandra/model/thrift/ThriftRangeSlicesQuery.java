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
import me.prettyprint.hector.api.query.RangeSlicesQuery;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query for the thrift call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class ThriftRangeSlicesQuery<K, N,V> extends AbstractSliceQuery<K, N,V,OrderedRows<K, N,V>>
    implements RangeSlicesQuery<K, N, V> {

  private final HKeyRange<K> keyRange;

  public ThriftRangeSlicesQuery(Keyspace keyspace,
      Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, keySerializer, nameSerializer, valueSerializer);
    keyRange = new HKeyRange<K>(keySerializer);
  }

  @Override
  public RangeSlicesQuery<K, N, V> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSlicesQuery<K, N, V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public QueryResult<OrderedRows<K, N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new QueryResultImpl<OrderedRows<K, N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K, N,V>>() {
          @Override
          public OrderedRows<K, N,V > doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedRowsImpl<K,N,V>((LinkedHashMap<K, List<Column>>) thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesQuery<K, N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (RangeSlicesQuery<K, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesQuery<K, N, V> setColumnFamily(String cf) {
    return (RangeSlicesQuery<K, N, V>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesQuery<K, N, V> setColumnNames(N... columnNames) {
    return (RangeSlicesQuery<K, N, V>) super.setColumnNames(columnNames);
  }
}
