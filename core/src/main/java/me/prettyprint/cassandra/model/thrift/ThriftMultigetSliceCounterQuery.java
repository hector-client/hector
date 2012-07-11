package me.prettyprint.cassandra.model.thrift;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.CounterRowsImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterColumn;

/**
 * A query wrapper for the thrift call multiget_slice
 */
public final class ThriftMultigetSliceCounterQuery<K, N> extends AbstractSliceQuery<K, N, Long, CounterRows<K, N>>
    implements MultigetSliceCounterQuery<K, N> {

  private Iterable<K> keys;

  public ThriftMultigetSliceCounterQuery(Keyspace k,
                                         Serializer<K> keySerializer,
                                         Serializer<N> nameSerializer) {
    super(k, keySerializer, nameSerializer, LongSerializer.get());
  }

  @Override
  public MultigetSliceCounterQuery<K, N> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public MultigetSliceCounterQuery<K, N> setKeys(Iterable<K> keys) {
    this.keys = keys;
    return this;
  }


  @Override
  public QueryResult<CounterRows<K, N>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(keys, "keys can't be null");

    return new QueryResultImpl<CounterRows<K, N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<CounterRows<K, N>>() {
          @Override
          public CounterRows<K, N> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<ByteBuffer> keysList = new ArrayList<ByteBuffer>();
            for (K k : keys) {
              keysList.add(keySerializer.toByteBuffer(k));
            }
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<CounterColumn>> thriftRet = keySerializer.fromBytesMap(
                ks.multigetCounterSlice(keysList, columnParent, getPredicate()));
            return new CounterRowsImpl<K, N>(thriftRet, columnNameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceCounterQuery<K, N> setColumnNames(N... columnNames) {
    return (MultigetSliceCounterQuery<K, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceCounterQuery<K, N> setRange(N start, N finish, boolean reversed,
      int count) {
    return (MultigetSliceCounterQuery<K, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSliceCounterQuery<K, N> setColumnFamily(String cf) {
    return (MultigetSliceCounterQuery<K, N>) super.setColumnFamily(cf);
  }
}
