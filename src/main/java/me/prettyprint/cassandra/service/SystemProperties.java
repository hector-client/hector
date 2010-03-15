package me.prettyprint.cassandra.service;

/**
 * System properties used by Hector.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public enum SystemProperties {

  /**
   * Should hector perform name resolution?
   * Default: no; Do not perform name resolution
   * For example: -DHECTOR_PERFORM_NAME_RESOLUTION=true
   */
  HECTOR_PERFORM_NAME_RESOLUTION,

  /**
   * What's the default socket timeout for thrift in miliseconds.
   * Default: system settings (about a minute or more)
   * Example: -DCASSANDRA_THRIFT_SOCKET_TIMEOUT=5000
   */
  CASSANDRA_THRIFT_SOCKET_TIMEOUT,
}
