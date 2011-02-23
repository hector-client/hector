package me.prettyprint.cassandra.serializers;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * A StringSerializer translates the byte[] to and from string using utf-8
 * encoding.
 * 
 * @author Ran Tavory
 * 
 */
public final class StringSerializer extends AbstractSerializer<String> {

  private static final String UTF_8 = "UTF-8";
  private static final StringSerializer instance = new StringSerializer();
  private static final Charset charset = Charset.defaultCharset();

  public static StringSerializer get() {
    return instance;
  }

  @Override
  public ByteBuffer toByteBuffer(String obj) {
    if (obj == null) {
      return null;
    }
    try {
      return ByteBuffer.wrap(obj.getBytes(charset.name()));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String fromByteBuffer(ByteBuffer byteBuffer) {
    if (byteBuffer == null) {
      return null;
    }    
    return charset.decode(byteBuffer.duplicate()).toString();    
  }
}
