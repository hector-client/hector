package me.prettyprint.cassandra.model;

public abstract class AbstractQuery<T> implements Query<T>{

  protected String columnFamilyName; 

  @Override
  public AbstractQuery<T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }


}
