package me.prettyprint.hom.cache;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.Column;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.converters.DefaultConverter;

/**
 * Parse, validate, and set defaults if needed for Inheritance functionality.
 * 
 * @author bburruss
 */
public class ColumnParser implements ColumnParserValidator {

  @Override
  public <T> void parse(Field f, Annotation anno, PropertyDescriptor pd, CFMappingDef<T> cfMapDef) {
    try {
      if (anno instanceof Column) {
        processColumnAnnotation(f, (Column) anno, pd, cfMapDef);
      } else if (anno instanceof me.prettyprint.hom.annotations.Column) {
        processColumnCustomAnnotation(f, (me.prettyprint.hom.annotations.Column) anno, pd, cfMapDef);
      } else {
        throw new HectorObjectMapperException("This class cannot parse annotation, "
            + anno.getClass().getSimpleName());
      }
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private <T> void processColumnAnnotation(Field f, Column anno, PropertyDescriptor pd,
      CFMappingDef<T> cfMapDef) throws InstantiationException, IllegalAccessException {
    PropertyMappingDefinition md = new PropertyMappingDefinition(pd, anno.name(),
        DefaultConverter.class);
    cfMapDef.addPropertyDefinition(md);
  }

  private void processColumnCustomAnnotation(Field f, me.prettyprint.hom.annotations.Column anno,
      PropertyDescriptor pd, CFMappingDef<?> cfMapDef) throws InstantiationException,
      IllegalAccessException {
    PropertyMappingDefinition md = new PropertyMappingDefinition(pd, anno.name(), anno.converter());
    
    // if collection type and default converter then make note of collection type for later use
    Class<?> type = pd.getPropertyType();
    if (Collection.class.isAssignableFrom(type) && md.isDefaultConverter()) {
      md.setCollectionType(type);
    }
    
    cfMapDef.addPropertyDefinition(md);
  }
}
