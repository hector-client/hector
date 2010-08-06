package me.prettyprint.cassandra.extractors;

import me.prettyprint.cassandra.model.Extractor;

/**
 * The BytesExtractor is a simple identity function. It supports the Extractor interface and 
 * implements the fromBytes and toBytes as simple identity functions.
 * 
 * @author Ran Tavory 
 *
 */
public class BytesExtractor implements Extractor<byte[]> {

  private static BytesExtractor instance = new BytesExtractor();

  public static BytesExtractor get() {
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
