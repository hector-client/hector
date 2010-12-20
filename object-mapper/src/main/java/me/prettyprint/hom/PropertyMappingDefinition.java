package me.prettyprint.hom;

import java.beans.PropertyDescriptor;

import me.prettyprint.hom.converters.Converter;


public class PropertyMappingDefinition<T> {
  private PropertyDescriptor propDesc;
  private String colName;
  private Converter<T> converter;

  public PropertyMappingDefinition(PropertyDescriptor propDesc, String colName, Class<Converter<T>> converter)
  throws InstantiationException, IllegalAccessException {
    this.propDesc = propDesc;
    this.colName = colName;
    this.converter = (Converter<T>) converter.newInstance();
  }

  @Override
  public String toString() {
    return "PropertyMappingDefinition [colName=" + colName + ", converter=" + converter + ", propDesc=" + propDesc
    + "]";
  }

  public PropertyDescriptor getPropDesc() {
    return propDesc;
  }

  public String getColName() {
    return colName;
  }

  public Converter<T> getConverter() {
    return converter;
  }
}

