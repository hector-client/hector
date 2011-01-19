package me.prettyprint.cassandra.service.clock;

import me.prettyprint.hector.api.ClockResolution;


/**
 * Synchronized Milliseconds Resolution used to create clocks.
 *
 * @author Patricio Echague (pechague@gmail.com)
 */
public class MicrosecondsSyncClockResolution extends AbstractClockResolution implements ClockResolution {

  private static final long serialVersionUID = -4671061000963496156L;
  private static final long ONE_THOUSAND = 1000L;

  /**
   * The last time value issued. Used to try to prevent duplicates.
   */
  private static long lastTime = -1;

  @Override
  public long createClock() {
    // The following simulates a microseconds resolution by advancing a static counter
    // every time a client calls the createClock method, simulating a tick.
    long us = getSystemMilliseconds() * ONE_THOUSAND;
    // Synchronized to guarantee unique time within and across threads.
    synchronized (MicrosecondsSyncClockResolution.class) {
      if (us > lastTime) {
        lastTime = us;
      } else {
        // the time i got from the system is equals or less
        // (hope not - clock going backwards)
        // One more "microsecond"
        us = ++lastTime;
      }
    }
    return us;
  }

}
