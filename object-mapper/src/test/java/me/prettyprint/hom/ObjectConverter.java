package me.prettyprint.hom;

import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.hom.converters.Converter;

public class ObjectConverter implements Converter<Object> {

  private static final ObjectSerializer OBJ_SER = ObjectSerializer.get();
  
  @Override
  public Object convertCassTypeToObjType(PropertyMappingDefinition md, byte[] value) {
    return OBJ_SER.fromBytes(value);
  }

  @Override
  public byte[] convertObjTypeToCassType(Object value) {
    return OBJ_SER.toBytes(value);
  }
  
}
