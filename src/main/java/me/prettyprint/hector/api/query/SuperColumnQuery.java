package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.HSuperColumn;

/**
 * A SuperColumnQuery is used for querying the value of a single entire supercolumn from a SC family
 *
 * @author Ran Tavory
 *
 * @param <K> Type of the key
 * @param <SN> Type of the supercolumn name
 * @param <N> Type of the column name
 * @param <V> Type of the column value
 */
public interface SuperColumnQuery<K, SN, N, V> extends Query<HSuperColumn<SN, N, V>>{

  SuperColumnQuery<K, SN, N, V> setKey(K key);

  SuperColumnQuery<K, SN, N, V> setSuperName(SN superName);

  SuperColumnQuery<K, SN, N, V> setColumnFamily(String cf);

}