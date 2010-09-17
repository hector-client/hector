package me.prettyprint.hector.api.query;


/**
 * Counts sub columns for a of a key and a super column in a super column family
 *
 * @author Ran Tavory
 *
 * @param <SN> super column name tyoe
 */
public interface SubCountQuery<SN> extends Query<Integer>{

  SubCountQuery<SN> setSuperColumn(SN sc);

  SubCountQuery<SN> setKey(String key);

  SubCountQuery<SN> setColumnFamily(String cf);

}