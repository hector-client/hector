package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.connection.TimerToken;

/**
 * The default implementation.
 * 
 * @author zznate
 */
public class NoOpTimerTracker implements HOpTimerTracker {


  /**
   * Does nothing
   * @param timerToken
   */
  @Override
  public void processTimerToken(TimerToken timerToken) {
    // do nothing
  }
}
