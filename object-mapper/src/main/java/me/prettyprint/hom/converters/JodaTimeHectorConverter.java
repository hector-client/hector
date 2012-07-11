package me.prettyprint.hom.converters;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.hom.PropertyMappingDefinition;

import org.joda.time.DateTime;

public class JodaTimeHectorConverter implements Converter<DateTime> {

  @Override
  public DateTime convertCassTypeToObjType(PropertyMappingDefinition md, byte[] value) {
    return new DateTime(LongSerializer.get().fromBytes(value));
  }

  @Override
  public byte[] convertObjTypeToCassType(DateTime value) {
    return LongSerializer.get().toBytes(value.getMillis());
  }

}
