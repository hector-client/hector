package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

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

  private final Extractor<SN> sNameExtractor;
  private final HKeyRange keyRange;
  private SN superColumn;


  /*package*/ RangeSubSlicesQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, nameExtractor, valueExtractor);
    Assert.notNull(sNameExtractor, "sNameExtractor cannot be null");
    this.sNameExtractor = sNameExtractor;
    keyRange = new HKeyRange();
  }

  public RangeSubSlicesQuery<K,SN,N,V> setTokens(String start, String end) {
    keyRange.setTokens(start, end);
    return this;
  }

  public RangeSubSlicesQuery<K,SN,N,V> setKeys(K start, K end) {
    keyRange.setKeys(start, end, keyExtractor);
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

  public Result<OrderedRows<K,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new Result<OrderedRows<K,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameExtractor.toBytes(superColumn));
            LinkedHashMap<K, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift(), keyExtractor);
            return new OrderedRows<K,N,V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
