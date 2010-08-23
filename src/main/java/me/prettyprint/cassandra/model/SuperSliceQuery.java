package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

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
public final class SuperSliceQuery<K,SN,N,V> extends AbstractSliceQuery<K,N,V,SuperSlice<SN,N,V>> {

  private K key;
  private final Serializer<SN> sNameSerializer;

  /*package*/ SuperSliceQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
  }

  public SuperSliceQuery<K,SN,N,V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public Result<SuperSlice<SN,N,V>> execute() {
    return new Result<SuperSlice<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<SuperSlice<SN,N,V>>() {
          @Override
          public SuperSlice<SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<SuperColumn> thriftRet = ks.getSuperSlice(keySerializer.toBytes(key), columnParent, getPredicate());
            return new SuperSlice<SN,N,V>(thriftRet, sNameSerializer, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SuperSliceQuery(" + key + "," + toStringInternal() + ")";
  }
}
