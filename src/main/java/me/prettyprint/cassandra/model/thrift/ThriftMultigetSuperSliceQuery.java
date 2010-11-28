package me.prettyprint.cassandra.model.thrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.model.SuperRowsImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.SuperRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A query wrapper for the thrift call multiget_slice for a slice of supercolumns
 *
 * @author ran
 */
public final class ThriftMultigetSuperSliceQuery<K, SN, N, V> extends
    AbstractSliceQuery<K, SN, V, SuperRows<K, SN, N, V>> implements MultigetSuperSliceQuery<K, SN, N, V> {

  private Collection<K> keys;
  private final Serializer<N> nameSerializer;

  public ThriftMultigetSuperSliceQuery(Keyspace keyspace,
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, keySerializer, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer can't be null");
    this.nameSerializer = nameSerializer;
  }

  @Override
  public MultigetSuperSliceQuery<K, SN, N, V> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public QueryResult<SuperRows<K, SN, N, V>> execute() {
    return new QueryResultImpl<SuperRows<K, SN, N, V>>(
        keyspace.doExecute(new KeyspaceOperationCallback<SuperRows<K, SN, N, V>>() {
          @Override
          public SuperRows<K, SN, N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<K> keysList = new ArrayList<K>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<SuperColumn>> thriftRet = keySerializer.fromBytesMap(ks.multigetSuperSlice(
                keySerializer.toBytesList(keysList), columnParent, getPredicate()));
            return new SuperRowsImpl<K, SN, N, V>(thriftRet, keySerializer, columnNameSerializer,
                nameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSuperSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceQuery<K, SN, N, V> setRange(SN start, SN finish, boolean reversed, int count) {
    return (MultigetSuperSliceQuery<K, SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceQuery<K, SN, N, V> setColumnNames(SN... columnNames) {
    return (MultigetSuperSliceQuery<K, SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceQuery<K, SN, N, V> setColumnFamily(String cf) {
    return (MultigetSuperSliceQuery<K, SN, N, V>) super.setColumnFamily(cf);
  }

}
