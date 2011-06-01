package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.OrderedCounterSuperRowsImpl;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedCounterSuperRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSuperSlicesCounterQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterSuperColumn;

/**
 * A query for the thrift call get_range_slices of supercolumns
 *
 * 
 *
 * @param <N>
 */
public final class ThriftRangeSuperSlicesCounterQuery<K, SN, N> extends
    AbstractSliceQuery<K, SN, Long, OrderedCounterSuperRows<K, SN, N>> implements
        RangeSuperSlicesCounterQuery<K, SN, N> {

  private final Serializer<N> nameSerializer;
  private final HKeyRange<K> keyRange;

  public ThriftRangeSuperSlicesCounterQuery(Keyspace keyspace,
                                            Serializer<K> keySerializer,
                                            Serializer<SN> sNameSerializer,
                                            Serializer<N> nameSerializer) {
    super(keyspace, keySerializer, sNameSerializer, LongSerializer.get());
    Assert.notNull(nameSerializer, "nameSerializer cannot be null");
    this.nameSerializer = nameSerializer;
    keyRange = new HKeyRange<K>(keySerializer);
  }

  @Override
  public RangeSuperSlicesCounterQuery<K, SN, N> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSuperSlicesCounterQuery<K, SN, N> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public QueryResult<OrderedCounterSuperRows<K, SN,N>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new QueryResultImpl<OrderedCounterSuperRows<K, SN,N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedCounterSuperRows<K, SN,N>>() {
          @Override
          public OrderedCounterSuperRows<K, SN,N> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<CounterSuperColumn>> thriftRet = keySerializer.fromBytesMap(
                ks.getSuperRangeCounterSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedCounterSuperRowsImpl<K, SN, N>(
                (LinkedHashMap<K, List<CounterSuperColumn>>) thriftRet, keySerializer,
                columnNameSerializer, nameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesCounterQuery<K, SN, N> setColumnNames(SN... columnNames) {
    return (RangeSuperSlicesCounterQuery<K, SN, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesCounterQuery<K, SN, N> setRange(SN start, SN finish, boolean reversed, int count) {
    return (RangeSuperSlicesCounterQuery<K, SN, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesCounterQuery<K, SN, N> setColumnFamily(String cf) {
    return (RangeSuperSlicesCounterQuery<K, SN, N>) super.setColumnFamily(cf);
  }
}
