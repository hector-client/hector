package me.prettyprint.hector.api;

import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.hector.api.exceptions.HectorException;


/**
 *
 * @author Ran Tavory
 *
 */
public interface Keyspace {

  public static final String KEYSPACE_SYSTEM = "system";
  
  void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp);


  long createClock();

}