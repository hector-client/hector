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
public abstract class AbstractColumnQuery<N, V> extends AbstractQuery<N, V, HColumn<N, V>>
    implements ColumnQuery<N, V>{

  protected String key;
  protected N name;

  protected AbstractColumnQuery(Keyspace ko, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
  }

  @Override
  public ColumnQuery<N, V> setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public ColumnQuery<N, V> setName(N name) {
    this.name = name;
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ColumnQuery<N, V> setColumnFamily(String cf) {
    return (ColumnQuery<N, V>) super.setColumnFamily(cf);
  }

  @Override
  public String toString() {
    return "AbstractColumnQuery(" + key + "," + name + ")";
  }

}