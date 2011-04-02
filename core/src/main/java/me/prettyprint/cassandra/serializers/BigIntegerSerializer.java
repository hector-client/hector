package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.INTEGERTYPE;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import me.prettyprint.hector.api.ddl.ComparatorType;

/**
 * Serializer implementation for BigInteger
 * 
 * @author zznate
 */
public final class BigIntegerSerializer extends AbstractSerializer<BigInteger> {

  private static final BigIntegerSerializer INSTANCE = new BigIntegerSerializer();

  public static BigIntegerSerializer get() {
    return INSTANCE;
  }

  @Override
  public BigInteger fromByteBuffer(ByteBuffer byteBuffer) {
    if (byteBuffer == null) {
      return null;
    }
    int length = byteBuffer.remaining();
    byte[] bytes = new byte[length];
    byteBuffer.duplicate().get(bytes);
    return new BigInteger(bytes);
  }

  @Override
  public ByteBuffer toByteBuffer(BigInteger obj) {
    if (obj == null) {
      return null;
    }
    return ByteBuffer.wrap(obj.toByteArray());
  }

  @Override
  public ComparatorType getComparatorType() {
    return INTEGERTYPE;
  }

}
