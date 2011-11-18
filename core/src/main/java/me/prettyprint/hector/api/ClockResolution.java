package me.prettyprint.hector.api;

import me.prettyprint.cassandra.service.clock.MicrosecondsClockResolution;
import me.prettyprint.cassandra.service.clock.MicrosecondsSyncClockResolution;
import me.prettyprint.cassandra.service.clock.MillisecondsClockResolution;
import me.prettyprint.cassandra.service.clock.SecondsClockResolution;

import java.io.Serializable;


/**
 *	Clock interface to allow client define their own behavior for Clock Resolution.
 *
 * @see {@link SecondsClockResolution}
 * @see {@link MillisecondsClockResolution}
 * @see {@link MicrosecondsClockResolution}
 * @see {@link MicrosecondsSyncClockResolution}
 *
 * @author Patricio Echague (pechague@gmail.com)
 */
public interface ClockResolution extends Serializable {

  /**
   * Provides a clock resolution with seconds granularity.
   * @see {@link SecondsClockResolution}
   */
  static final String SECONDS ="SECONDS";

  /**
   * Provides a clock resolution with milliseconds granularity.
   * @see {@link MillisecondsClockResolution}
   */
  static final String MILLISECONDS = "MILLISECONDS";

  /**
   * Provides a clock resolution with microseconds granularity.
   * @see {@link MicrosecondsClockResolution}
   */
  static final String MICROSECONDS = "MICROSECONDS";

  /**
   * Provides a clock resolution with microseconds granularity and assuring a unique timestamp
   * within and across threads.
   * @see {@link MicrosecondsSyncClockResolution}
   */
  static final String MICROSECONDS_SYNC = "MICROSECONDS_SYNC";

  /**
   * Creates a timestamp.
   */
  public long createClock();

}
