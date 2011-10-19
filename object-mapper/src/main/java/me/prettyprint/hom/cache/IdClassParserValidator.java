package me.prettyprint.hom.cache;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.IdClass;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.KeyDefinition;

public class IdClassParserValidator implements ParserValidator {

  @Override
  public <T> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T> cfMapDef) {
    if (anno instanceof IdClass) {
      parseIdClassAnnotation(cacheMgr, (IdClass) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation, "
          + anno.getClass().getSimpleName());
    }
  }

  @Override
  public <T> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    KeyDefinition keyDef = cfMapDef.getKeyDef();
    
    if ( null == keyDef.getPkClazz() ) {
      return;
    }
    
    Map<String, PropertyDescriptor> pdMap;
    try {
      pdMap = cacheMgr.getFieldPropertyDescriptorMap(keyDef.getPkClazz());
    } catch (IntrospectionException e) {
      throw new HectorObjectMapperException("exception while introspecting class, "
          + keyDef.getPkClazz().getName(), e);
    }
    
    if ( keyDef.getIdPropertyMap().size() != pdMap.size() ) {
      throw new HectorObjectMapperException("Each field in the primary key class, " + keyDef.getPkClazz().getName()
          + ", must have a corresponding property in the entity, " + cfMapDef.getRealClass().getName() + ", annotated with @" + Id.class.getSimpleName() );
    }
    
    for ( String idFieldName : pdMap.keySet() ) {
      if ( !keyDef.getIdPropertyMap().containsKey(idFieldName)) {
        throw new HectorObjectMapperException("Each field in the primary key class, " + keyDef.getPkClazz().getName()
            + ", must have a corresponding property in the entity, " + cfMapDef.getRealClass().getName() + ", annotated with @" + Id.class.getSimpleName() + " : missing ID field, " + idFieldName);
      }
    }
  }

  private <T> void parseIdClassAnnotation(ClassCacheMgr cacheMgr, IdClass anno,
      CFMappingDef<T> cfMapDef) {
    Class<?> pkClazz = anno.value();

    verifyClassConformsToJpaSpec(pkClazz);
    cfMapDef.getKeyDef().setPkClass(pkClazz);
  }

  private void verifyClassConformsToJpaSpec(Class<?> pkClazz) {
    // TODO:BTB double check the JPA spec, section 2.4

    if (!(Serializable.class.isAssignableFrom(pkClazz))) {
      throw new HectorObjectMapperException("JPA requires that primary key class, "
          + pkClazz.getName() + ", must be Serializable");
    }
  }
}
