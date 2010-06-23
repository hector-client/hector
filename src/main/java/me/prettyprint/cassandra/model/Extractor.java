package me.prettyprint.cassandra.model;

/**
 * Extracts a type T from the given bytes, or vice a versa.
 * Very useful in conjunction with cassandra column names and values which are encoded in byte[].
 * The extractor extracts real java object from the bytes, one such example is the StringExtractor.
 * @author Ran Tavory 
 *
 * @param <T> The type to which data extraction should work.
 */
public interface Extractor<T> {

  /**
   * Extract bytes from the obj of type T
   * @param obj
   * @return
   */
  public byte[] toBytes(T obj);
  
  /**
   * Extract an object of type T from the bytes.
   * @param bytes
   * @return
   */
  public T fromBytes(byte[] bytes);
}
