package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.query.ColumnQuery;

/**
 * Defines the commonalities b/w the Avro and the Thrift ColumnQuery implementations.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
/*package*/ abstract class AbstractColumnQuery<K, N, V> extends AbstractQuery<K, N, V, HColumn<N, V>>
    implements ColumnQuery<K, N, V>{

  protected K key;
  protected N name;

  public AbstractColumnQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public AbstractColumnQuery<K, N, V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public AbstractColumnQuery<K, N, V> setName(N name) {
    this.name = name;
    return this;
  }

  @Override
  public AbstractColumnQuery<K, N,V> setColumnFamily(String cf) {
    return (AbstractColumnQuery<K, N,V>) super.setColumnFamily(cf);
  }

  @Override
  public String toString() {
    return "ColumnQuery(" + key + "," + name + ")";
  }

}