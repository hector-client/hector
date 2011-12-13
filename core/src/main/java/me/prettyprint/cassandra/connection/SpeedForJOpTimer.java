package me.prettyprint.cassandra.connection;

import com.ecyrd.speed4j.StopWatchFactory;
import me.prettyprint.cassandra.service.Operation;

public class SpeedForJOpTimer implements HOpTimer {

  private final StopWatchFactory stopWatchFactory;

  public SpeedForJOpTimer(String clusterName) {
    //
    // This uses speed4j.properties to instantiate a new logger.
	  //  pay attention to the naming scheme
    //
    stopWatchFactory = StopWatchFactory.getInstance("hector-"+clusterName);
  }

  /**
   * Build and return a new {@link SpeedForJTimerToken}
   * @return
   */
  @Override
  public TimerToken start() {
    return new SpeedForJTimerToken(stopWatchFactory.getStopWatch());
  }

  /**
   * Invoke stop on the provided token, using {@link Operation#getCassandraHost} and
   * {@link Operation#stopWatchTagName} to fill in the detail
   * @param op The operation for which we will stop the timer
   *
   * @param token
   * @param status
   */
  @Override
  public void stop(Operation op, TimerToken token) {
     token.stop(op);
  }

}
