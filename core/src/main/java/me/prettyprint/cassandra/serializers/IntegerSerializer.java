package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;


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
  public ByteBuffer toByteBuffer(Integer obj) {
    if (obj == null) {
      return null;
    }
    int l = obj;
    int size = 4;
    byte[] b = new byte[size];
    for (int i = 0; i < size; ++i) {
      b[i] = (byte) (l >> (size - i - 1 << 3));
    }
    return ByteBuffer.wrap(b);
  }

  @Override
  public Integer fromByteBuffer(ByteBuffer byteBuffer) {
    if (byteBuffer == null) {
      return null;
    }    
    int in = 0;
    int size = byteBuffer.capacity();
    for (int i = 0; i < size; ++i) {
      in |= (byteBuffer.get(i) & 0xff) << (size - i - 1 << 3);
    }
    return in;
  }

}
