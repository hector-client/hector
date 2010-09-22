package me.prettyprint.cassandra.model.thrift;

import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.ColumnSliceImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query for the thrift call get_slice
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class ThriftSliceQuery<K, N, V> extends AbstractSliceQuery<K, N, V, ColumnSlice<N, V>>
    implements SliceQuery<K, N, V> {

  private K key;

  public ThriftSliceQuery(Keyspace k,
      Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
  }

  @Override
  public SliceQuery<K, N, V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public QueryResult<ColumnSlice<N, V>> execute() {
    return new QueryResultImpl<ColumnSlice<N, V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<Column> thriftRet = ks.getSlice(keySerializer.toBytes(key), columnParent, getPredicate());
            return new ColumnSliceImpl<N, V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SliceQuery(" + key + "," + toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceQuery<K, N, V> setColumnNames(N... columnNames) {
    return (SliceQuery<K, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceQuery<K, N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (SliceQuery<K, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceQuery<K, N, V> setColumnFamily(String cf) {
    return (SliceQuery<K, N, V>) super.setColumnFamily(cf);
  }
}
