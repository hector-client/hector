package me.prettyprint.hom;

import java.beans.PropertyDescriptor;

import me.prettyprint.hom.converters.Converter;
import me.prettyprint.hom.converters.DefaultConverter;


public class PropertyMappingDefinition {
  private PropertyDescriptor propDesc;
  private String colName;
  @SuppressWarnings("rawtypes")
  private Converter converter;
  private Class<?> collectionType;

  public Class<?> getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(Class<?> collectionType) {
    this.collectionType = collectionType;
  }

  public PropertyMappingDefinition(PropertyDescriptor propDesc, String colName, @SuppressWarnings("rawtypes") Class<? extends Converter> converter)
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

  @SuppressWarnings("rawtypes")
  public Converter getConverter() {
    return converter;
  }

  public boolean isCollectionType() {
    return null != collectionType;
  }

  public boolean isDefaultConverter() {
    return converter instanceof DefaultConverter;
  }
}

