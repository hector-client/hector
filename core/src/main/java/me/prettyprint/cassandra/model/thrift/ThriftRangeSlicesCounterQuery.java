package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.OrderedCounterRowsImpl;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedCounterRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesCounterQuery;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterColumn;

/**
 * A query for the thrift call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 */
public final class ThriftRangeSlicesCounterQuery<K, N> extends AbstractSliceQuery<K, N, Long, OrderedCounterRows<K, N>>
    implements RangeSlicesCounterQuery<K, N> {

  private final HKeyRange<K> keyRange;

  public ThriftRangeSlicesCounterQuery(Keyspace keyspace,
                                       Serializer<K> keySerializer,
                                       Serializer<N> nameSerializer) {
    super(keyspace, keySerializer, nameSerializer, LongSerializer.get());
    keyRange = new HKeyRange<K>(keySerializer);
  }

  @Override
  public RangeSlicesCounterQuery<K, N> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSlicesCounterQuery<K, N> setTokens(K startKey, String startToken, String endToken) {
    keyRange.setTokens(startKey, startToken, endToken);
    return this;
  }
  
  @Override
  public RangeSlicesCounterQuery<K, N> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public QueryResult<OrderedCounterRows<K, N>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new QueryResultImpl<OrderedCounterRows<K, N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedCounterRows<K, N>>() {
          @Override
          public OrderedCounterRows<K, N> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<CounterColumn>> thriftRet = keySerializer.fromBytesMap(
                ks.getRangeCounterSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedCounterRowsImpl<K,N>((LinkedHashMap<K, List<CounterColumn>>) thriftRet, columnNameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesCounterQuery<K, N> setRange(N start, N finish, boolean reversed, int count) {
    return (RangeSlicesCounterQuery<K, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesCounterQuery<K, N> setColumnFamily(String cf) {
    return (RangeSlicesCounterQuery<K, N>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesCounterQuery<K, N> setColumnNames(N... columnNames) {
    return (RangeSlicesCounterQuery<K, N>) super.setColumnNames(columnNames);
  }

  @Override
  public ThriftRangeSlicesCounterQuery<K, N> setReturnKeysOnly() {
    super.setReturnKeysOnly();
    return this;
  }

  
  
  

}
