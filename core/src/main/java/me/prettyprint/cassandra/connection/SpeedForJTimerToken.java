package me.prettyprint.cassandra.connection;

import com.ecyrd.speed4j.StopWatch;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.Operation;

/**
 * @author zznate
 */
public class SpeedForJTimerToken implements TimerToken {

  private final StopWatch stopWatch;
  private String tagName;
  private CassandraHost cassandraHost;
  // TODO pull these up? ^

  public SpeedForJTimerToken(StopWatch stopWatch) {
    this.stopWatch = stopWatch;
  }

  /**
   *
   * @return the underlying {@link StopWatch} 
   */
  @Override
  public Object getToken() {
    return stopWatch;
  }

  @Override
  public CassandraHost getCassandraHost() {
    return cassandraHost;
  }

  /**
   * Returns the tagName which will be null until 'stop' is called
   * SpeedForJ tag names take the form of [operation tag name].["success_"|"fail_"]
   * where operation tag name comes from {@link Operation#stopWatchTagName}
   * @return
   */
  @Override
  public String getTagName() {
    return tagName;  
  }

  @Override
  public void start() {
    stopWatch.start();
  }

  /**
   * Stop this timer and population information from the {@link Operation}
   * @param op
   */
  @Override
  public void stop(Operation op) {
    this.cassandraHost = op.getCassandraHost();
    this.tagName = op.stopWatchTagName.concat(op.getExecutionStatus() ? ".success_" : ".fail_");
    stopWatch.stop(tagName);
  }
}
