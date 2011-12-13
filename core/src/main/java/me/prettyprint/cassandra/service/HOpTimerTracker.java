package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.connection.TimerToken;

/**
 * API for BYO-stats tracking
 * @author zznate
 */
public interface HOpTimerTracker {

  /**
   * Invoked by HConnectionManager on each {@link Operation} execution attempt
   * @param timerToken
   */
  void processTimerToken(TimerToken timerToken);
  
}
