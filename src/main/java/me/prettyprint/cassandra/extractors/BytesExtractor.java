package me.prettyprint.cassandra.extractors;

import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.Extractor;

/**
 * The BytesExtractor is a simple identity function. It supports the Extractor interface and
 * implements the fromBytes and toBytes as simple identity functions.
 *
 * @author Ran Tavory
 *
 */
public final class BytesExtractor implements Extractor<byte[]> {

  private static BytesExtractor instance = new BytesExtractor();

  public static BytesExtractor get() {
    return instance;
  }

  public byte[] toBytes(byte[] obj) {
    return obj;
  }

  public byte[] fromBytes(byte[] bytes) {
    return bytes;
  }

  public List<byte[]> toBytesList(List<byte[]> list) {
    return list;
  }

  public List<byte[]> fromBytesList(List<byte[]> list) {
    return list;
  }

  public <V> Map<byte[], V> toBytesMap(Map<byte[], V> map) {
    return map;
  }

  public <V> Map<byte[], V> fromBytesMap(Map<byte[], V> map) {
    return map;
  }

}
