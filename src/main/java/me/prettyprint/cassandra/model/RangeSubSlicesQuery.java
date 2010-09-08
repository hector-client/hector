package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query for the thrift call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public final class RangeSubSlicesQuery<K,SN,N,V> extends AbstractSliceQuery<K,N,V,OrderedRows<K,N,V>> {

  private final Serializer<SN> sNameSerializer;
  private final HKeyRange<K> keyRange;
  private SN superColumn;

  public RangeSubSlicesQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
    keyRange = new HKeyRange<K>(keySerializer);
  }


  public RangeSubSlicesQuery<K,SN,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end);
    return this;
  }

  public RangeSubSlicesQuery<K,SN,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  public  RangeSubSlicesQuery<K,SN,N,V> setSuperColumn(SN sc) {
    Assert.notNull(sc, "sc can't be null");
    superColumn = sc;
    return this;
  }

  @Override
  public Result<OrderedRows<K,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new Result<OrderedRows<K,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift()));
            return new OrderedRows<K,N,V>((LinkedHashMap<K, List<Column>>) thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
