package me.prettyprint.cassandra.service;

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
  /**
   * The last time value issued. Used to try to prevent duplicates.
   */
  private static long lastTime = Long.MIN_VALUE;

  public long createClock() {
    long current = System.currentTimeMillis();
    switch(this) {
    case MICROSECONDS:
      long us = current * 1000;
      if (us > lastTime) {
        lastTime = us;
      } else {
        us = ++lastTime;
      }
      return us;
    case MILLISECONDS:
      return current;
    case SECONDS:
      return current / 1000;
    };
    return current;
  }
}
