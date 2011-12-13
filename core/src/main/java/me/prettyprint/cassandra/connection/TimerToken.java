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
   * in order to retrieve additional context information. See the
   * {@link SpeedForJTimerToken} implementation for an example.
   * @return
   */
  Object getToken();

  /**
   * The {@link CassandraHost} against which we executed 
   * @return
   */
  CassandraHost getCassandraHost();

  /**
   *
   * @return an arbitrary tag name. Implementation dependant.
   */
  String getTagName();

  /**
   * enter the 'START' phase of execution. Implementations wishing to track timing
   * would start the clock here
   */
  void start();

  /**
   * Stop this instance, filling in the details from the provided operation.
   * Implementations implementing timing mechanisms would stop the clock here
   * @param op
   */
  void stop(Operation op);
}
