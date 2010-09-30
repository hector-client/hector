package me.prettyprint.cassandra.serializers;


/**
 * Converts bytes to Boolean and vice versa
 *
 * @author Bozhidar Bozhanov
 *
 */
public final class BooleanSerializer extends AbstractSerializer<Boolean> {

  private static final BooleanSerializer instance = new BooleanSerializer();

  public static BooleanSerializer get() {
    return instance;
  }

  @Override
  public byte[] toBytes(Boolean obj) {
    if (obj == null) {
      return null;
    }
    boolean bool = obj;
    byte[] b = new byte[1];
    b[0] = bool ? (byte) 1 : (byte) 0;

    return b;
  }

  @Override
  public Boolean fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return bytes[0] == (byte) 1;
  }

}
