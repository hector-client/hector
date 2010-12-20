package me.prettyprint.hom.converters;


/**
 * Interface defining a custom object mapper conversion. For instance, from an
 * enum to a string.
 * 
 * @param <T> Type of object to convert
 * 
 * @author Todd Burruss
 */
public interface Converter<T> {

  /**
   * Convert Cassandra byte[] to Java type.
   *
   * @param clazz
   * @param value
   * @return
   */
  T convertCassTypeToObjType(Class<T> clazz, byte[] value);

  /**
   * Convert Java type to byte[].
   *
   * @param value
   * @return
   */
  byte[] convertObjTypeToCassType(T value);
}
