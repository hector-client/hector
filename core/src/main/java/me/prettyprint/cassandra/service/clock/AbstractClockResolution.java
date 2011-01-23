package me.prettyprint.cassandra.service.clock;

/**
 * Define common functionaly for ClockResolution Implementations.
 *
 * @author pechague
 *
 */
public abstract class AbstractClockResolution {


    protected long getSystemMilliseconds() {
      return System.currentTimeMillis();
    }

}
