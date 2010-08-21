package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

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
public final class RangeSlicesQuery<N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>> {

  private final HKeyRange keyRange;

  /*package*/ RangeSlicesQuery(KeyspaceOperator ko, Serializer<N> nameExtractor, Serializer<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
    keyRange = new HKeyRange();
  }

  public RangeSlicesQuery<N,V> setKeys(String start, String end) {
    keyRange.setKeys(start, end);
    return this;
  }

  public RangeSlicesQuery<N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  @Override
  public Result<OrderedRows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedRows<N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<N,V>>() {
          @Override
          public OrderedRows<N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<String, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedRows<N,V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }
}
