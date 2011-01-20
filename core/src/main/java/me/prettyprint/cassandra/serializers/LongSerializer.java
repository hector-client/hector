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
    ByteBuffer b = ByteBuffer.allocate(8);
    b.putLong(obj);
    b.rewind();
    return b;
  }

  @Override
  public Long fromByteBuffer(ByteBuffer byteBuffer) {
    if ((byteBuffer == null) || (byteBuffer.remaining() < 8)) {
      return null;
    }
    long l = byteBuffer.getLong();
    return l;
  }

}
