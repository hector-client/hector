package me.prettyprint.hom;

import me.prettyprint.hom.converters.Converter;

public class ColorConverter implements Converter<Colors> {

  @Override
  public Colors convertCassTypeToObjType(PropertyMappingDefinition md, byte[] value) {
    return Colors.getInstance(new String(value));
  }

  @Override
  public byte[] convertObjTypeToCassType(Colors value) {
    return value.getName().getBytes();
  }

}
