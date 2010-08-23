package me.prettyprint.cassandra.serializers;


/**
 * The BytesExtractor is a simple identity function. It supports the Extractor interface and
 * implements the fromBytes and toBytes as simple identity functions.
 *
 * @author Ran Tavory
 *
 */
public final class BytesSerializer extends AbstractSerializer<byte[]> {

  private static BytesSerializer instance = new BytesSerializer();

  public static BytesSerializer get() {
    return instance;
  }

  @Override
  public byte[] fromBytes(byte[] bytes) {
    return bytes;
  }

  @Override
  public byte[] toBytes(byte[] obj) {
    return obj;
  }
}
