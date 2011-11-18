package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.ColumnQuery;

/**
 * Defines the commonalities b/w the Avro and the Thrift ColumnQuery implementations.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public abstract class AbstractColumnQuery<K, N, V> extends AbstractQuery<K, N, V, HColumn<N, V>>
    implements ColumnQuery<K, N, V>{

  protected K key;
  protected N name;

  protected AbstractColumnQuery(Keyspace k, Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
  }

  @Override
  public ColumnQuery<K, N, V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public ColumnQuery<K, N, V> setName(N name) {
    this.name = name;
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ColumnQuery<K, N, V> setColumnFamily(String cf) {
    return (ColumnQuery<K, N, V>) super.setColumnFamily(cf);
  }

  @Override
  public String toString() {
    return "AbstractColumnQuery(" + key + "," + name + ")";
  }

}