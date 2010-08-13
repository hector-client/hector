package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;


/**
 * A query for the thrift call get_slice on subcolumns of a supercolumns.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class SubSliceQuery<K,SN,N,V> extends AbstractSliceQuery<K,N,V,ColumnSlice<N,V>> {

  private K key;
  private SN superColumn;
  private final Extractor<SN> sNameExtractor;

  /*package*/ SubSliceQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, nameExtractor, valueExtractor);
    Assert.notNull(sNameExtractor, "Supername extractor cannot be null");
    this.sNameExtractor = sNameExtractor;
  }

  public SubSliceQuery<K,SN,N,V> setKey(K key) {
    this.key = key;
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  public SubSliceQuery<K,SN,N,V> setSuperColumn(SN superColumn) {
    this.superColumn = superColumn;
    return this;
  }

  public Result<ColumnSlice<N, V>> execute() {
    Assert.notNull(key, "Key cannot be null");
    Assert.notNull(superColumn, "Supercolumn cannot be null");
    return new Result<ColumnSlice<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameExtractor.toBytes(superColumn));
            List<Column> thriftRet = ks.getSlice(keyExtractor.toBytes(key), columnParent, getPredicate());
            return new ColumnSlice<N, V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SubSliceQuery(" + key + "," + superColumn + "," + toStringInternal() + ")";
  }
}
