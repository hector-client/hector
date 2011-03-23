package me.prettyprint.hector.api;

import me.prettyprint.cassandra.service.CassandraHost;

/**
 * Common interface for tracking the {@link CassandraHost} used
 * for the execution of the operation and how long that operation
 * took in micro seconds
 *
 * @author zznate
 */
public interface ResultStatus {

  /**
   * How long the operation took to execute in MICRO-seconds. Internally
   * this is usually the difference between two calls of {@link System#nanoTime()} 
   * divided by 1000
   * @return
   */
  long getExecutionTimeMicro();
  
  long getExecutionTimeNano();

  /**
   * The {@link CassandraHost} on which this operation
   * was successful
   */
  CassandraHost getHostUsed();
}
