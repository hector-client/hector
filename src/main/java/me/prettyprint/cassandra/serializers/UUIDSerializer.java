package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A UUIDSerializer translates the byte[] to and from UUID types.
 * @author Ed Anuff
 *
 */
public final class UUIDSerializer extends AbstractSerializer<UUID> {

  private static final UUIDSerializer instance = new UUIDSerializer();

  public static UUIDSerializer get() {
    return instance;
  }

  public byte[] toBytes(UUID uuid) {
    if (uuid == null) {
      return null;
    }
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

  public UUID fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    ByteBuffer bb = ByteBuffer.wrap(bytes, 0, 16);
    return new UUID(bb.getLong(), bb.getLong());
  }

}
