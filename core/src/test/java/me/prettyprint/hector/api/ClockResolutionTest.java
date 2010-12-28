package me.prettyprint.hector.api;

import static org.junit.Assert.fail;

import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.factory.HFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test @link {@link ClockResolutionTest}
 *
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public class ClockResolutionTest {

  private static final Logger log = LoggerFactory.getLogger(ClockResolutionTest.class);

  /**
   * Test that multiple calls do not generate the same clock (timestamp)
   */
  @Test
  public void testMicrosecondsSync() throws Exception {
    ClockResolution clockResolution = HFactory.createClockResolution(ClockResolution.MICROSECONDS_SYNC);
    long previous = clockResolution.createClock();
    for (int i = 0; i < 50; i++) {
      long current = clockResolution.createClock();
      log.debug("previous=" + previous + " - current=" + current);
      if (current == previous) {
        fail("Two calls to clock generated the same timestamp. (previous=" + previous
            + " - current=" + current + "). Cycle:" + i);
      }
      current = previous;

    }
  }

}
