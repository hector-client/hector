package me.prettyprint.cassandra.service;

import java.io.Serializable;

import me.prettyprint.cassandra.connection.TimerToken;

/**
 * The default implementation.
 * 
 * @author zznate
 */
public class NoOpTimerTracker implements HOpTimerTracker, Serializable {


  /**
   * Does nothing
   * @param timerToken
   */
  @Override
  public void processTimerToken(TimerToken timerToken) {
    // do nothing
  }
}
