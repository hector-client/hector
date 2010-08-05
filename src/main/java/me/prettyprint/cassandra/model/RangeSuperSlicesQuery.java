package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A query for the thrift call get_range_slices of supercolumns
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class RangeSuperSlicesQuery<SN,N,V> extends AbstractSliceQuery<SN,V,OrderedSuperRows<SN,N,V>> {

  /** Whether to use start/end as tokens or as keys */
  private boolean useTokens = true;
  private String start, end;
  private int rowCount = 100;
  private final Extractor<N> nameExtractor;

  /*package*/ RangeSuperSlicesQuery(KeyspaceOperator ko, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, sNameExtractor, valueExtractor);
    Assert.notNull(nameExtractor, "nameExtractor cannot be null");
    this.nameExtractor = nameExtractor;
  }

  public RangeSuperSlicesQuery<SN,N,V> setTokens(String start, String end) {
    useTokens = true;
    this.start = start;
    this.end = end;
    return this;
  }

  public RangeSuperSlicesQuery<SN,N,V> setKeys(String start, String end) {
    useTokens = false;
    this.start = start;
    this.end = end;
    return this;
  }

  public RangeSuperSlicesQuery<SN,N,V> setRowCount(int rowCount) {
    this.rowCount = rowCount;
    return this;
  }

  @Override
  public Result<OrderedSuperRows<SN,N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(start, "start can't be null");
    Assert.notNull(end, "end can't be null");

    return new Result<OrderedSuperRows<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedSuperRows<SN,N,V>>() {
          @Override
          public OrderedSuperRows<SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            KeyRange keyRange = new KeyRange(rowCount);
            if (useTokens) {
              keyRange.setStart_token(start);
              keyRange.setEnd_token(end);
            } else {
              keyRange.setStart_key(start);
              keyRange.setEnd_key(end);
            }
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<String, List<SuperColumn>> thriftRet =
                ks.getSuperRangeSlices(columnParent, getPredicate(), keyRange);
            return new OrderedSuperRows<SN,N,V>(thriftRet, columnNameExtractor, nameExtractor,
                valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    String tk = useTokens ? "t" : "k";
    return "RangeSuperSlicesQuery(" + tk + "Start:" + start + "," + tk + "End:" + end + ","
        + super.toStringInternal() + ")";
  }

}
