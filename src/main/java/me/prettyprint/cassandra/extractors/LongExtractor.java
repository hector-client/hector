package me.prettyprint.cassandra.extractors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.prettyprint.cassandra.model.Extractor;

/**
 * Converts bytes to Long and vise a versa
 *
 * @author Ran Tavory
 *
 */
public final class LongExtractor implements Extractor<Long> {

  private static final LongExtractor instance = new LongExtractor();

  public static LongExtractor get() {
    return instance;
  }

  public byte[] toBytes(Long obj) {
    if (obj == null) {
      return null;
    }
    long l = obj;
    int size = 8;
    byte[] b = new byte[size];
    for (int i = 0; i < size; ++i) {
      b[i] = (byte) (l >> (size - i - 1 << 3));
    }
    return b;
  }

  public Long fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    long l = 0;
    int size = bytes.length;
    for (int i = 0; i < size; ++i) {
      l |= ((long) bytes[i] & 0xff) << (size - i - 1 << 3);
    }
    return l;
  }

  public List<byte[]> toBytesList(List<Long> list) {
    List<byte[]> bytesList = new ArrayList<byte[]>();
    for (Long s : list) {
      bytesList.add(toBytes(s));
    }
    return bytesList;
  }

  public List<Long> fromBytesList(List<byte[]> list) {
    List<Long> longList = new ArrayList<Long>();
    for (byte[] b : list) {
      longList.add(fromBytes(b));
    }
    return longList;
  }

  public <V> Map<byte[], V> toBytesMap(Map<Long, V> map) {
    Map<byte[], V> bytesMap = new LinkedHashMap<byte[], V>();
    for (Entry<Long, V> entry : map.entrySet()) {
      bytesMap.put(toBytes(entry.getKey()), entry.getValue());
    }
    return bytesMap;
  }

  public <V> Map<Long, V> fromBytesMap(Map<byte[], V> map) {
    Map<Long, V> longMap = new LinkedHashMap<Long, V>();
    for (Entry<byte[], V> entry : map.entrySet()) {
      longMap.put(fromBytes(entry.getKey()), entry.getValue());
    }
    return longMap;
  }



}
