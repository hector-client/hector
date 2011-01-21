package me.prettyprint.cassandra.service.clock;

import me.prettyprint.hector.api.ClockResolution;

/**
 * Seconds Resolution used to create clocks.
 *
 * @author Patricio Echague (pechague@gmail.com)
 */
public class SecondsClockResolution extends AbstractClockResolution implements ClockResolution {

  private static final long serialVersionUID = -371806723621204991L;

  @Override
  public long createClock() {
    return getSystemMilliseconds() / 1000;
  }
}