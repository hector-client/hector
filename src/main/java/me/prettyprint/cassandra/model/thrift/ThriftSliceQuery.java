package me.prettyprint.cassandra.model.thrift;

import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.ColumnSliceImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
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
public final class ThriftSliceQuery<N, V> extends AbstractSliceQuery<N, V, ColumnSlice<N, V>>
    implements SliceQuery<N, V> {

  private String key;

  /*package*/public ThriftSliceQuery(KeyspaceOperator ko, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
  }

  @Override
  public SliceQuery<N, V> setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public Result<ColumnSlice<N, V>> execute() {
    return new Result<ColumnSlice<N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<Column> thriftRet = ks.getSlice(key, columnParent, getPredicate());
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
  public SliceQuery<N, V> setColumnNames(N... columnNames) {
    return (SliceQuery<N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceQuery<N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (SliceQuery<N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceQuery<N, V> setColumnFamily(String cf) {
    return (SliceQuery<N, V>) super.setColumnFamily(cf);
  }
}
