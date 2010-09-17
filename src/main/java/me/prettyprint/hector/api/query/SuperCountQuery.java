package me.prettyprint.hector.api.query;


/**
 * Counts super column for a of a key in a super column family
 *
 * @author Ran Tavory
 */
public interface SuperCountQuery extends  Query<Integer>{

  SuperCountQuery setKey(String key);

  SuperCountQuery setColumnFamily(String cf);

}