package me.prettyprint.hom.converters;

import me.prettyprint.cassandra.serializers.LongSerializer;

import org.joda.time.DateTime;

public class JodaTimeHectorConverter implements Converter {

  @Override
  public DateTime convertCassTypeToObjType(Class<?> clazz, byte[] value) {
    return new DateTime(LongSerializer.get().fromBytes(value));
  }

  @Override
  public byte[] convertObjTypeToCassType(Object value) {
    return LongSerializer.get().toBytes(((DateTime)value).getMillis());
  }

}
