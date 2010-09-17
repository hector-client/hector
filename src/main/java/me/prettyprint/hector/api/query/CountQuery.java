package me.prettyprint.hector.api.query;

/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public interface CountQuery extends Query<Integer>{

  CountQuery setKey(String key);

  CountQuery setColumnFamily(String cf);

}