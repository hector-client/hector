package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.HCounterColumn;

/**
 * A CounterQuery is used for querying the value of a single and standard counter column.
 * <p>
 *
 * @author patricio
 *
 * @param <N> Column name type.
 */
public interface CounterQuery<K, N> extends Query<HCounterColumn<N>>{

  /**
   * Set the row key for this query.
   */
  CounterQuery<K, N> setKey(K key);

  /**
   * Set the column name for this query.
   */
  CounterQuery<K, N> setName(N name);

  CounterQuery<K, N> setColumnFamily(String cf);

}