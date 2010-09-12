package me.prettyprint.hector.api.query;

import me.prettyprint.cassandra.model.HColumn;

/**
 * Used to get the value of a subcolumn within a super column
 *
 * @author Ran Tavory
 *
 * @param <SN> supercolumn name type
 * @param <N> column name type
 * @param <V> column value type
 */
public interface SubColumnQuery<SN, N, V> extends Query<HColumn<N,V>>{

  SubColumnQuery<SN, N, V> setKey(String key);

  SubColumnQuery<SN, N, V> setSuperColumn(SN superName);

  SubColumnQuery<SN, N, V> setColumn(N columnName);

  SubColumnQuery<SN, N, V> setColumnFamily(String cf);

}