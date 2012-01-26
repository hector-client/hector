package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.OrderedCounterRowsImpl;
import me.prettyprint.cassandra.model.OrderedRowsImpl;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedCounterRows;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSubSlicesCounterQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterColumn;

/**
 * A query for the thrift call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public final class ThriftRangeSubSlicesCounterQuery<K,SN,N> extends AbstractSliceQuery<K,N,Long,OrderedCounterRows<K,N>>
  implements RangeSubSlicesCounterQuery<K, SN, N>{

  private final Serializer<SN> sNameSerializer;
  private final HKeyRange<K> keyRange;
  private SN superColumn;

  public ThriftRangeSubSlicesCounterQuery(Keyspace k, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer) {
    super(k, keySerializer, nameSerializer, LongSerializer.get());
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
    keyRange = new HKeyRange<K>(keySerializer);
  }


  @Override
  public RangeSubSlicesCounterQuery<K,SN,N> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSubSlicesCounterQuery<K,SN,N> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public  RangeSubSlicesCounterQuery<K,SN,N> setSuperColumn(SN sc) {
    Assert.notNull(sc, "sc can't be null");
    superColumn = sc;
    return this;
  }

  @Override
  public QueryResult<OrderedCounterRows<K,N>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new QueryResultImpl<OrderedCounterRows<K,N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedCounterRows<K,N>>() {
          @Override
          public OrderedCounterRows<K,N> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toByteBuffer(superColumn));
            Map<K, List<CounterColumn>> thriftRet = keySerializer.fromBytesMap(
                ks.getRangeCounterSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedCounterRowsImpl<K,N>((LinkedHashMap<K, List<CounterColumn>>) thriftRet, columnNameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSubSlicesCounterQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesCounterQuery<K, SN, N> setColumnNames(N... columnNames) {
    return (RangeSubSlicesCounterQuery<K, SN, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesCounterQuery<K, SN, N> setRange(N start, N finish, boolean reversed, int count) {
    return (RangeSubSlicesCounterQuery<K, SN, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesCounterQuery<K, SN, N> setColumnFamily(String cf) {
    return (RangeSubSlicesCounterQuery<K, SN, N>) super.setColumnFamily(cf);
  }

}
