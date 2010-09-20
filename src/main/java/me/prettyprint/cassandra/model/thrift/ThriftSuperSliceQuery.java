package me.prettyprint.cassandra.model.thrift;

import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.model.SuperSliceImpl;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.beans.SuperSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.SuperSliceQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;


/**
 * A query for the thrift call get_slice.
 * <p>
 * Get a slice of super columns from a super column family.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class ThriftSuperSliceQuery<K, SN, N, V> extends
    AbstractSliceQuery<K, N, V, SuperSlice<SN, N, V>> implements SuperSliceQuery<K, SN, N, V> {

  private K key;
  private final Serializer<SN> sNameSerializer;

  public ThriftSuperSliceQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
  }

  @Override
  public SuperSliceQuery<K, SN, N, V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public Result<SuperSlice<SN,N,V>> execute() {
    return new Result<SuperSlice<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<SuperSlice<SN,N,V>>() {
          @Override
          public SuperSlice<SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<SuperColumn> thriftRet = ks.getSuperSlice(keySerializer.toBytes(key),
                columnParent, getPredicate());
            return new SuperSliceImpl<SN, N, V>(thriftRet, sNameSerializer, columnNameSerializer,
                valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SuperSliceQuery(" + key + "," + toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
    public SuperSliceQuery<K, SN, N, V> setColumnNames(N... columnNames) {
      return (SuperSliceQuery<K, SN, N, V>) super.setColumnNames(columnNames);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SuperSliceQuery<K, SN, N, V> setRange(N start, N finish, boolean reversed, int count) {
      return (SuperSliceQuery<K, SN, N, V>) super.setRange(start, finish, reversed, count);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SuperSliceQuery<K, SN, N, V> setColumnFamily(String cf) {
      return (SuperSliceQuery<K, SN, N, V>) super.setColumnFamily(cf);
    }
}
