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
public final class MultigetSubSliceQuery<K, SN, N, V> extends AbstractSliceQuery<K, N, V, Rows<K, N, V>> {

  private Collection<K> keys;
  private final Extractor<SN> sNameExtractor;
  private SN superColumn;

  /*package*/MultigetSubSliceQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, nameExtractor, valueExtractor);
    Assert.notNull(nameExtractor, "sNameExtractor can't be null");
    this.sNameExtractor = sNameExtractor;
  }

  public MultigetSubSliceQuery<K, SN, N, V> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  public MultigetSubSliceQuery<K,SN,N,V> setSuperColumn(SN superColumn) {
    Assert.notNull(superColumn, "supercolumn may not be null");
    this.superColumn = superColumn;
    return this;
  }


  public Result<Rows<K, N, V>> execute() {
    Assert.noneNull(keys, "Keys cannot be null");
    Assert.noneNull(columnFamilyName, "columnFamilyName cannot be null");
    Assert.noneNull(superColumn, "superColumn cannot be null");

    return new Result<Rows<K, N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<Rows<K, N, V>>() {
          @Override
          public Rows<K, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            List<K> keysList = new ArrayList<K>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameExtractor.toBytes(superColumn));
            Map<K, List<Column>> thriftRet = ks.multigetSlice(keysList,
                columnParent, getPredicate(), keyExtractor);
            return new Rows<K, N, V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSubSliceQuery(" + superColumn + "," + keys + "," + super.toStringInternal()
        + ")";
  }
}
