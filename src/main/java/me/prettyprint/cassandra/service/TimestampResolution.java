package me.prettyprint.cassandra.service;

/**
 * Resolution used to create timestamps.
 * Clients may wish to use millisec, micro or sec, depending on the application
 * needs and existing data and other tools, so Hector makes that
 * configurable.
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public enum TimestampResolution {
  SECONDS, MILLISECONDS, MICROSECONDS;
  
  public long createTimestamp() {
    long current = System.currentTimeMillis();
    switch(this) {
    case MICROSECONDS:
      return current * 1000;
    case MILLISECONDS:
      return current;
    case SECONDS:
      return current / 1000;
    };
    return current;
  }
}
