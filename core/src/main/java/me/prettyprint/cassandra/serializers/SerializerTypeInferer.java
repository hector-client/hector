package me.prettyprint.cassandra.serializers;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import me.prettyprint.hector.api.Serializer;

/**
 * Utility class that infers the concrete Serializer needed to turn a value into
 * its binary representation
 * 
 * @author Bozhidar Bozhanov
 * 
 */
public class SerializerTypeInferer {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T> Serializer<T> getSerializer(T value) {
    Serializer serializer = null;
    if (value == null) {
      serializer = ByteBufferSerializer.get();
    } else if (value instanceof UUID) {
      serializer = UUIDSerializer.get();
    } else if (value instanceof String) {
      serializer = StringSerializer.get();
    } else if (value instanceof Long) {
      serializer = LongSerializer.get();
    } else if (value instanceof Integer) {
      serializer = IntegerSerializer.get();
    } else if (value instanceof BigInteger) {
      serializer = BigIntegerSerializer.get();
    } else if (value instanceof Boolean) {
      serializer = BooleanSerializer.get();
    } else if (value instanceof byte[]) {
      serializer = BytesArraySerializer.get();
    } else if (value instanceof ByteBuffer ) {
      serializer = ByteBufferSerializer.get();
    } else if (value instanceof Date) {
      serializer = DateSerializer.get();
    } else {
      serializer = ObjectSerializer.get();
    }
    // Add other serializers here

    return serializer;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T> Serializer<T> getSerializer(Class<?> valueClass) {
    Serializer serializer = null;
    if (valueClass.equals(UUID.class)) {
      serializer = UUIDSerializer.get();
    } else if (valueClass.equals(String.class)) {
      serializer = StringSerializer.get();
    } else if (valueClass.equals(Long.class) || valueClass.equals(long.class)) {
      serializer = LongSerializer.get();
    } else if (valueClass.equals(Integer.class) || valueClass.equals(int.class)) {
      serializer = IntegerSerializer.get();
    } else if (valueClass.equals(Boolean.class)
        || valueClass.equals(boolean.class)) {
      serializer = BooleanSerializer.get();
    } else if (valueClass.equals(byte[].class)) {
      serializer = ByteBufferSerializer.get();
    } else if (valueClass.equals(ByteBuffer.class)) {
      serializer = ByteBufferSerializer.get();
    } else if (valueClass.equals(Date.class)) {
      serializer = DateSerializer.get();
    } else {
      serializer = ObjectSerializer.get();
    }
    // Add other serializers here

    return serializer;
  }
}
