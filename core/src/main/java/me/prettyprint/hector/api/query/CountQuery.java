package me.prettyprint.hector.api.query;

/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public interface CountQuery<K, N> extends Query<Integer>{

  CountQuery<K, N> setKey(K key);

  CountQuery<K, N> setColumnFamily(String cf);

  CountQuery<K, N> setRange(N start, N finish, int count);

}