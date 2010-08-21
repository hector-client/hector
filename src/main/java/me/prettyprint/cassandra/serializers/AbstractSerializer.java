package me.prettyprint.cassandra.serializers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.prettyprint.cassandra.model.Serializer;

public abstract class AbstractSerializer<T> implements Serializer<T> {

  @Override
  public abstract byte[] toBytes(T obj);

  @Override
  public abstract T fromBytes(byte[] bytes);

  @Override
  public List<byte[]> toBytesList(List<T> list) {
    List<byte[]> bytesList = new ArrayList<byte[]>();
    for (T s : list) {
      bytesList.add(toBytes(s));
    }
    return bytesList;
  }

  @Override
  public List<T> fromBytesList(List<byte[]> list) {
    List<T> objList = new ArrayList<T>();
    for (byte[] b : list) {
      objList.add(fromBytes(b));
    }
    return objList;
  }

  @Override
  public <V> Map<byte[], V> toBytesMap(Map<T, V> map) {
    Map<byte[], V> bytesMap = new LinkedHashMap<byte[], V>();
    for (Entry<T, V> entry : map.entrySet()) {
      bytesMap.put(toBytes(entry.getKey()), entry.getValue());
    }
    return bytesMap;
  }

  @Override
  public <V> Map<T, V> fromBytesMap(Map<byte[], V> map) {
    Map<T, V> objMap = new LinkedHashMap<T, V>();
    for (Entry<byte[], V> entry : map.entrySet()) {
      objMap.put(fromBytes(entry.getKey()), entry.getValue());
    }
    return objMap;
  }

}
