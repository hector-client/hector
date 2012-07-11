package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.query.SuperColumnQuery;

/**
 * Defines the commonalities b/w avro and thrift SuperColumnQuery implementations
 *
 * @author Ran Tavory
 *
 * @param <SN>
 * @param <N>
 * @param <V>
 */
public abstract class AbstractSuperColumnQuery<K, SN, N, V> extends
    AbstractQuery<K, N, V, HSuperColumn<SN, N, V>> implements SuperColumnQuery<K, SN, N, V>{

  protected final Serializer<SN> sNameSerializer;
  protected K key;
  protected SN superName;

  protected AbstractSuperColumnQuery(Keyspace k,
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
    noneNull(sNameSerializer, nameSerializer, valueSerializer);
    this.sNameSerializer = sNameSerializer;
  }

  @Override
  public SuperColumnQuery<K, SN, N, V> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public SuperColumnQuery<K, SN, N, V> setSuperName(SN superName) {
    this.superName = superName;
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public SuperColumnQuery<K, SN, N, V> setColumnFamily(String cf) {
    return (SuperColumnQuery<K, SN, N, V>) super.setColumnFamily(cf);
  }

  @Override
  public String toString() {
    return "SuperColumnQuery(" + key + "," + superName + ")";
  }
}