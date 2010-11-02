package me.prettyprint.cassandra.serializers;

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
  public static <T> Serializer<T> getSerializer(Object value) {
    Serializer serializer = null;
    if (value == null) {
      serializer = BytesSerializer.get();
    } else if (value instanceof UUID) {
      serializer = UUIDSerializer.get();
    } else if (value instanceof String) {
      serializer = StringSerializer.get();
    } else if (value instanceof Long) {
      serializer = LongSerializer.get();
    } else if (value instanceof byte[]) {
      serializer = BytesSerializer.get();
    } else {
      serializer = ObjectSerializer.get();
    }
    // Add other serializers here

    return serializer;
  }
}
