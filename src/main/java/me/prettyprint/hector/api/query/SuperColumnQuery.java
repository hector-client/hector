package me.prettyprint.hector.api.query;

import me.prettyprint.cassandra.model.HSuperColumn;

/**
 * A SuperColumnQuery is used for querying the value of a single entire supercolumn from a SC family
 *
 * @author Ran Tavory
 *
 * @param <SN> Type of the supercolumn name
 * @param <N> Type of the column name
 * @param <V> Type of the column value
 */
public interface SuperColumnQuery<SN, N, V> extends Query<HSuperColumn<SN, N, V>>{

  SuperColumnQuery<SN, N, V> setKey(String key);

  SuperColumnQuery<SN, N, V> setSuperName(SN superName);

  SuperColumnQuery<SN, N, V> setColumnFamily(String cf);

}