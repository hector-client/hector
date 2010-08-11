package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query wrapper for the thrift call multiget_slice for subcolumns  of supercolumns
 */
public final class MultigetSubSliceQuery<SN, N, V> extends AbstractSliceQuery<N, V, Rows<N, V>> {

  private Collection<byte[]> keys;
  private final Extractor<SN> sNameExtractor;
  private SN superColumn;

  /*package*/MultigetSubSliceQuery(KeyspaceOperator ko, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
    Assert.notNull(nameExtractor, "sNameExtractor can't be null");
    this.sNameExtractor = sNameExtractor;
  }

  public MultigetSubSliceQuery<SN, N, V> setKeys(byte[]... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  public MultigetSubSliceQuery<SN,N,V> setSuperColumn(SN superColumn) {
    Assert.notNull(superColumn, "supercolumn may not be null");
    this.superColumn = superColumn;
    return this;
  }


  public Result<Rows<N, V>> execute() {
    Assert.noneNull(keys, "Keys cannot be null");
    Assert.noneNull(columnFamilyName, "columnFamilyName cannot be null");
    Assert.noneNull(superColumn, "superColumn cannot be null");

    return new Result<Rows<N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<Rows<N, V>>() {
          @Override
          public Rows<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            List<byte[]> keysList = new ArrayList<byte[]>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameExtractor.toBytes(superColumn));
            Map<byte[], List<Column>> thriftRet = ks.multigetSlice(keysList,
                columnParent, getPredicate());
            return new Rows<N, V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSubSliceQuery(" + superColumn + "," + keys + "," + super.toStringInternal()
        + ")";
  }
}
