package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

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
    if (cfMapDef.isBaseInheritanceClass()) {
      validateBaseClassInheritance(cfMapDef);
    } else if (cfMapDef.isDerivedClassInheritance()) {
      validateDerivedClassInheritance(cfMapDef);
    } else if (cfMapDef.isIntermediateClassInheritance()) {
        validateIntermediateClassInheritance(cfMapDef);
    } else {
      if (null != cacheMgr.findBaseClassViaMappings(cfMapDef))
        throw new HectorObjectMapperException("@" + Inheritance.class.getSimpleName()
            + " found in class hierarchy of class " + cfMapDef.getEffectiveClass().getName() + ", but no @" + DiscriminatorValue.class.getSimpleName()
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
    if (Modifier.isAbstract(cfMapDef.getEffectiveClass().getModifiers())
        && null != cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Abstract class, " + cfMapDef.getRealClass().getName()
          + ", has an @" + DiscriminatorValue.class.getSimpleName()
          + " annotation, but cannot be instantiated");
    }

    // since abstract, must have a discriminator column defined
    if (!Modifier.isAbstract(cfMapDef.getEffectiveClass().getModifiers())
        && null == cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Class, " + cfMapDef.getRealClass().getName()
          + ", is not abstract, so it must have an @" + DiscriminatorValue.class.getSimpleName()
          + " annotation");
    }
  }

  private <T> void validateIntermediateClassInheritance(CFMappingDef<T> cfMapDef) {
    if (null != cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException(cfMapDef.getRealClass().getName() 
          + " is abstract, but specified a "
          + DiscriminatorValue.class.getSimpleName() + " annotation");
    }
  }

  private <T> void validateDerivedClassInheritance(CFMappingDef<T> cfMapDef) {
    if (null == cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Base class "
          + cfMapDef.getCfBaseMapDef().getClass().getName()
          + " requested single table inheritance, but this class, "
          + cfMapDef.getRealClass().getName() + ", did not specify a "
          + DiscriminatorValue.class.getSimpleName() + " annotation");
    }
  }
}
