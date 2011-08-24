package me.prettyprint.cassandra.connection;

import java.io.Serializable;

public class NullOpTimer implements HOpTimer, Serializable {

  private static final long serialVersionUID = -4762728985083933452L;

  @Override
  public Object start() {
    return this;
  }

  @Override
  public void stop(Object token, String tagName, boolean success) {
  }

}
