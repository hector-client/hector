package me.prettyprint.hom.converters;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.HectorObjectMapper;


/**
 * Default converter used when none specified in {@link Column} annotation. Uses
 * Java reflection to determine the Java type to use.
 * 
 * @author Todd Burruss
 */
public class DefaultConverter implements Converter<Object> {

  @Override
  public Object convertCassTypeToObjType(Class<Object> clazz, byte[] value) {
    Serializer<?> s = (Serializer<?>) HectorObjectMapper.determineSerializer(clazz);
    return s.fromBytes(value);
  }

  @Override
  public byte[] convertObjTypeToCassType(Object value) {
    @SuppressWarnings("unchecked")
    Serializer<Object> s = (Serializer<Object>) HectorObjectMapper.determineSerializer(value.getClass());
    return s.toBytes(value);
  }

}
