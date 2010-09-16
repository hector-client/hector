package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query for the thrift call get_range_slices for subcolumns of supercolumns
 *
 * @author Ran Tavory
 *
 */
public final class RangeSubSlicesQuery<SN,N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>> {

  private final Serializer<SN> sNameSerializer;
  private final HKeyRange keyRange;
  private SN superColumn;


  public RangeSubSlicesQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
    keyRange = new HKeyRange();
  }

  public RangeSubSlicesQuery<SN,N,V> setKeys(String start, String end) {
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

  @Override
  public Result<OrderedRows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(superColumn, "superColumn cannot be null");

    return new Result<OrderedRows<N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<N,V>>() {
          @Override
          public OrderedRows<N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            LinkedHashMap<String, List<Column>> thriftRet =
                ks.getRangeSlices(columnParent, getPredicate(), keyRange.toThrift());
            return new OrderedRows<N,V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "RangeSuperSlicesQuery(" + keyRange + super.toStringInternal() + ")";
  }

}
