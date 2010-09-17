package me.prettyprint.hector.api.query;


/**
 * Counts super column for a of a key in a super column family
 *
 * @author Ran Tavory
 */
public interface SuperCountQuery<K, SN> extends  Query<Integer>{

  SuperCountQuery<K, SN> setKey(K key);

  SuperCountQuery<K, SN> setColumnFamily(String cf);

  SuperCountQuery<K, SN> setRange(SN start, SN finish, int count);

}