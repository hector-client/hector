package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.StringUtils.*;

public class StringExtractor implements Extractor<String> {

  private static StringExtractor instance = new StringExtractor();

  public static StringExtractor get() {
    return instance;
  }

  @Override
  public String fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return string(bytes);
  }

  @Override
  public byte[] toBytes(String obj) {
    if (obj == null) {
      return null;
    }
    return bytes(obj);
  }

}
