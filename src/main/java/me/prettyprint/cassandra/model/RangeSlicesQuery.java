package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;

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
public final class RangeSlicesQuery<K,N,V> extends AbstractSliceQuery<K,N,V,OrderedRows<K,N,V>> {

  private final HKeyRange<K> keyRange;

  public RangeSlicesQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
    keyRange = new HKeyRange<K>(keySerializer);
  }


  public RangeSlicesQuery<K,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  public RangeSlicesQuery<K,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public Result<OrderedRows<K,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedRows<K,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedRows<K,N,V>((LinkedHashMap<K, List<Column>>) thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }
}
