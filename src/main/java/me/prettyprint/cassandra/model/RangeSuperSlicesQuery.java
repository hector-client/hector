package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;

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
public final class RangeSuperSlicesQuery<SN,N,V> extends AbstractSliceQuery<SN,V,OrderedSuperRows<SN,N,V>> {

  private final Serializer<N> nameSerializer;
  private final HKeyRange keyRange;

  public RangeSuperSlicesQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer cannot be null");
    this.nameSerializer = nameSerializer;
    keyRange = new HKeyRange();
  }

  public RangeSuperSlicesQuery<SN,N,V> setKeys(String start, String end) {
    keyRange.setKeys(start, end);
    return this;
  }

  public RangeSuperSlicesQuery<SN,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public Result<OrderedSuperRows<SN,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedSuperRows<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedSuperRows<SN,N,V>>() {
          @Override
          public OrderedSuperRows<SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<String, List<SuperColumn>> thriftRet =
                ks.getSuperRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedSuperRows<SN,N,V>(thriftRet, columnNameSerializer, nameSerializer,
                valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
