package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.FLOATTYPE;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.ddl.ComparatorType;

/**
 * Uses IntSerializer via translating Float objects to and from raw long bytes form.
 * 
 * @author Todd Nine
 */
public class FloatSerializer extends AbstractSerializer<Float> {

  private static final FloatSerializer instance = new FloatSerializer();

  public static FloatSerializer get() {
    return instance;
  }
  
  @Override
  public ByteBuffer toByteBuffer(Float obj) {
    return IntegerSerializer.get().toByteBuffer(Float.floatToRawIntBits(obj));
  }

  @Override
  public Float fromByteBuffer(ByteBuffer bytes) {
    return Float.intBitsToFloat(IntegerSerializer.get().fromByteBuffer(bytes));
  }

  @Override
      public ComparatorType getComparatorType() {
      return FLOATTYPE;
  }

}
