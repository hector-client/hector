package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;


/**
 * A query for the thrift call get_slice
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class SliceQuery<K,N,V> extends AbstractSliceQuery<K,N,V,ColumnSlice<N,V>> {

  private K key;

  /*package*/ SliceQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public SliceQuery<K,N,V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public Result<ColumnSlice<N, V>> execute() {
    return new Result<ColumnSlice<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<Column> thriftRet = ks.getSlice(keySerializer.toBytes(key), columnParent, getPredicate());
            return new ColumnSlice<N, V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SliceQuery(" + key + "," + toStringInternal() + ")";
  }
}
