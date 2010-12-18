package me.prettyprint.cassandra.utils;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

/**
 * Test @link {@link TimeUUIDUtils}
 *
 * @author Patricio Echague (pechague@gmail.com)
 *
 */
public class TimeUUIDUtilsTest {

  @Test
  public void testTimeUUIDAsByteArray() {
    // Generate UUID, convert to array and back to UUID. Then assert.
    UUID uuid = TimeUUIDUtils.getTimeUUID();
    UUID uuidAfterConversion = TimeUUIDUtils.toUUID(TimeUUIDUtils.asByteArray(uuid));
    assertEquals(uuid, uuidAfterConversion);

    // Used the previously generated UUID, convert to array and back to UUID. Then compare their times.
    long timeInUUID = TimeUUIDUtils.getTimeFromUUID(TimeUUIDUtils.asByteArray(uuid));
    assertEquals(uuid.timestamp(), timeInUUID);
  }

  @Test
  public void testTimeUUIDAsByteBuffer() {
    UUID expectedUuid = TimeUUIDUtils.getTimeUUID();
    UUID actualUuid = TimeUUIDUtils.uuid(TimeUUIDUtils.asByteBuffer(expectedUuid));
    assertEquals(expectedUuid, actualUuid);
  }

}
