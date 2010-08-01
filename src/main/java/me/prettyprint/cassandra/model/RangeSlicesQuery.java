package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.KeyRange;

/**
 * A query for the thrift call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class RangeSlicesQuery<N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>> {

  /** Whther to use start/end as tokens or as keys */
  private boolean useTokens = true;
  private String start, end;
  private int rowCount = 100;

  RangeSlicesQuery(KeyspaceOperator ko, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  public RangeSlicesQuery<N,V> setTokens(String start, String end) {
    useTokens = true;
    this.start = start;
    this.end = end;
    return this;
  }

  public RangeSlicesQuery<N,V> setKeys(String start, String end) {
    useTokens = false;
    this.start = start;
    this.end = end;
    return this;
  }

  public RangeSlicesQuery<N,V> setRowCount(int rowCount) {
    this.rowCount = rowCount;
    return this;
  }

  @Override
  public Result<OrderedRows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(start, "start can't be null");
    Assert.notNull(end, "end can't be null");

    return new Result<OrderedRows<N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<N,V>>() {
          @Override
          public OrderedRows<N,V> doInKeyspace(Keyspace ks) throws HectorException {
            KeyRange keyRange = new KeyRange(rowCount);
            if (useTokens) {
              keyRange.setStart_token(start);
              keyRange.setEnd_token(end);
            } else {
              keyRange.setStart_key(start);
              keyRange.setEnd_key(end);
            }
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            LinkedHashMap<String, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange);
            return new OrderedRows<N,V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    String tk = useTokens ? "t" : "k";
    return "RangeSlicesQuery(" + tk + "Start:" + start + "," + tk + "End:" + end + ","
        + super.toStringInternal() + ")";
  }

}
