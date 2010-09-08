package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query wrapper for the thrift call multiget_slice
 */
public final class MultigetSliceQuery<K,N,V> extends AbstractSliceQuery<K,N,V,Rows<K,N,V>> {

  private Collection<K> keys;

  public MultigetSliceQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public MultigetSliceQuery<K,N,V> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }


  @Override
  public Result<Rows<K,N,V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(keys, "keys can't be null");

    return new Result<Rows<K,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<Rows<K,N,V>>() {

          @Override
          public Rows<K,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            List<K> keysList = new ArrayList<K>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
              ks.multigetSlice(keySerializer.toBytesList(keysList), columnParent, getPredicate()));
            return new Rows<K,N,V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }


  @Override
  public String toString() {
    return "MultigetSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }
}
