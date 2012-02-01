package me.prettyprint.hom.converters;

import java.math.BigInteger;

import me.prettyprint.cassandra.serializers.BigIntegerSerializer;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.cache.HectorObjectMapperException;

public class VariableIntegerConverter implements Converter<Object> {

  @Override
  public Object convertCassTypeToObjType(PropertyMappingDefinition md, byte[] value) {
    BigInteger bigInt = new BigInteger(value);

    // determine our target integer type and then go from there on the
    // conversion method
    Class<?> targetClass = md.getPropDesc().getPropertyType();
    if (targetClass.equals(Integer.class) || targetClass.equals(int.class)) {
      return Integer.valueOf(bigInt.intValue());
    } else if (targetClass.equals(Long.class) || targetClass.equals(long.class)) {
      return Long.valueOf(bigInt.longValue());
    } else if (targetClass.equals(Short.class) || targetClass.equals(short.class)) {
      return Short.valueOf(bigInt.shortValue());
    } else if (targetClass.equals(Byte.class) || targetClass.equals(byte.class)) {
      return Byte.valueOf(bigInt.byteValue());
    } else if (targetClass.equals(BigInteger.class)) {
      return bigInt;
    } else {
      throw new HectorObjectMapperException("Column, " + md.getColName()
          + ", cannot be converted using " + getClass().getSimpleName()
          + " because POJO property, " + md.getPropDesc().getName() + ", of type "
          + md.getPropDesc().getPropertyType().getName()
          + " is not an integer type (in a mathematical context)");
    }
  }

  @Override
  public byte[] convertObjTypeToCassType(Object value) {
    BigInteger bigInt;
    if (value instanceof Byte) {
      bigInt = BigInteger.valueOf(((Byte) value).longValue());
    } else if (value instanceof Short) {
      bigInt = BigInteger.valueOf(((Short) value).longValue());
    } else if (value instanceof Integer) {
      bigInt = BigInteger.valueOf(((Integer) value).longValue());
    } else if (value instanceof Long) {
      bigInt = BigInteger.valueOf((Long) value);
    } else if (value instanceof BigInteger) {
      bigInt = (BigInteger) value;
    } else {
      throw new HectorObjectMapperException(
          "value of type "
              + value.getClass().getName()
              + " is not an integer type (in a mathematical context) and cannot be converted to a variable integer type");
    }

    return BigIntegerSerializer.get().toBytes(bigInt);
  }
}
