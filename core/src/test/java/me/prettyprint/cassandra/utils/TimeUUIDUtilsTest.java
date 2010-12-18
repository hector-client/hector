package me.prettyprint.cassandra.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import me.prettyprint.cassandra.service.clock.MicrosecondsClockResolution;
import me.prettyprint.cassandra.service.clock.MicrosecondsSyncClockResolution;
import me.prettyprint.hector.api.ClockResolution;

import org.junit.Test;

import com.eaio.uuid.UUIDGen;

/**
 * Test @link {@link TimeUUIDUtils}
 *
 * @author Patricio Echague (pechague@gmail.com)
 *
 */
public class TimeUUIDUtilsTest {

  /**
   * This test must be placed FIRST. Please don't change the order.
   * @throws Exception
   */
  @Test
  public void testTimeUUIDWithClockResolution() throws Exception {
    ClockResolution clock = new MicrosecondsClockResolution();
    long time = clock.createClock();

    // Invoke twice with same time. Both generated UUID should be the same.
    // Test improved algorithm.
    assertEquals(TimeUUIDUtils.getTimeUUID(time),
        java.util.UUID.fromString(
            new com.eaio.uuid.UUID(UUIDGen.createTime(time), UUIDGen.getClockSeqAndNode()).toString()));

    clock = new MicrosecondsSyncClockResolution();
    // Invoke twice with a clockResolution that guarantees unique timestamp. The second must be greater
    // than the first one.
    java.util.UUID first = TimeUUIDUtils.getTimeUUID(clock);
    java.util.UUID second = TimeUUIDUtils.getTimeUUID(clock);
    assertTrue(second.compareTo(first) > 0);
  }

  @Test
  public void testTimeUUIDAsByteArray() {
    // Generate UUID, convert to array and back to UUID. Then assert.
    UUID uuid = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
    UUID uuidAfterConversion = TimeUUIDUtils.toUUID(TimeUUIDUtils.asByteArray(uuid));
    assertEquals(uuid, uuidAfterConversion);

    // Used the previously generated UUID, convert to array and back to UUID. Then compare their times.
    long timeInUUID = TimeUUIDUtils.getTimeFromUUID(TimeUUIDUtils.asByteArray(uuid));
    assertEquals(uuid.timestamp(), timeInUUID);
  }

  @Test
  public void testTimeUUIDAsByteBuffer() {
    UUID expectedUuid = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
    UUID actualUuid = TimeUUIDUtils.uuid(TimeUUIDUtils.asByteBuffer(expectedUuid));
    assertEquals(expectedUuid, actualUuid);
  }



}
