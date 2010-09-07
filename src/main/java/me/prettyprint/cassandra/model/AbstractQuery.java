package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.query.Query;

public abstract class AbstractQuery<K,N,V,T> implements Query<T>{

  protected final KeyspaceOperator keyspaceOperator;
  protected String columnFamilyName;
  protected final Serializer<K> keySerializer;
  protected final Serializer<N> columnNameSerializer;
  protected final Serializer<V> valueSerializer;


  /*package*/ AbstractQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(ko, keySerializer, nameSerializer, valueSerializer);
    keyspaceOperator = ko;
    this.keySerializer = keySerializer;
    this.columnNameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  public AbstractQuery<K,N,V,T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }
}
