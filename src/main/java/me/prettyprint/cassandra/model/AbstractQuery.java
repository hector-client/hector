package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;

public abstract class AbstractQuery<N,V,T> implements Query<T>{

  protected final KeyspaceOperator keyspaceOperator;
  protected String columnFamilyName;
  protected final Serializer<N> columnNameSerializer;
  protected final Serializer<V> valueSerializer;


  /*package*/ AbstractQuery(KeyspaceOperator ko, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(ko, nameSerializer, valueSerializer);
    keyspaceOperator = ko;
    this.columnNameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  public AbstractQuery<N,V,T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }
}
