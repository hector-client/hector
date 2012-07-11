package me.prettyprint.cassandra.model.thrift;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.CounterSuperRowsImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSuperRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSuperSliceCounterQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterSuperColumn;

/**
 * A query wrapper for the thrift call multiget_slice for a slice of supercolumns
 *
 */
public final class ThriftMultigetSuperSliceCounterQuery<K, SN, N> extends
    AbstractSliceQuery<K, SN, Long, CounterSuperRows<K, SN, N>> implements MultigetSuperSliceCounterQuery<K, SN, N> {

  private Collection<K> keys;
  private final Serializer<N> nameSerializer;

  public ThriftMultigetSuperSliceCounterQuery(Keyspace keyspace,
                                              Serializer<K> keySerializer,
                                              Serializer<SN> sNameSerializer,
                                              Serializer<N> nameSerializer) {
    super(keyspace, keySerializer, sNameSerializer, LongSerializer.get());
    Assert.notNull(nameSerializer, "nameSerializer can't be null");
    this.nameSerializer = nameSerializer;
  }

  @Override
  public MultigetSuperSliceCounterQuery<K, SN, N> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public MultigetSuperSliceCounterQuery<K, SN, N> setKeys(Collection<K> keys) {
    this.keys = keys;
    return this;
  }

  @Override
  public QueryResult<CounterSuperRows<K, SN, N>> execute() {
    return new QueryResultImpl<CounterSuperRows<K, SN, N>>(
        keyspace.doExecute(new KeyspaceOperationCallback<CounterSuperRows<K, SN, N>>() {
          @Override
          public CounterSuperRows<K, SN, N> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<K> keysList = new ArrayList<K>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<CounterSuperColumn>> thriftRet = keySerializer.fromBytesMap(ks.multigetCounterSuperSlice(
                    keySerializer.toBytesList(keysList), columnParent, getPredicate()));
            return new CounterSuperRowsImpl<K, SN, N>(thriftRet, keySerializer, columnNameSerializer,
                nameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSuperSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceCounterQuery<K, SN, N> setRange(SN start, SN finish, boolean reversed, int count) {
    return (MultigetSuperSliceCounterQuery<K, SN, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceCounterQuery<K, SN, N> setColumnNames(SN... columnNames) {
    return (MultigetSuperSliceCounterQuery<K, SN, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceCounterQuery<K, SN, N> setColumnNames(Collection<SN> columnNames) {
    return (MultigetSuperSliceCounterQuery<K, SN, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceCounterQuery<K, SN, N> setColumnFamily(String cf) {
    return (MultigetSuperSliceCounterQuery<K, SN, N>) super.setColumnFamily(cf);
  }

}
