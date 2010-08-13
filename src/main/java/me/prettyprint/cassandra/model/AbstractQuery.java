package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;

public abstract class AbstractQuery<K,N,V,T> implements Query<T>{

  protected final KeyspaceOperator keyspaceOperator;
  protected String columnFamilyName;
  protected final Extractor<K> keyExtractor;
  protected final Extractor<N> columnNameExtractor;
  protected final Extractor<V> valueExtractor;


  /*package*/ AbstractQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    Assert.noneNull(ko, keyExtractor, nameExtractor, valueExtractor);
    keyspaceOperator = ko;
    this.keyExtractor = keyExtractor;
    this.columnNameExtractor = nameExtractor;
    this.valueExtractor = valueExtractor;
  }

  public AbstractQuery<K,N,V,T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }
}
