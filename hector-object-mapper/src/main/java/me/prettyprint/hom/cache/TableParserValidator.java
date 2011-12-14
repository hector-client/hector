package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;

import javax.persistence.Inheritance;
import javax.persistence.Table;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;

/**
 * Parse, validate, and set defaults if needed for Inheritance functionality.
 * 
 * @author bburruss
 */
public class TableParserValidator implements ParserValidator {

  @Override
  public <T> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T> cfMapDef) {
    if (anno instanceof Table) {
      parseTableAnnotation(cacheMgr, (Table) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation, "
          + anno.getClass().getSimpleName());
    }
  }

  private <T> void parseTableAnnotation(ClassCacheMgr cacheMgr, Table anno, CFMappingDef<T> cfMapDef) {
    CFMappingDef<?> tmpDef;

    // column family can only be mapped to one class (base class)
    if (null != (tmpDef = cacheMgr.getCfMapDef(anno.name(), false))) {
      throw new HectorObjectMapperException(
          "classes, "
              + cfMapDef.getEffectiveClass().getName()
              + " and "
              + tmpDef.getEffectiveClass().getName()
              + ", are both mapped to ColumnFamily, "
              + tmpDef.getEffectiveColFamName()
              + ".  Can only have one Class/ColumnFamily mapping - if multiple classes can be derived from a single ColumnFamily, use @"
              + Inheritance.class.getSimpleName());
    }
    cfMapDef.setColFamName(anno.name());
  }

  @Override
  public <T> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    if (cfMapDef.isBaseEntity()) {
      validateBaseEntityClass(cacheMgr, cfMapDef);
    } else if ( cfMapDef.isDerivedEntity() ) {
      validateDerivedEntityClass(cacheMgr, cfMapDef);
      if (cfMapDef.isPersistableDerivedEntity()) {
        validatePersistableDerivedEntityClass(cacheMgr, cfMapDef);
      }
    }

    if (cfMapDef.isPersistableEntity()) {
      validatePersistableEntityClass(cacheMgr, cfMapDef);
    }
  }

  private <T> void validatePersistableEntityClass(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
//    CFMappingDef<? super T> cfSuperDef;

    if (null == cfMapDef.getEffectiveColFamName()) {
      throw new HectorObjectMapperException("Class, " + cfMapDef.getRealClass().getName()
          + ", is missing @" + Table.class.getSimpleName());
    }
//    else if (null != (cfSuperDef = cacheMgr.findBaseClassViaMappings(cfMapDef))) {
//      throw new HectorObjectMapperException("@" + Table.class.getSimpleName()
//          + " can only be used once per hierarchy and has been specified in class "
//          + cfSuperDef.getRealClass().getName() + " and " + cfMapDef.getRealClass().getName()
//          + " - quitting");
//    }
  }

  private <T> void validateBaseEntityClass(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    if (null == cfMapDef.getColFamName()) {
      throw new HectorObjectMapperException(cfMapDef.getRealClass()
          + " is recognized as a base class, but doesn't specify @" + Table.class.getSimpleName()
          + " - quitting");
    }
  }

  private <T> void validatePersistableDerivedEntityClass(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    // save this class in the base class for reference during loading
    cfMapDef.getCfBaseMapDef().addDerivedClassMap(cfMapDef);
  }

  private <T> void validateDerivedEntityClass(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    findAndSetBaseClassViaMappings(cacheMgr, cfMapDef);
    if (null == cfMapDef.getCfBaseMapDef()) {
      throw new HectorObjectMapperException("@" + Table.class.getSimpleName() + " used by class, "
          + cfMapDef.getRealClass().getName() + ", "
          + " has already been specified by base class, "
          + cfMapDef.getCfBaseMapDef().getEffectiveClass().getName());
    }
  }

  private <T> void findAndSetBaseClassViaMappings(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    CFMappingDef<? super T> cfBaseMapDef = cacheMgr.findBaseClassViaMappings(cfMapDef);

    if (null == cfBaseMapDef) {
      throw new HectorObjectMapperException(cfMapDef.getRealClass()
          + " is a derived class entity but @" + Table.class.getSimpleName()
          + " is not specified in its super classes - quitting");
    }

    cfMapDef.setCfBaseMapDef(cfBaseMapDef);
  }
}
