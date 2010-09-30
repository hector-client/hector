package me.prettyprint.hector.api.query;

import me.prettyprint.hector.api.beans.HColumn;

/**
 * Used to get the value of a subcolumn within a super column
 *
 * @author Ran Tavory
 *
 * @param <K> Type of the key
 * @param <SN> supercolumn name type
 * @param <N> column name type
 * @param <V> column value type
 */
public interface SubColumnQuery<K, SN, N, V> extends Query<HColumn<N,V>>{

  SubColumnQuery<K, SN, N, V> setKey(K key);

  SubColumnQuery<K, SN, N, V> setSuperColumn(SN superName);

  SubColumnQuery<K, SN, N, V> setColumn(N columnName);

  SubColumnQuery<K, SN, N, V> setColumnFamily(String cf);

}