package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.Operation;

/**
 * Hold execution information about an individual op timing event
 * @author zznate
 */
public interface TimerToken {

  /**
   * Implementations are free to return an underlying wrapper of some sort
   * in order to retrieve additional context information
   * @return
   */
  Object getToken();

  CassandraHost getCassandraHost();

  String getTagName();

  void start();

  /**
   * Stop this instance, filling in the details from the provided operation
   * @param op
   */
  void stop(Operation op);
}
