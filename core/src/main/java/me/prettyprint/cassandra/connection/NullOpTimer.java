package me.prettyprint.cassandra.connection;

import java.io.Serializable;

import me.prettyprint.cassandra.service.Operation;

/**
 * Default implementation. Holds no members, all API calls are no-ops or return null
 *
 * @author zznate
 */
public class NullOpTimer implements HOpTimer, Serializable {

  private static final long serialVersionUID = -4762728985083933452L;

  @Override
  public TimerToken start() {
    return null;
  }

  @Override
  public void stop(Operation op, TimerToken timerToken, boolean success) {
  }

}
