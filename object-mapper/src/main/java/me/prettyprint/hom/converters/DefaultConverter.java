package me.prettyprint.hom.converters;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.HectorObjectMapper;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.annotations.Column;


/**
 * Default converter used when none specified in {@link Column} annotation. Uses
 * Java reflection to determine the Java type to use.
 * 
 * @author Todd Burruss
 */
public class DefaultConverter implements Converter<Object> {

  @Override
  public Object convertCassTypeToObjType(PropertyMappingDefinition md, byte[] value) {
    Serializer<?> s = HectorObjectMapper.determineSerializer(md.getPropDesc().getPropertyType());
    return s.fromBytes(value);
  }

  @Override
  public byte[] convertObjTypeToCassType(Object value) {
    @SuppressWarnings("unchecked")
    Serializer<Object> s = (Serializer<Object>) HectorObjectMapper.determineSerializer(value.getClass());
    return s.toBytes(value);
  }

}
