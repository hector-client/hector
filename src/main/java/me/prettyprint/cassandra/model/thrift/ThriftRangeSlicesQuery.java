package me.prettyprint.cassandra.model.thrift;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.HKeyRange;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.OrderedRowsImpl;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

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
public final class ThriftRangeSlicesQuery<N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>>
    implements RangeSlicesQuery<N, V> {

  private final HKeyRange keyRange;

  public ThriftRangeSlicesQuery(Keyspace ko, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    keyRange = new HKeyRange();
  }

  @Override
  public RangeSlicesQuery<N, V> setKeys(String start, String end) {
    keyRange.setKeys(start, end);
    return this;
  }

  @Override
  public RangeSlicesQuery<N, V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public QueryResult<OrderedRows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new QueryResultImpl<OrderedRows<N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedRows<N,V>>() {
          @Override
          public OrderedRows<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<String, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedRowsImpl<N,V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesQuery<N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (RangeSlicesQuery<N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesQuery<N, V> setColumnFamily(String cf) {
    return (RangeSlicesQuery<N, V>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public RangeSlicesQuery<N, V> setColumnNames(N... columnNames) {
    return (RangeSlicesQuery<N, V>) super.setColumnNames(columnNames);
  }
}
