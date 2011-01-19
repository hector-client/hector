package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;

import org.apache.openjpa.util.UnsupportedException;

/**
 * Parse, validate, and set defaults if needed for Inheritance functionality.
 * 
 * @author bburruss
 */
public class InheritanceParserValidator implements ParserValidator {

  public <T, I> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T, I> cfMapDef) {
    if (anno instanceof Inheritance) {
      parseInheritanceAnnotation((Inheritance) anno, cfMapDef);
    } else if (anno instanceof DiscriminatorColumn) {
      parseDiscriminatorColumnAnnotation((DiscriminatorColumn) anno, cfMapDef);
    } else if (anno instanceof DiscriminatorValue) {
      parseDiscriminatorValueAnnotation((DiscriminatorValue) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation "
          + anno.getClass().getSimpleName());
    }
  }

  private <T, I> void parseInheritanceAnnotation(Inheritance anno, CFMappingDef<T, I> cfMapDef) {
    if (InheritanceType.SINGLE_TABLE == anno.strategy()) {
      cfMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);
    } else {
      throw new UnsupportedException("Hector object mapper only supports "
          + InheritanceType.SINGLE_TABLE + " inheritance at the moment");
    }
  }

  private <T, I> void parseDiscriminatorColumnAnnotation(DiscriminatorColumn anno,
      CFMappingDef<T, I> cfMapDef) {
    cfMapDef.setDiscColumn(anno.name());
    cfMapDef.setDiscType(anno.discriminatorType());
  }

  private <T, I> void parseDiscriminatorValueAnnotation(DiscriminatorValue anno,
      CFMappingDef<T, I> cfMapDef) {
    cfMapDef.setDiscValue(anno.value());
  }

  public <T, I> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    if (cfMapDef.isBaseInheritanceClass()) {
      validateBaseClassInheritance(cfMapDef);
    } else if (cfMapDef.isDerivedClassInheritance()) {
      validateDerivedClassInheritance(cfMapDef);
    } else {
      if (null != cacheMgr.findBaseClassViaMappings(cfMapDef))
        throw new HectorObjectMapperException("@" + Inheritance.class.getSimpleName()
            + " found in class hierarchy, but no @" + DiscriminatorValue.class.getSimpleName()
            + " - quitting");
    }
  }

  private <T, I> void validateBaseClassInheritance(CFMappingDef<T, I> cfMapDef) {
    if (InheritanceType.SINGLE_TABLE.equals(cfMapDef.getInheritanceType())) {
      validateSingleTableInheritance(cfMapDef);
    } else {
      throw new HectorObjectMapperException("You chose inheritance type, "
          + cfMapDef.getInheritanceType().getClass().getSimpleName()
          + ".  Object mapper only supports " + InheritanceType.SINGLE_TABLE + " inheritance type");
    }
  }

  private <T, I> void validateSingleTableInheritance(CFMappingDef<T, I> cfMapDef) {
    if (null == cfMapDef.getDiscColumn()) {
      throw new HectorObjectMapperException("Class " + cfMapDef.getClass().getName()
          + " requested single table inheritance, but you did not specify a "
          + DiscriminatorColumn.class.getSimpleName() + " annotation");
    } else if (null == cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Class " + cfMapDef.getClass().getName()
          + " requested single table inheritance, but you did specify a "
          + DiscriminatorValue.class.getSimpleName() + " annotation");
    }
  }

  private <T, I> void validateDerivedClassInheritance(CFMappingDef<T, I> cfMapDef) {
    if (null == cfMapDef.getDiscValue()) {
      throw new HectorObjectMapperException("Base class "
          + cfMapDef.getCfBaseMapDef().getClass().getName()
          + " requested single table inheritance, but this class, " + cfMapDef.getClass().getName()
          + ", did not specify a " + DiscriminatorValue.class.getSimpleName() + " annotation");
    }
  }

}
