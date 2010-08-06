package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.extractors.StringExtractor;

/**
 * Extracts a type T from the given bytes, or vice a versa.
 * 
 * In cassandra column names and column values (and starting with 0.7.0 row keys) are all byte[].
 * To allow type safe conversion in java and keep all conversion code in one place we define the 
 * Extractor interface.
 * Implementors of the interface define type conversion according to their domains. A predefined 
 * set of common extractors can be found in the extractors package, for example 
 * {@link StringExtractor}.
 * 
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
