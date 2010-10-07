package me.prettyprint.cassandra.service;

import java.util.concurrent.atomic.AtomicLong;

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
  /** The last time value issued. Used to try to prevent duplicates. */
  private static final AtomicLong lastTime = new AtomicLong(Long.MIN_VALUE);

  public long createClock() {
    long current = System.currentTimeMillis();
    switch (this) {
    case MICROSECONDS:
      // The following simmulates a microsec resolution by advancing a static counter every time
      // a client calls the createClock method, simulating a tick.
      long us = current * 1000;
      if (us > lastTime.longValue()) {
        lastTime.set(us);
      } else { // the time i got from the system is equals or less (hope not - clock going
               // backwards)
        // One more "microsecond"
        us = lastTime.incrementAndGet();
      }
      return us;
    case MILLISECONDS:
      return current;
    case SECONDS:
      return current / 1000;
    }
    ;
    return current;
  }
}
