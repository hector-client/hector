package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.OrderedSuperRowsImpl;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedSuperRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A query for the thrift call get_range_slices of supercolumns
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class ThriftRangeSuperSlicesQuery<K, SN, N, V> extends
    AbstractSliceQuery<K, SN, V, OrderedSuperRows<K, SN, N, V>> implements
    RangeSuperSlicesQuery<K, SN, N, V> {

  private final Serializer<N> nameSerializer;
  private final HKeyRange<K> keyRange;

  public ThriftRangeSuperSlicesQuery(Keyspace keyspace,
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, keySerializer, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer cannot be null");
    this.nameSerializer = nameSerializer;
    keyRange = new HKeyRange<K>(keySerializer);
  }

  @Override
  public RangeSuperSlicesQuery<K, SN, N, V> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSuperSlicesQuery<K, SN, N, V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public QueryResult<OrderedSuperRows<K, SN,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new QueryResultImpl<OrderedSuperRows<K, SN,N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedSuperRows<K, SN,N,V>>() {
          @Override
          public OrderedSuperRows<K, SN,N,V> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<SuperColumn>> thriftRet = keySerializer.fromBytesMap(
                ks.getSuperRangeSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedSuperRowsImpl<K, SN, N, V>(
                (LinkedHashMap<K, List<SuperColumn>>) thriftRet, keySerializer,
                columnNameSerializer, nameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesQuery<K, SN, N, V> setColumnNames(SN... columnNames) {
    return (RangeSuperSlicesQuery<K, SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesQuery<K, SN, N, V> setRange(SN start, SN finish, boolean reversed, int count) {
    return (RangeSuperSlicesQuery<K, SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesQuery<K, SN, N, V> setColumnFamily(String cf) {
    return (RangeSuperSlicesQuery<K, SN, N, V>) super.setColumnFamily(cf);
  }
}
