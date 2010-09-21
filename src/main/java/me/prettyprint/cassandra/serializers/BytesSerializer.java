package me.prettyprint.cassandra.serializers;

import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Serializer;

/**
 * The BytesSerializer is a simple identity function. It supports the Serializer interface and
 * implements the fromBytes and toBytes as simple identity functions.
 *
 * @author Ran Tavory
 *
 */
public final class BytesSerializer implements Serializer<byte[]> {

  private static BytesSerializer instance = new BytesSerializer();

  public static BytesSerializer get() {
    return instance;
  }

  @Override
  public byte[] toBytes(byte[] obj) {
    return obj;
  }

  @Override
  public byte[] fromBytes(byte[] bytes) {
    return bytes;
  }

  @Override
  public List<byte[]> toBytesList(List<byte[]> list) {
    return list;
  }

  @Override
  public List<byte[]> fromBytesList(List<byte[]> list) {
    return list;
  }

  @Override
  public <V> Map<byte[], V> toBytesMap(Map<byte[], V> map) {
    return map;
  }

  @Override
  public <V> Map<byte[], V> fromBytesMap(Map<byte[], V> map) {
    return map;
  }

}
