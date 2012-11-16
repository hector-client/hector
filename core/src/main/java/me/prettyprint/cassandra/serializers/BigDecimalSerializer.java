package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.DECIMALTYPE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import me.prettyprint.hector.api.ddl.ComparatorType;

/**
 * Serializer implementation for BigDecimal
 * 
 * @author peter lin
 */
public final class BigDecimalSerializer extends AbstractSerializer<BigDecimal> {

  private static final BigDecimalSerializer INSTANCE = new BigDecimalSerializer();

  public static BigDecimalSerializer get() {
    return INSTANCE;
  }

  @Override
  public byte[] toBytes(BigDecimal obj) {
	  return this.toByteArray(obj);
  }
  
  @Override
  public BigDecimal fromByteBuffer(ByteBuffer byteBuffer) {
    if (byteBuffer == null) {
      return null;
    }
    int scale = byteBuffer.getInt();
    byte[] bibytes = new byte[byteBuffer.remaining()];
    byteBuffer.get(bibytes, 0, byteBuffer.remaining());
    BigInteger bi = new BigInteger(bibytes);
    // now we create an instance of BigDecimal with BigInteger and scale
    BigDecimal value = new BigDecimal(bi, scale);
    return value;
  }

  @Override
  public ByteBuffer toByteBuffer(BigDecimal obj) {
    if (obj == null) {
      return null;
    }
    return ByteBuffer.wrap(toByteArray(obj));
  }

  public byte[] toByteArray(BigDecimal bigDecimal) {
	  byte[] bigIntBytes = bigDecimal.unscaledValue().toByteArray();
	  byte[] scaleBytes = IntegerSerializer.get().toBytes(bigDecimal.scale());
	  byte[] bytes = new byte[scaleBytes.length + bigIntBytes.length];
	  System.arraycopy(scaleBytes, 0, bytes, 0, scaleBytes.length);
	  System.arraycopy(bigIntBytes, 0, bytes, scaleBytes.length, bigIntBytes.length);
	  return bytes;
  }
  
  @Override
  public ComparatorType getComparatorType() {
    return DECIMALTYPE;
  }

}
