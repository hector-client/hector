package me.prettyprint.hom.cache;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.prettyprint.hom.CFMappingDef;

public interface ColumnParserValidator {

  <T> void parse(Field field, Annotation anno, PropertyDescriptor pd, CFMappingDef<T> cfMapDef);

//  <T> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef);
  
}
