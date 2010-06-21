package me.prettyprint.cassandra.model;

/**
 * 
 * @author Ran Tavory
 *
 * @param <T> Result type. For example Column or SuperColumn
 */
public interface Query<T> {

  <Q extends Query<T>> Q setColumnFamily(String cf);

  Result<T> execute();

}
