package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;


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
  public ByteBuffer toByteBuffer(Long obj) {
    if (obj == null) {
      return null;
    }
    long l = obj;
    int size = 8;
    ByteBuffer b = ByteBuffer.allocate(size);
    for (int i = 0; i < size; ++i) {
      b.put(i,(byte) (l >> (size - i - 1 << 3)));
    }
    return b;
  }

  @Override
  public Long fromByteBuffer(ByteBuffer byteBuffer) {
    if (byteBuffer == null || !byteBuffer.hasArray()) {
      return null;
    }
    long l = 0;
    int size = byteBuffer.capacity();
    for (int i = 0; i < size; ++i) {
      l |= ((long) byteBuffer.get(i) & 0xff) << (size - i - 1 << 3);
    }
    return l;
  }

}
