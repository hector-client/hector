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
public final class RangeSlicesQuery<K,N,V> extends AbstractSliceQuery<K,N,V,OrderedRows<K,N,V>> {

  private final HKeyRange keyRange;

  /*package*/ RangeSlicesQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, nameExtractor, valueExtractor);
    keyRange = new HKeyRange();
  }

  public RangeSlicesQuery<K,N,V> setTokens(String start, String end) {
    keyRange.setTokens(start, end);
    return this;
  }

  public RangeSlicesQuery<K,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end, keyExtractor);
    return this;
  }

  public RangeSlicesQuery<K,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  public Result<OrderedRows<K,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedRows<K,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<K, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift(), keyExtractor);
            return new OrderedRows<K,N,V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }
}
