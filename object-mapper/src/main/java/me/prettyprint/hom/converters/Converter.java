package me.prettyprint.hom.converters;


/**
 * Interface defining a custom object mapper conversion. For instance, from an
 * enum to a string.
 * 
 * @author Todd Burruss
 */
public interface Converter {

  /**
   * Convert Cassandra byte[] to Java type.
   *
   * @param clazz
   * @param value
   * @return Type of object
   */
  abstract Object convertCassTypeToObjType(Class<?> clazz, byte[] value);

  /**
   * Convert Java type to byte[].
   *
   * @param value
   * @return Type of object converted to byte[]
   */
  abstract byte[] convertObjTypeToCassType(Object value);
}
