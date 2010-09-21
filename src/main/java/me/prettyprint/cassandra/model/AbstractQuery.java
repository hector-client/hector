package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.Query;

public abstract class AbstractQuery<N,V,T> implements Query<T>{

  protected final ExecutingKeyspace keyspace;
  protected String columnFamilyName;
  protected final Serializer<N> columnNameSerializer;
  protected final Serializer<V> valueSerializer;


  /*package*/ AbstractQuery(Keyspace ko, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(ko, nameSerializer, valueSerializer);
    keyspace = (ExecutingKeyspace) ko;
    this.columnNameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  public Query<T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }
}
