package me.prettyprint.hom.cache;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.persistence.IdClass;
import javax.persistence.Table;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;

public class IdClassParserValidator implements ParserValidator {

  @Override
  public <T, I> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T, I> cfMapDef) {
    if (anno instanceof IdClass) {
      parseIdClassAnnotation(cacheMgr, (IdClass) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation "
          + anno.getClass().getSimpleName());
    }
  }

  @Override
  public <T, I> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    // nothing to do
  }

  private <T, I> void parseIdClassAnnotation(ClassCacheMgr cacheMgr, IdClass anno,
      CFMappingDef<T, I> cfMapDef) {
    Class<?> pkClazz = anno.value();

    verifyClassConformsToJpaSpec(pkClazz);
    Map<String, PropertyDescriptor> pdMap;
    try {
      pdMap = cacheMgr.getFieldPropertyDescriptorMap(pkClazz);
    } catch (IntrospectionException e) {
      throw new HectorObjectMapperException("exception while introspecting class, "
          + pkClazz.getName(), e);
    }
  }

  private void verifyClassConformsToJpaSpec(Class<?> pkClazz) {
    // TODO:BTB double check the JPA spec, section 2.4

    if (!(Serializable.class.isAssignableFrom(pkClazz))) {
      throw new HectorObjectMapperException("JPA requires that primary key class, "
          + pkClazz.getName() + ", must be Serializable");
    }
  }
}
