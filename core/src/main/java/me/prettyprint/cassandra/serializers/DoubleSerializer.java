package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.serializers.AbstractSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;

/**
 * Uses LongSerializer via translating Doubles to and from raw long bytes form.
 * 
 * @author Yuri Finkelstein 
 */
public class DoubleSerializer extends AbstractSerializer<Double> {

  private static final DoubleSerializer instance = new DoubleSerializer();

  public static DoubleSerializer get() {
    return instance;
  }
  
  @Override
  public ByteBuffer toByteBuffer(Double obj) {
    if (obj == null) {
      return null;
    }
    return LongSerializer.get().toByteBuffer(Double.doubleToRawLongBits(obj));
  }

  @Override
  public Double fromByteBuffer(ByteBuffer bytes) {
    Long l = LongSerializer.get().fromByteBuffer(bytes);
    return l == null ? null : Double.longBitsToDouble (l);
  }

}

