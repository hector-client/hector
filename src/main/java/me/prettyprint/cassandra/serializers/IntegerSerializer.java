package me.prettyprint.cassandra.serializers;


/**
 * Converts bytes to Integer and vice versa
 *
 * @author Bozhidar Bozhanov
 *
 */
public final class IntegerSerializer extends AbstractSerializer<Integer> {

  private static final IntegerSerializer instance = new IntegerSerializer();

  public static IntegerSerializer get() {
    return instance;
  }

  @Override
  public byte[] toBytes(Integer obj) {
    if (obj == null) {
      return null;
    }
    int l = obj;
    int size = 4;
    byte[] b = new byte[size];
    for (int i = 0; i < size; ++i) {
      b[i] = (byte) (l >> (size - i - 1 << 3));
    }
    return b;
  }

  @Override
  public Integer fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    int in = 0;
    int size = bytes.length;
    for (int i = 0; i < size; ++i) {
      in |= (bytes[i] & 0xff) << (size - i - 1 << 3);
    }
    return in;
  }

}
