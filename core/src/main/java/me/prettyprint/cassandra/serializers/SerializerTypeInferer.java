package me.prettyprint.cassandra.serializers;

import java.io.Externalizable;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.beans.Composite;

/**
 * Utility class that infers the concrete Serializer needed to turn a value into
 * its binary representation
 * 
 * @author Bozhidar Bozhanov
 * 
 */
public class SerializerTypeInferer {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T> Serializer<T> getSerializer(Object value) {
    Serializer serializer = null;
    if (value == null) {
      serializer = ByteBufferSerializer.get();
    } else if (value instanceof BigInteger) {
      serializer = BigIntegerSerializer.get();
    } else if (value instanceof Boolean) {
      serializer = BooleanSerializer.get();
    } else if (value instanceof byte[]) {
      serializer = BytesArraySerializer.get();
    } else if (value instanceof ByteBuffer) {
      serializer = ByteBufferSerializer.get();
    } else if (value instanceof Character) {
      serializer = CharSerializer.get();
    } else if (value instanceof Composite) {
      serializer = CompositeSerializer.get();
    } else if (value instanceof DynamicComposite) {
      serializer = DynamicCompositeSerializer.get();
    } else if (value instanceof Date) {
      serializer = DateSerializer.get();
    } else if (value instanceof Double) {
      serializer = DoubleSerializer.get();
    } else if (value instanceof Float) {
      serializer = FloatSerializer.get();
    } else if (value instanceof Integer) {
      serializer = IntegerSerializer.get();
    } else if (value instanceof Long) {
      serializer = LongSerializer.get();
    } else if (value instanceof Short) {
      serializer = ShortSerializer.get();
    } else if (value instanceof String) {
      serializer = StringSerializer.get();
    } else if (value instanceof UUID) {
      serializer = UUIDSerializer.get();
    } else if (value instanceof Serializable) {
      serializer = ObjectSerializer.get();
    }
    // Add other serializers here

    return serializer;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T> Serializer<T> getSerializer(Class<?> valueClass) {
    Serializer serializer = null;
    if (valueClass == BigInteger.class) {
      serializer = BigIntegerSerializer.get();
    } else if (valueClass.equals(Boolean.class) || valueClass.equals(boolean.class)) {
      serializer = BooleanSerializer.get();
    } else if (valueClass.equals(byte[].class)) {
      serializer = BytesArraySerializer.get();
    } else if (valueClass.equals(ByteBuffer.class)) {
      serializer = ByteBufferSerializer.get();
    } else if (valueClass.equals(Character.class)) {
      serializer = CharSerializer.get();
    } else if (valueClass.equals(Composite.class)) {
      serializer = CompositeSerializer.get();
    } else if (valueClass.equals(DynamicComposite.class)) {
      serializer = DynamicCompositeSerializer.get();
    } else if (valueClass.equals(Date.class)) {
      serializer = DateSerializer.get();
    } else if (valueClass.equals(Double.class) || valueClass.equals(double.class)) {
      serializer = DoubleSerializer.get();
    } else if (valueClass.equals(Float.class) || valueClass.equals(float.class)) {
      serializer = FloatSerializer.get();
    } else if (valueClass.equals(Integer.class) || valueClass.equals(int.class)) {
      serializer = IntegerSerializer.get();
    } else if (valueClass.equals(Long.class) || valueClass.equals(long.class)) {
      serializer = LongSerializer.get();
    } else if (valueClass.equals(Short.class) || valueClass.equals(short.class)) {
      serializer = ShortSerializer.get();
    } else if (valueClass.equals(String.class)) {
      serializer = StringSerializer.get();
    } else if (valueClass.equals(UUID.class)) {
      serializer = UUIDSerializer.get();
    } else if (isSerializable(valueClass)) {
      serializer = ObjectSerializer.get();
    }
    // Add other serializers here

    return serializer;
  }

  public static boolean isSerializable(Class<?> clazz) {
    return isImplementedBy(clazz, Serializable.class) || isImplementedBy(clazz, Externalizable.class);
  }

  public static boolean isImplementedBy(Class<?> clazz, Class<?> target) {
    if (null == clazz || null == target) {
      return false;
    }

    Class<?>[] interArr = clazz.getInterfaces();
    if (null == interArr) {
      return false;
    }

    for (Class<?> interfa : interArr) {
      if (interfa.equals(target)) {
        return true;
      }
    }
    if(clazz.getSuperclass()!=null) {
      return isImplementedBy(clazz.getSuperclass(), target);
    }
    return false;
  }

}
