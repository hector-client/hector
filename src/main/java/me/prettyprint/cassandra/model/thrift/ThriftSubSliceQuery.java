package me.prettyprint.cassandra.model.thrift;

import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.ColumnSliceImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SubSliceQuery;

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
public final class ThriftSubSliceQuery<K,SN,N,V> extends AbstractSliceQuery<K,N,V,ColumnSlice<N,V>>
  implements SubSliceQuery<K, SN, N, V>{

  private K key;
  private SN superColumn;
  private final Serializer<SN> sNameSerializer;

  public ThriftSubSliceQuery(Keyspace k, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "Supername serializer cannot be null");
    this.sNameSerializer = sNameSerializer;
  }

  @Override
  public SubSliceQuery<K,SN,N,V> setKey(K key) {
    this.key = key;
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  @Override
  public SubSliceQuery<K,SN,N,V> setSuperColumn(SN superColumn) {
    this.superColumn = superColumn;
    return this;
  }

  @Override
  public QueryResult<ColumnSlice<N, V>> execute() {
    Assert.notNull(key, "Key cannot be null");
    Assert.notNull(superColumn, "Supercolumn cannot be null");
    return new QueryResultImpl<ColumnSlice<N, V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            List<Column> thriftRet = ks.getSlice(keySerializer.toBytes(key), columnParent, getPredicate());
            return new ColumnSliceImpl<N, V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SubSliceQuery(" + key + "," + superColumn + "," + toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubSliceQuery<K, SN, N, V> setColumnNames(N... columnNames) {
    return (SubSliceQuery<K, SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubSliceQuery<K, SN, N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (SubSliceQuery<K, SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubSliceQuery<K, SN, N, V> setColumnFamily(String cf) {
    return (SubSliceQuery<K, SN, N, V>) super.setColumnFamily(cf);
  }
}
