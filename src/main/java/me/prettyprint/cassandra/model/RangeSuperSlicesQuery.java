package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

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
public final class RangeSuperSlicesQuery<K,SN,N,V> extends AbstractSliceQuery<K,SN,V,OrderedSuperRows<K,SN,N,V>> {

  private final Serializer<N> nameSerializer;
  private final HKeyRange<K> keyRange;

  /*package*/ RangeSuperSlicesQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer cannot be null");
    this.nameSerializer = nameSerializer;
    keyRange = new HKeyRange<K>(keySerializer);
  }


  public RangeSuperSlicesQuery<K,SN,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  public RangeSuperSlicesQuery<K,SN,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public Result<OrderedSuperRows<K,SN,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedSuperRows<K,SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedSuperRows<K,SN,N,V>>() {
          @Override
          public OrderedSuperRows<K,SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<SuperColumn>> thriftRet = keySerializer.fromBytesMap(
                ks.getSuperRangeSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedSuperRows<K,SN,N,V>((LinkedHashMap<K, List<SuperColumn>>) thriftRet, keySerializer, columnNameSerializer, nameSerializer,
                valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
