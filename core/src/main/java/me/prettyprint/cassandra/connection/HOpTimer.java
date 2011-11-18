package me.prettyprint.cassandra.connection;

/**
 * Timer For Cassandra operations
 */
public interface HOpTimer {

  /**
   * Start timing an operation.
   * 
   * @return - a token that will be returned to the timer when stop(...) in
   *         invoked
   */
  Object start();

  /**
   * 
   * @param token
   *          - the token returned from start
   * @param tagName
   *          - the name of the tag
   * @param success
   *          - did the oepration succeed
   */
  void stop(Object token, String tagName, boolean success);
}
