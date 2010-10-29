package me.prettyprint.cassandra.serializers;

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
  public byte[] toBytes(Double obj) {
    return LongSerializer.get().toBytes(Double.doubleToRawLongBits(obj));
  }

  @Override
  public Double fromBytes(byte[] bytes) {
    return Double.longBitsToDouble (LongSerializer.get().fromBytes(bytes));
  }

}

