package me.prettyprint.cassandra.model.thrift;


import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.CounterSuperSliceImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSuperSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SuperSliceCounterQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterSuperColumn;


/**
 * A query for the thrift call get_slice.
 * <p>
 * Get a slice of super counter columns from a super column family.
 *
 *
 * @param <N>
 */
public final class ThriftSuperSliceCounterQuery<K, SN, N> extends
    AbstractSliceQuery<K, SN, Long, CounterSuperSlice<SN, N>> implements SuperSliceCounterQuery<K, SN, N> {

  private K key;
  private final Serializer<N> nameSerializer;

  public ThriftSuperSliceCounterQuery(Keyspace keyspace,
                                      Serializer<K> keySerializer,
                                      Serializer<SN> sNameSerializer,
                                      Serializer<N> nameSerializer) {
    super(keyspace, keySerializer, sNameSerializer, LongSerializer.get());
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.nameSerializer = nameSerializer;
  }

  @Override
  public SuperSliceCounterQuery<K, SN, N> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public QueryResult<CounterSuperSlice<SN, N>> execute() {
    return new QueryResultImpl<CounterSuperSlice<SN,N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<CounterSuperSlice<SN,N>>() {
          @Override
          public CounterSuperSlice<SN, N> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<CounterSuperColumn> thriftRet = ks.getCounterSuperSlice(keySerializer.toByteBuffer(key),
                    columnParent, getPredicate());
            return new CounterSuperSliceImpl<SN, N>(thriftRet, columnNameSerializer, nameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SuperSliceQuery(" + key + "," + toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
    public SuperSliceCounterQuery<K, SN, N> setColumnNames(SN... columnNames) {
      return (SuperSliceCounterQuery<K, SN, N>) super.setColumnNames(columnNames);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SuperSliceCounterQuery<K, SN, N> setRange(SN start, SN finish, boolean reversed, int count) {
      return (SuperSliceCounterQuery<K, SN, N>) super.setRange(start, finish, reversed, count);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SuperSliceCounterQuery<K, SN, N> setColumnFamily(String cf) {
      return (SuperSliceCounterQuery<K, SN, N>) super.setColumnFamily(cf);
    }
}
