package me.prettyprint.cassandra.serializers;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;
import me.prettyprint.cassandra.model.Serializer;

/**
 * A StringSerializer translates the byte[] to and from string using utf-8 encoding.
 * @author Ran Tavory
 *
 */
public final class StringSerializer implements Serializer<String> {

  private static final StringSerializer instance = new StringSerializer();

  public static StringSerializer get() {
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
