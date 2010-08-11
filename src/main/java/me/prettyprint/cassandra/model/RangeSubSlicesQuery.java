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
public final class RangeSubSlicesQuery<SN,N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>> {

  private final Extractor<SN> sNameExtractor;
  private final HKeyRange keyRange;
  private SN superColumn;


  /*package*/ RangeSubSlicesQuery(KeyspaceOperator ko, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
    Assert.notNull(sNameExtractor, "sNameExtractor cannot be null");
    this.sNameExtractor = sNameExtractor;
    keyRange = new HKeyRange();
  }

  public RangeSubSlicesQuery<SN,N,V> setTokens(String start, String end) {
    keyRange.setTokens(start, end);
    return this;
  }

  public RangeSubSlicesQuery<SN,N,V> setKeys(byte[] start, byte[] end) {
    keyRange.setKeys(start, end);
    return this;
  }

  public RangeSubSlicesQuery<SN,N,V> setRowCount(int rowCount) {
    keyRange.setRowCount(rowCount);
    return this;
  }

  public  RangeSubSlicesQuery<SN,N,V> setSuperColumn(SN sc) {
    Assert.notNull(sc, "sc can't be null");
    superColumn = sc;
    return this;
  }

  public Result<OrderedRows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new Result<OrderedRows<N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<N,V>>() {
          @Override
          public OrderedRows<N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameExtractor.toBytes(superColumn));
            LinkedHashMap<byte[], List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedRows<N,V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
