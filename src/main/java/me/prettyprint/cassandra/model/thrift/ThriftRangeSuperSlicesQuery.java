package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.OrderedSuperRowsImpl;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.beans.OrderedSuperRows;
import me.prettyprint.hector.api.exceptions.HectorException;
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
public final class ThriftRangeSuperSlicesQuery<SN, N, V> extends
    AbstractSliceQuery<SN, V, OrderedSuperRows<SN, N, V>> implements
    RangeSuperSlicesQuery<SN, N, V> {

  private final Serializer<N> nameSerializer;
  private final HKeyRange keyRange;

  public ThriftRangeSuperSlicesQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer cannot be null");
    this.nameSerializer = nameSerializer;
    keyRange = new HKeyRange();
  }

  @Override
  public RangeSuperSlicesQuery<SN, N, V> setKeys(String start, String end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSuperSlicesQuery<SN, N, V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public Result<OrderedSuperRows<SN,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedSuperRows<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedSuperRows<SN,N,V>>() {
          @Override
          public OrderedSuperRows<SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<String, List<SuperColumn>> thriftRet =
                ks.getSuperRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedSuperRowsImpl<SN,N,V>(thriftRet, columnNameSerializer, nameSerializer,
                valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesQuery<SN, N, V> setColumnNames(SN... columnNames) {
    return (RangeSuperSlicesQuery<SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesQuery<SN, N, V> setRange(SN start, SN finish, boolean reversed, int count) {
    return (RangeSuperSlicesQuery<SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSuperSlicesQuery<SN, N, V> setColumnFamily(String cf) {
    return (RangeSuperSlicesQuery<SN, N, V>) super.setColumnFamily(cf);
  }

}
