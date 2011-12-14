package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.LONGTYPE;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.ddl.ComparatorType;

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
    return ByteBuffer.allocate(8).putLong(0, obj);
  }

  @Override
  public Long fromByteBuffer(ByteBuffer byteBuffer) {
    if ((byteBuffer == null) || (byteBuffer.remaining() < 8)) {
      return null;
    }
    long l = byteBuffer.getLong();
    return l;
  }

  @Override
  public ComparatorType getComparatorType() {
    return LONGTYPE;
  }

}
