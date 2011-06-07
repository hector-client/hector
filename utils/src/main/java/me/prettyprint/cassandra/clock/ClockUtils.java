/**
 * 
 */
package me.prettyprint.cassandra.clock;

import me.prettyprint.hector.api.ClockResolution;

/**
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 *
 */
public class ClockUtils {
	
	  /**
	   * Create a clock resolution based on <code>clockResolutionName</code> which
	   * has to match any of the constants defined at {@link ClockResolution}
	   * 
	   * @param clockResolutionName
	   *          type of clock resolution to create
	   * @return a ClockResolution
	   */
	  public static ClockResolution createClockResolution(String clockResolutionName) {
	    if (clockResolutionName.equals(ClockResolution.SECONDS)) {
	      return new SecondsClockResolution();
	    } else if (clockResolutionName.equals(ClockResolution.MILLISECONDS)) {
	      return new MillisecondsClockResolution();
	    } else if (clockResolutionName.equals(ClockResolution.MICROSECONDS)) {
	      return new MicrosecondsClockResolution();
	    } else if (clockResolutionName.equals(ClockResolution.MICROSECONDS_SYNC)) {
	      return new MicrosecondsSyncClockResolution();
	    }
	    throw new RuntimeException(String.format(
	        "Unsupported clock resolution: %s", clockResolutionName));
	  }

}
