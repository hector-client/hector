package me.prettyprint.hector.api.query;


/**
 * Counts sub columns for a of a key and a super column in a super column family
 *
 * @author Ran Tavory
 *
 * @param <SN> super column name tyoe
 */
public interface SubCountQuery<K, SN, N> extends Query<Integer>{

  SubCountQuery<K, SN, N> setSuperColumn(SN sc);

  SubCountQuery<K, SN, N> setKey(K key);

  SubCountQuery<K, SN, N> setColumnFamily(String cf);

  SubCountQuery<K, SN, N> setRange(N start, N finish, int count);

}