package me.prettyprint.cassandra.serializers;

import me.prettyprint.hector.api.Serializer;

/**
 * A serializer that dynamically delegates to a proper serializer based on the
 * value passed
 *
 * @author Bozhidar Bozhanov
 *
 * @param <T> type
 */
public class TypeInferringSerializer<T> extends AbstractSerializer<T> implements Serializer<T> {

  @SuppressWarnings("rawtypes")
  private static final TypeInferringSerializer instance = new TypeInferringSerializer();

  @SuppressWarnings("unchecked")
  public static <T> TypeInferringSerializer<T> get() {
    return instance;
  }

  @Override
  public byte[] toBytes(T obj) {
    return SerializerTypeInferer.getSerializer(obj).toBytes(obj);
  }

  @Override
  public T fromBytes(byte[] bytes) {
    throw new IllegalStateException(
        "The type inferring serializer can only be used for data going to the database, and not data coming from the database");
  }
}
