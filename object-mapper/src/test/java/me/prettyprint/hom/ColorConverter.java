package me.prettyprint.hom;

import me.prettyprint.hom.converters.Converter;

public class ColorConverter implements Converter {

  @Override
  public Colors convertCassTypeToObjType(Class<?> clazz, byte[] value) {
    return Colors.getInstance(new String(value));
  }

  @Override
  public byte[] convertObjTypeToCassType(Object value) {
    return ((Colors)value).getName().getBytes();
  }

}
