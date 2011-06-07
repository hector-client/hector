package prettyprint.cassandra.clock;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import me.prettyprint.cassandra.clock.ClockUtils;
import me.prettyprint.cassandra.clock.MicrosecondsClockResolution;
import me.prettyprint.cassandra.clock.MicrosecondsSyncClockResolution;
import me.prettyprint.cassandra.clock.MillisecondsClockResolution;
import me.prettyprint.cassandra.clock.SecondsClockResolution;
import me.prettyprint.hector.api.ClockResolution;

import org.junit.Test;

public class ClockUtilsTest {

	  @Test
	  public void testCreateClockResolution() throws Exception {
	    try {
	      ClockUtils.createClockResolution("ItDoesNotExist");
	      fail();
	    } catch (RuntimeException e) {
	        // good!
	    }

	    assertTrue(ClockUtils.createClockResolution(ClockResolution.SECONDS) instanceof SecondsClockResolution);
	    assertTrue(ClockUtils.createClockResolution(ClockResolution.MILLISECONDS) instanceof MillisecondsClockResolution);
	    assertTrue(ClockUtils.createClockResolution(ClockResolution.MICROSECONDS) instanceof MicrosecondsClockResolution);
	    assertTrue(ClockUtils.createClockResolution(ClockResolution.MICROSECONDS_SYNC)
	            instanceof MicrosecondsSyncClockResolution);
	  }

}
