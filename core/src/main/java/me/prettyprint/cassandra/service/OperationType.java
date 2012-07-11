package me.prettyprint.cassandra.service;

/**
 * Specifies the "type" of operation - read or write.
 * It's used for Speed4j, so should be in sync with hectorLog4j.xml
 * @author Ran Tavory (ran@outbain.com)
 * 
 */
public enum OperationType {
  /** Read operations*/
  READ,
  /** Write operations */
  WRITE,
  /** Meta read operations, such as describe*() */
  META_READ,
  /** Operation on one of the system_ methods */
  META_WRITE;
}