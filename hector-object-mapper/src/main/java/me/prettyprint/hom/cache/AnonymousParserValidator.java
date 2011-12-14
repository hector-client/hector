package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.annotations.AnonymousPropertyHandling;

/**
 * Parse, validate, and set defaults if needed for Inheritance functionality.
 * 
 * @author bburruss
 */
public class AnonymousParserValidator implements ParserValidator {

  @Override
  public <T> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T> cfMapDef) {
    if (anno instanceof AnonymousPropertyHandling) {
      parseInheritanceAnnotation((AnonymousPropertyHandling) anno, cfMapDef);
    } else {
      throw new HectorObjectMapperException("This class cannot parse annotation, "
          + anno.getClass().getSimpleName());
    }
  }

  private <T> void parseInheritanceAnnotation(AnonymousPropertyHandling anno,
      CFMappingDef<T> cfMapDef) {
    cfMapDef.setAnonymousValueType(anno.type());
    try {
      cfMapDef.setAnonymousValueSerializer(instantiateSerializer(anno.serializer()));
    } catch (Throwable e) {
      throw new HectorObjectMapperException(
          "exception while instantiating anonymous converter for class, " + cfMapDef.getRealClass()
              + ", with converter type, " + anno.serializer(), e);
    }

    Method addMeth;
    try {
      addMeth = cfMapDef.getRealClass().getMethod(anno.adder(), String.class, anno.type());
    } catch (NoSuchMethodException e) {
      throw new HectorObjectMapperException("Could not find anonymous add handler for class, "
          + cfMapDef.getRealClass() + ", with anonymous value type, " + anno.type());
    } catch (Throwable e) {
      throw new HectorObjectMapperException(
          "exception while finding anonymous add handler for class, " + cfMapDef.getRealClass()
              + ", with anonymous value type, " + anno.type(), e);
    }

    Method getMeth;
    try {
      getMeth = cfMapDef.getRealClass().getMethod(anno.getter());
    } catch (NoSuchMethodException e) {
      throw new HectorObjectMapperException("Could not find anonymous get handler for class, "
          + cfMapDef.getRealClass());
    } catch (Throwable e) {
      throw new HectorObjectMapperException(
          "exception while finding anonymous get handler for class, " + cfMapDef.getRealClass());
    }

    cfMapDef.setAnonymousPropertyAddHandler(addMeth);
    cfMapDef.setAnonymousPropertyGetHandler(getMeth);
  }

  @Override
  public <T> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef) {
//    Serializer<?> ser = cfMapDef.getAnonymousValueSerializer();
//    if ( null == ser) {
//      return;
//    }
//    
//    try {
//      ser.getClass().getMethod("toByteBuffer", cfMapDef.getAnonymousValueType());
//    } catch (NoSuchMethodException e) {
//      throw new HectorObjectMapperException("Anonymous serializer, "
//          + cfMapDef.getAnonymousValueSerializer().getClass().getName() + ", cannot handle type, "
//          + cfMapDef.getAnonymousValueType().getClass().getName());
//    } catch (Throwable e) {
//      throw new HectorObjectMapperException(
//          "exception while validating anonymous configuration for class, "
//              + cfMapDef.getAnonymousValueSerializer().getClass().getName());
//    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private Serializer instantiateSerializer(Class ser) {
    try {
      return (Serializer) ser.getMethod("get").invoke(null, (Object[]) null);
    } catch (NoSuchMethodException e) {
      try {
        return  (Serializer) ser.newInstance();
      } catch (Throwable e1) {
        throw new HectorObjectMapperException("exception while instantiating Hector serializer, "
            + ser.getName());
      }
    } catch (Throwable e) {
      throw new HectorObjectMapperException("exception while instantiating Hector serializer, "
          + ser.getName());
    }
  }
}
