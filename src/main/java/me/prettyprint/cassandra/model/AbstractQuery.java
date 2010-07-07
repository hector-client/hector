package me.prettyprint.cassandra.model;

public abstract class AbstractQuery<T> implements Query<T>{

  protected final KeyspaceOperator keyspaceOperator;
  protected String columnFamilyName;

  /*package*/ AbstractQuery(KeyspaceOperator ko) {
    keyspaceOperator = ko;
  }

  @SuppressWarnings("unchecked")
  @Override
  public AbstractQuery<T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }
}
