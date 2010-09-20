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
public interface ColumnQuery<N, V> extends Query<HColumn<N, V>>{

  /**
   * Set the row key for this query.
   */
  ColumnQuery<N, V> setKey(String key);

  /**
   * Set the column name for this query.
   */
  ColumnQuery<N, V> setName(N name);

  ColumnQuery<N,V> setColumnFamily(String cf);

}