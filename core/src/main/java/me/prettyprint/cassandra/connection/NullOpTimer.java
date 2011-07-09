package me.prettyprint.cassandra.connection;

public class NullOpTimer implements HOpTimer {

  @Override
  public Object start() {
    return this;
  }

  @Override
  public void stop(Object token, String tagName, boolean success) {
  }

}
