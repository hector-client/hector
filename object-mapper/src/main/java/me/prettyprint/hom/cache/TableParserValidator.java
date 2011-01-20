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
  public <T, I> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T, I> cfMapDef) {
    if (anno instanceof Table) {
      parseTableAnnotation(cacheMgr, (Table) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation "
          + anno.getClass().getSimpleName());
    }
  }

  private <T, I> void parseTableAnnotation(ClassCacheMgr cacheMgr, Table anno,
      CFMappingDef<T, I> cfMapDef) {
    CFMappingDef<?, ?> tmpDef;

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
  public <T, I> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    if (cfMapDef.isStandaloneClass()) {
      validateStandaloneClass(cacheMgr, cfMapDef);
    } else if (cfMapDef.isBaseInheritanceClass()) {
      validateBaseClass(cacheMgr, cfMapDef);
    } else if (cfMapDef.isDerivedClassInheritance()) {
      validateDerivedClass(cacheMgr, cfMapDef);
    }
  }

  private <T, I> void validateStandaloneClass(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    CFMappingDef<? super T, I> cfSuperDef;

    if (null == cfMapDef.getEffectiveColFamName()) {
      throw new HectorObjectMapperException("Class, " + cfMapDef.getRealClass().getName()
          + ", is missing @" + Table.class.getSimpleName());
    } else if (null != (cfSuperDef = cacheMgr.findBaseClassViaMappings(cfMapDef))) {
      throw new HectorObjectMapperException("@" + Table.class.getSimpleName()
          + " can only be used once per hierarchy and has been specified in class "
          + cfSuperDef.getRealClass().getName() + " and " + cfMapDef.getRealClass().getName()
          + " - quitting");
    }
  }

  private <T, I> void validateBaseClass(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    if (null == cfMapDef.getColFamName()) {
      throw new HectorObjectMapperException(cfMapDef.getRealClass()
          + " is recognized as a base class, but doesn't specify @" + Table.class.getSimpleName()
          + " - quitting");
    }
  }

  private <T, I> void validateDerivedClass(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    findAndSetBaseClassViaMappings(cacheMgr, cfMapDef);
    if (null == cfMapDef.getCfBaseMapDef()) {
      throw new HectorObjectMapperException("@" + Table.class.getSimpleName() + " used by class, "
          + cfMapDef.getRealClass().getName() + ", "
          + " has already been specified by base class, "
          + cfMapDef.getCfBaseMapDef().getEffectiveClass().getName());
    }

    // save this class in the base class for reference during loading
    cfMapDef.getCfBaseMapDef().addDerivedClassMap(cfMapDef);
  }

  private <T, I> void findAndSetBaseClassViaMappings(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    CFMappingDef<? super T, I> cfBaseMapDef = cacheMgr.findBaseClassViaMappings(cfMapDef);

    if (null == cfBaseMapDef) {
      throw new HectorObjectMapperException(cfMapDef.getRealClass()
          + " is a derived class entity but @" + Table.class.getSimpleName()
          + " is not specified in its super classes - quitting");
    }

    cfMapDef.setCfBaseMapDef(cfBaseMapDef);

    // Class<T> derClazz = cfMapDef.getEffectiveClass();
    // Class<? super T> superClazz = (Class<T>) derClazz.getSuperclass();
    // if (null == superClazz) {
    // return;
    // }
    //
    // CFMappingDef<T, I> superCfMapDef = getCfMapDef(superClazz, false);
    // if (null == superCfMapDef) {
    // return;
    // }
    //
    // while (null != superCfMapDef && null ==
    // superCfMapDef.getInheritanceType()) {
    // superCfMapDef = (CFMappingDef<T, I>) superCfMapDef.getCfBaseMapDef();
    // }
    //
    // if (null == superCfMapDef) {
    // throw new HectorObjectMapperException(derClazz.getName() +
    // " has mapped super class, but "
    // + superClazz.getName() + " isn't marked with @" +
    // Inheritance.class.getSimpleName()
    // + " - cannot continue");
    // }

  }
}
