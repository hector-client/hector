package me.prettyprint.hom;

import java.beans.PropertyDescriptor;

import me.prettyprint.hom.converters.Converter;


public class PropertyMappingDefinition {
  private PropertyDescriptor propDesc;
  private String colName;
  private Converter converter;

  public PropertyMappingDefinition(PropertyDescriptor propDesc, String colName, Class<? extends Converter> converter)
  throws InstantiationException, IllegalAccessException {
    this.propDesc = propDesc;
    this.colName = colName;
    this.converter = converter.newInstance();
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

  public Converter getConverter() {
    return converter;
  }
}

