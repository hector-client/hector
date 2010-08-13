package me.prettyprint.cassandra.extractors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.prettyprint.cassandra.model.Extractor;

public abstract class AbstractExtractor<T> implements Extractor<T> {

  public abstract byte[] toBytes(T obj);

  public abstract T fromBytes(byte[] bytes);

  public List<byte[]> toBytesList(List<T> list) {
    List<byte[]> bytesList = new ArrayList<byte[]>();
    for (T s : list) {
      bytesList.add(toBytes(s));
    }
    return bytesList;
  }

  public List<T> fromBytesList(List<byte[]> list) {
    List<T> objList = new ArrayList<T>();
    for (byte[] b : list) {
      objList.add(fromBytes(b));
    }
    return objList;
  }

  public <V> Map<byte[], V> toBytesMap(Map<T, V> map) {
    Map<byte[], V> bytesMap = new LinkedHashMap<byte[], V>();
    for (Entry<T, V> entry : map.entrySet()) {
      bytesMap.put(toBytes(entry.getKey()), entry.getValue());
    }
    return bytesMap;
  }

  public <V> Map<T, V> fromBytesMap(Map<byte[], V> map) {
    Map<T, V> objMap = new LinkedHashMap<T, V>();
    for (Entry<byte[], V> entry : map.entrySet()) {
      objMap.put(fromBytes(entry.getKey()), entry.getValue());
    }
    return objMap;
  }

}
