package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.OrderedRowsImpl;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query for the thrift call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public final class ThriftRangeSubSlicesQuery<SN,N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>> implements RangeSubSlicesQuery<SN, N, V> {

  private final Serializer<SN> sNameSerializer;
  private final HKeyRange keyRange;
  private SN superColumn;


  public ThriftRangeSubSlicesQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
    keyRange = new HKeyRange();
  }

  @Override
  public RangeSubSlicesQuery<SN, N, V> setKeys(String start, String end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSubSlicesQuery<SN, N, V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public RangeSubSlicesQuery<SN, N, V> setSuperColumn(SN sc) {
    Assert.notNull(sc, "sc can't be null");
    superColumn = sc;
    return this;
  }

  @Override
  public Result<OrderedRows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new Result<OrderedRows<N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<N,V>>() {
          @Override
          public OrderedRows<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            LinkedHashMap<String, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedRowsImpl<N,V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesQuery<SN, N, V> setColumnNames(N... columnNames) {
    return (RangeSubSlicesQuery<SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesQuery<SN, N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (RangeSubSlicesQuery<SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSubSlicesQuery<SN, N, V> setColumnFamily(String cf) {
    return (RangeSubSlicesQuery<SN, N, V>) super.setColumnFamily(cf);
  }

}
