package me.prettyprint.cassandra.service;

import org.apache.cassandra.thrift.Clock;

/**
 * Resolution used to create clocks.
 * Clients may wish to use millisec, micro or sec, depending on the application
 * needs and existing data and other tools, so Hector makes that
 * configurable.
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public enum ClockResolution {
  SECONDS, MILLISECONDS, MICROSECONDS;
  
  public Clock createClock() {
    long current = System.currentTimeMillis();
    switch(this) {
    case MICROSECONDS:
      return new Clock(current * 1000);
    case MILLISECONDS:
      return new Clock(current);
    case SECONDS:
      return new Clock(current / 1000);
    };
    return new Clock(current);
  }
}
