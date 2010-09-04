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
/*package*/ abstract class AbstractColumnQuery<N, V> extends AbstractQuery<N, V, HColumn<N, V>>
    implements ColumnQuery<N, V>{

  protected String key;
  protected N name;

  public AbstractColumnQuery(KeyspaceOperator ko, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
  }

  @Override
  public AbstractColumnQuery<N, V> setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public AbstractColumnQuery<N, V> setName(N name) {
    this.name = name;
    return this;
  }

  @Override
  public AbstractColumnQuery<N,V> setColumnFamily(String cf) {
    return (AbstractColumnQuery<N,V>) super.setColumnFamily(cf);
  }

  @Override
  public String toString() {
    return "ColumnQuery(" + key + "," + name + ")";
  }

}