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
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query wrapper for the thrift call multiget_slice
 */
public final class ThriftMultigetSliceQuery<N, V> extends AbstractSliceQuery<N, V, Rows<N, V>>
    implements MultigetSliceQuery<N, V> {

  private Collection<String> keys;

  public ThriftMultigetSliceQuery(Keyspace ko, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
  }

  @Override
  public MultigetSliceQuery<N, V> setKeys(String... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public QueryResult<Rows<N, V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(keys, "keys can't be null");

    return new QueryResultImpl<Rows<N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<Rows<N,V>>() {
          @Override
          public Rows<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<String> keysList = new ArrayList<String>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<String, List<Column>> thriftRet =
              ks.multigetSlice(keysList, columnParent, getPredicate());
            return new RowsImpl<N,V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceQuery<N, V> setColumnNames(N... columnNames) {
    return (MultigetSliceQuery<N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceQuery<N, V> setRange(N start, N finish, boolean reversed,
      int count) {
    return (MultigetSliceQuery<N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceQuery<N, V> setColumnFamily(String cf) {
    return (MultigetSliceQuery<N, V>) super.setColumnFamily(cf);
  }
}
