package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

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

  private final Extractor<N> nameExtractor;
  private final HKeyRange keyRange;

  /*package*/ RangeSuperSlicesQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, sNameExtractor, valueExtractor);
    Assert.notNull(nameExtractor, "nameExtractor cannot be null");
    this.nameExtractor = nameExtractor;
    keyRange = new HKeyRange();
  }

  public RangeSuperSlicesQuery<K,SN,N,V> setTokens(String start, String end) {
    keyRange.setTokens(start, end);
    return this;
  }

  public RangeSuperSlicesQuery<K,SN,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end, keyExtractor);
    return this;
  }

  public RangeSuperSlicesQuery<K,SN,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  public Result<OrderedSuperRows<K,SN,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");

    return new Result<OrderedSuperRows<K,SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedSuperRows<K,SN,N,V>>() {
          @Override
          public OrderedSuperRows<K,SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<K, List<SuperColumn>> thriftRet =
                ks.getSuperRangeSlices(columnParent, getPredicate(), keyRange.toThrift(), keyExtractor);
            return new OrderedSuperRows<K,SN,N,V>(thriftRet, keyExtractor, columnNameExtractor, nameExtractor,
                valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
