package me.prettyprint.hector.api;


/**
 *
 * @author Ran Tavory
 *
 */
public interface Keyspace {

  public static final String KEYSPACE_SYSTEM = "system";
  
  void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp);

  String getKeyspaceName();
  
  long createClock();
}