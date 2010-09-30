package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.HColumn;

/**
 * A ColumnQuery is used for querying the value of a single and standard column.
 * <p>
 * To read a value of a super column use SuperColumnQuery.
 * To read a value of a sub-column within a super column use SubColumnQuery.
 *
 * @author Ran Tavory
 *
 * @param <N> Column name type.
 * @param <V> Column value type.
 */
public interface ColumnQuery<K, N, V> extends Query<HColumn<N, V>>{

  /**
   * Set the row key for this query.
   */
  ColumnQuery<K, N, V> setKey(K key);

  /**
   * Set the column name for this query.
   */
  ColumnQuery<K, N, V> setName(N name);

  ColumnQuery<K, N,V> setColumnFamily(String cf);

}