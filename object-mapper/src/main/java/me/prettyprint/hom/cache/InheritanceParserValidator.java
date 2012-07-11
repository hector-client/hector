package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;

/**
 * Parse, validate, and set defaults if needed for Inheritance functionality.
 * 
 * @author bburruss
 */
public class InheritanceParserValidator implements ParserValidator {

  @Override
  public <T> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T> cfMapDef) {
    if (anno instanceof Inheritance) {
      parseInheritanceAnnotation((Inheritance) anno, cfMapDef);
    } else if (anno instanceof DiscriminatorColumn) {
      parseDiscriminatorColumnAnnotation((DiscriminatorColumn) anno, cfMapDef);
    } else if (anno instanceof DiscriminatorValue) {
      parseDiscriminatorValueAnnotation((DiscriminatorValue) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation, "
          + anno.getClass().getSimpleName());
    }
  }

  private <T> void parseInheritanceAnnotation(Inheritance anno, CFMappingDef<T> cfMapDef) {
    if (InheritanceType.SINGLE_TABLE == anno.strategy()) {
      cfMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);
    } else {
      throw new RuntimeException("Hector object mapper only supports "
          + InheritanceType.SINGLE_TABLE + " inheritance at the moment");
    }
  }

  private <T> void parseDiscriminatorColumnAnnotation(DiscriminatorColumn anno,
      CFMappingDef<T> cfMapDef) {
    cfMapDef.setDiscColumn(anno.name());
    cfMapDef.setDiscType(anno.discriminatorType());
  }

  private <T> void parseDiscriminatorValueAnnotation(DiscriminatorValue anno,
      CFMappingDef<T> cfMapDef) {
    cfMapDef.setDiscValue(anno.value());
  }

  @Override
  public <T> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
    if (cfMapDef.isBaseEntity()) {
      validateBaseClassInheritance(cfMapDef);
    } else if (cfMapDef.isPersistableDerivedEntity()) {
      validateDerivedClassInheritance(cfMapDef);
    } else if (!cfMapDef.isNonPersistableDerivedEntity()) {
      if (null != cacheMgr.findBaseClassViaMappings(cfMapDef))
        throw new HectorObjectMapperException("@" + Inheritance.class.getSimpleName()
            + " found in class hierarchy, but no @" + DiscriminatorValue.class.getSimpleName()
            + " - quitting");
    }
  }

  private <T> void validateBaseClassInheritance(CFMappingDef<T> cfMapDef) {
    if (InheritanceType.SINGLE_TABLE.equals(cfMapDef.getInheritanceType())) {
      validateSingleTableInheritance(cfMapDef);
    } else {
      throw new HectorObjectMapperException("You chose inheritance type, "
          + cfMapDef.getInheritanceType().getClass().getSimpleName()
          + ".  Object mapper only supports " + InheritanceType.SINGLE_TABLE + " inheritance type");
    }
  }

  private <T> void validateSingleTableInheritance(CFMappingDef<T> cfMapDef) {
    // validating the base class in an inheritance hierarchy. must have a
    // discriminator column defined
    if (null == cfMapDef.getDiscColumn()) {
      throw new HectorObjectMapperException("Class, " + cfMapDef.getRealClass().getName()
          + ", requested single table inheritance, but you did not specify a "
          + DiscriminatorColumn.class.getSimpleName() + " annotation");
    }

    // if it is abstract, cannot be instantiated and therefore should not have a
    // discriminator value defined
    if (cfMapDef.isAbstract() && null != cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Abstract class, " + cfMapDef.getRealClass().getName()
          + ", has an @" + DiscriminatorValue.class.getSimpleName()
          + " annotation, but cannot be instantiated");
    } else if (!cfMapDef.isAbstract() && null == cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Class, "
          + cfMapDef.getEffectiveClass().getName()
          + ", is a part of inheritance hierarchy, but did not specify a "
          + DiscriminatorValue.class.getSimpleName() + " annotation.  Should it be 'abstract'?");
    }
  }

  private <T> void validateDerivedClassInheritance(CFMappingDef<T> cfMapDef) {
    if (null == cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Base class "
          + cfMapDef.getCfBaseMapDef().getEffectiveClass().getName()
          + " requested single table inheritance, but this class, "
          + cfMapDef.getEffectiveClass().getName() + ", did not specify a "
          + DiscriminatorValue.class.getSimpleName() + " annotation");
    }
  }

}
