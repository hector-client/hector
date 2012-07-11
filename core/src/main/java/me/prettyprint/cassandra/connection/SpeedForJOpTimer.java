package me.prettyprint.cassandra.connection;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;

public class SpeedForJOpTimer implements HOpTimer {

  private final StopWatchFactory stopWatchFactory;

  public SpeedForJOpTimer(String clusterName) {
    //
    // This uses speed4j.properties to instantiate a new logger.
	//  pay attention to the naming scheme 
    //
    stopWatchFactory = StopWatchFactory.getInstance("hector-"+clusterName);
  }

  @Override
  public Object start() {
    return stopWatchFactory.getStopWatch();
  }

  @Override
  public void stop(Object token, String tagName, boolean success) {
    ((StopWatch) token).stop(tagName.concat(success ? ".success_" : ".fail_"));
  }

}
