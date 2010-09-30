package me.prettyprint.cassandra.model.thrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.model.RowsImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query wrapper for the thrift call multiget_slice
 */
public final class ThriftMultigetSliceQuery<K, N, V> extends AbstractSliceQuery<K, N, V, Rows<K, N, V>>
    implements MultigetSliceQuery<K, N, V> {

  private Collection<K> keys;

  public ThriftMultigetSliceQuery(Keyspace k,
      Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
  }

  @Override
  public MultigetSliceQuery<K, N, V> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public QueryResult<Rows<K, N,V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(keys, "keys can't be null");

    return new QueryResultImpl<Rows<K, N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<Rows<K, N,V>>() {
          @Override
          public Rows<K, N,V> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<K> keysList = new ArrayList<K>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.multigetSlice(keySerializer.toBytesList(keysList), columnParent, getPredicate()));
            return new RowsImpl<K, N, V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceQuery<K, N, V> setColumnNames(N... columnNames) {
    return (MultigetSliceQuery<K, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceQuery<K, N, V> setRange(N start, N finish, boolean reversed,
      int count) {
    return (MultigetSliceQuery<K, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceQuery<K, N, V> setColumnFamily(String cf) {
    return (MultigetSliceQuery<K, N, V>) super.setColumnFamily(cf);
  }
}
