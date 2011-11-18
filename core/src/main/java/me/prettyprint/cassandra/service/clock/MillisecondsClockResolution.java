package me.prettyprint.cassandra.service.clock;

import me.prettyprint.hector.api.ClockResolution;

/**
 * Milliseconds Resolution used to create clocks.
 *
 * @author Patricio Echague (pechague@gmail.com)
 */
public class MillisecondsClockResolution extends AbstractClockResolution implements ClockResolution {

  private static final long serialVersionUID = 7378744130636812356L;

  @Override
  public long createClock() {
    return getSystemMilliseconds();
  }

}
