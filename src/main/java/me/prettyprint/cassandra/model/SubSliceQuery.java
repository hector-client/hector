package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;

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
public final class SubSliceQuery<SN,N,V> extends AbstractSliceQuery<N,V,ColumnSlice<N,V>> {

  private String key;
  private SN superColumn;
  private final Serializer<SN> sNameSerializer;

  public SubSliceQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "Supername serializer cannot be null");
    this.sNameSerializer = sNameSerializer;
  }

  public SubSliceQuery<SN,N,V> setKey(String key) {
    this.key = key;
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  public SubSliceQuery<SN,N,V> setSuperColumn(SN superColumn) {
    this.superColumn = superColumn;
    return this;
  }

  @Override
  public Result<ColumnSlice<N, V>> execute() {
    Assert.notNull(key, "Key cannot be null");
    Assert.notNull(superColumn, "Supercolumn cannot be null");
    return new Result<ColumnSlice<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            List<Column> thriftRet = ks.getSlice(key, columnParent, getPredicate());
            return new ColumnSlice<N, V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SubSliceQuery(" + key + "," + superColumn + "," + toStringInternal() + ")";
  }
}
