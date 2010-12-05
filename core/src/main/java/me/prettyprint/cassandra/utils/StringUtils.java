package me.prettyprint.cassandra.utils;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encoding and decoding utilities.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public final class StringUtils {

  private static final Logger log = LoggerFactory.getLogger(StringUtils.class);

  public static final String ENCODING = "utf-8";

  /**
   * Gets UTF-8 bytes from the string.
   *
   * @param s
   * @return
   */
  public static byte[] bytes(String s) {
    try {
      return s.getBytes(ENCODING);
    } catch (UnsupportedEncodingException e) {
      log.error("UnsupportedEncodingException ", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Utility for converting bytes to strings. UTF-8 is assumed.
   * @param bytes
   * @return
   */
  public static String string(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    try {
      return new String(bytes, ENCODING);
    } catch (UnsupportedEncodingException e) {
      log.error("UnsupportedEncodingException ", e);
      throw new RuntimeException(e);
    }
  }

}
