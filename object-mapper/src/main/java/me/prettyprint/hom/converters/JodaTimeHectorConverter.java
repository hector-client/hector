package me.prettyprint.hom.converters;

import me.prettyprint.cassandra.serializers.LongSerializer;

import org.joda.time.DateTime;

public class JodaTimeHectorConverter implements Converter<DateTime> {

  @Override
  public DateTime convertCassTypeToObjType(Class<DateTime> clazz, byte[] value) {
    return new DateTime(LongSerializer.get().fromBytes(value));
  }

  @Override
  public byte[] convertObjTypeToCassType(DateTime value) {
    return LongSerializer.get().toBytes(value.getMillis());
  }

}
