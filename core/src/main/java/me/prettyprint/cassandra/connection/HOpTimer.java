package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.Operation;

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
  TimerToken start();

  /**
   * 
   * @param op The operation for which we will stop the timer
   *
   * @param timerToken Holds the instance information of our timer
   */
  void stop(Operation op, TimerToken timerToken);
}
