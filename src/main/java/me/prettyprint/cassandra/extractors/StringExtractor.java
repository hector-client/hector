package me.prettyprint.cassandra.extractors;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.prettyprint.cassandra.model.Extractor;

/**
 * A StringExtractor translates the byte[] to and from string using utf-8 encoding.
 * @author Ran Tavory
 *
 */
public final class StringExtractor implements Extractor<String> {

  private static final StringExtractor instance = new StringExtractor();

  public static StringExtractor get() {
    return instance;
  }

  public String fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return string(bytes);
  }

  public byte[] toBytes(String obj) {
    if (obj == null) {
      return null;
    }
    return bytes(obj);
  }

  public List<byte[]> toBytesList(List<String> list) {
    List<byte[]> bytesList = new ArrayList<byte[]>();
    for (String s : list) {
      bytesList.add(toBytes(s));
    }
    return bytesList;
  }

  public List<String> fromBytesList(List<byte[]> list) {
    List<String> strList = new ArrayList<String>();
    for (byte[] b : list) {
      strList.add(fromBytes(b));
    }
    return strList;
  }

  public <V> Map<byte[], V> toBytesMap(Map<String, V> map) {
    Map<byte[], V> bytesMap = new LinkedHashMap<byte[], V>();
    for (Entry<String, V> entry : map.entrySet()) {
      bytesMap.put(toBytes(entry.getKey()), entry.getValue());
    }
    return bytesMap;
  }

  public <V> Map<String, V> fromBytesMap(Map<byte[], V> map) {
    Map<String, V> strMap = new LinkedHashMap<String, V>();
    for (Entry<byte[], V> entry : map.entrySet()) {
      strMap.put(fromBytes(entry.getKey()), entry.getValue());
    }
    return strMap;
  }

}
