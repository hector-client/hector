package me.prettyprint.cassandra.serializers;


/**
 * Converts bytes to Long and vise a versa
 *
 * @author Ran Tavory
 *
 */
public final class LongSerializer extends AbstractSerializer<Long> {

  private static final LongSerializer instance = new LongSerializer();

  public static LongSerializer get() {
    return instance;
  }

  @Override
  public byte[] toBytes(Long obj) {
    if (obj == null) {
      return null;
    }
    long l = obj;
    int size = 8;
    byte[] b = new byte[size];
    for (int i = 0; i < size; ++i) {
      b[i] = (byte) (l >> (size - i - 1 << 3));
    }
    return b;
  }

  @Override
  public Long fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    long l = 0;
    int size = bytes.length;
    for (int i = 0; i < size; ++i) {
      l |= ((long) bytes[i] & 0xff) << (size - i - 1 << 3);
    }
    return l;
  }
}
