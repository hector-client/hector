package me.prettyprint.cassandra.service;

import java.io.Serializable;

import me.prettyprint.cassandra.connection.TimerToken;

/**
 * @author zznate
 */
public class Speed4jTimerTracker implements HOpTimerTracker, Serializable {

  /**
   * Speed4J handles execution and logging logic internally so this is a no-op
   * @param timerToken
   */
  @Override
  public void processTimerToken(TimerToken timerToken) {
      
  }
}
