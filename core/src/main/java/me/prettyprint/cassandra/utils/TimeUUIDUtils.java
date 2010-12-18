package me.prettyprint.cassandra.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Utilitary class to generate TimeUUID (type 1)
 *
 * @author Patricio Echague (pechague@gmail.com)
 *
 */
public final class TimeUUIDUtils {

  /**
   * Gets a new time uuid. It is useful to use in a TimeUUIDType sorted column family.
   *
   * @return the time uuid
   */
  public static java.util.UUID getTimeUUID() {
    return java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
  }


  /**
   * Returns an instance of uuid. Useful for when you read out of cassandra
   * you are getting a byte[] that needs to be converted into a TimeUUID.
   *
   * @param uuid the uuid
   * @return the java.util.uuid
   */
  public static java.util.UUID toUUID(byte[] uuid) {
    return uuid(uuid, 0);
  }

  /**
   * Retrieves the time as long based on the byte[] representation of a UUID.
   *
   * @param uuid byte[] uuid representation
   * @return a long representing the time
   */
  public static long getTimeFromUUID(byte[] uuid) {
    return TimeUUIDUtils.toUUID(uuid).timestamp();
  }

  /**
   * As byte array.
   * This method is often used in conjunction with @link {@link #getTimeUUID()}
   *
   * @param uuid the uuid
   *
   * @return the byte[]
   */
  public static byte[] asByteArray(java.util.UUID uuid) {
    long msb = uuid.getMostSignificantBits();
    long lsb = uuid.getLeastSignificantBits();
    byte[] buffer = new byte[16];

    for (int i = 0; i < 8; i++) {
      buffer[i] = (byte) (msb >>> 8 * (7 - i));
    }

    for (int i = 8; i < 16; i++) {
      buffer[i] = (byte) (lsb >>> 8 * (7 - i));
    }

    return buffer;
  }

  /**
   * Coverts a java.util.UUID into a ByteBuffer.
   * @param uuid a java.util.UUID
   * @return a ByteBuffer representaion of the param UUID
   */
  public static ByteBuffer asByteBuffer(java.util.UUID uuid) {
    if (uuid == null) {
      return null;
    }

    return ByteBuffer.wrap(asByteArray(uuid));
  }


  public static UUID uuid(byte[] uuid, int offset) {
    ByteBuffer bb = ByteBuffer.wrap(uuid, offset, 16);
    return new UUID(bb.getLong(), bb.getLong());
  }

  /**
   * Converts a ByteBuffer containing a UUID into a java.util.UUID
   * @param bb a ByteBuffer containing a UUID
   * @return a java.util.UUID
   */
  public static UUID uuid(ByteBuffer bb) {
    bb = bb.slice();
    return new UUID(bb.getLong(), bb.getLong());
  }

}
