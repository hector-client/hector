package me.prettyprint.hom;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import me.prettyprint.hom.cache.HectorObjectMapperException;
import me.prettyprint.hom.cache.InheritanceParserValidator;
import me.prettyprint.hom.cache.TableParserValidator;
import me.prettyprint.hom.converters.Converter;
import me.prettyprint.hom.converters.DefaultConverter;

/**
 * Manage parsing and caching of class meta-data.
 * 
 * @author
 */
public class ClassCacheMgr {
  private Map<String, CFMappingDef<?, ?>> cfMapByColFamName = new HashMap<String, CFMappingDef<?, ?>>();
  private Map<Class<?>, CFMappingDef<?, ?>> cfMapByClazz = new HashMap<Class<?>, CFMappingDef<?, ?>>();

  private InheritanceParserValidator inheritanceParVal = new InheritanceParserValidator();
  private TableParserValidator tableParVal = new TableParserValidator();
  private IdClassParserValidator idClassParVal = new IdClassParserValidator();

  /**
   * Examine class hierarchy using {@link CFMappingDef} objects to discover the
   * given class' "base inheritance" class. A base inheritance class is
   * determined by {@link CFMappingDef#isBaseInheritanceClass()}
   * 
   * @param <T>
   * @param <I>
   * @param cfMapDef
   * @return returns the base in the ColumnFamily mapping hierarchy
   */
  public <T, I> CFMappingDef<? super T, I> findBaseClassViaMappings(CFMappingDef<T, I> cfMapDef) {
    CFMappingDef<T, I> tmpDef = cfMapDef;
    CFMappingDef<? super T, I> cfSuperDef;
    while (null != (cfSuperDef = tmpDef.getCfSuperMapDef())) {
      if (cfSuperDef.isBaseInheritanceClass()) {
        return cfSuperDef;
      }
      tmpDef = (CFMappingDef<T, I>) cfSuperDef;
    }
    return null;
  }

  /**
   * Retrieve class mapping meta-data by <code>Class</code> object.
   * 
   * @param <T>
   * @param <I>
   * @param clazz
   * @param throwException
   * @return
   */
  public <T, I> CFMappingDef<T, I> getCfMapDef(Class<T> clazz, boolean throwException) {
    @SuppressWarnings("unchecked")
    CFMappingDef<T, I> cfMapDef = (CFMappingDef<T, I>) cfMapByClazz.get(clazz);
    if (null == cfMapDef && throwException) {
      throw new HectorObjectMapperException(
          "could not find property definitions for class, "
              + clazz.getSimpleName()
              + ", in class cache.  This indicates the EntityManager was not initialized properly.  If not using EntityManager the cache must be explicity initialized");

    }
    return cfMapDef;
  }

  /**
   * Retrieve class mapping meta-data by ColumnFamily name.
   * 
   * @param <T>
   * @param <I>
   * @param colFamName
   * @param throwException
   * @return
   */
  public <T, I> CFMappingDef<T, I> getCfMapDef(String colFamName, boolean throwException) {
    @SuppressWarnings("unchecked")
    CFMappingDef<T, I> cfMapDef = (CFMappingDef<T, I>) cfMapByColFamName.get(colFamName);
    if (null == cfMapDef && throwException) {
      throw new HectorObjectMapperException(
          "could not find property definitions for column family, "
              + colFamName
              + ", in class cache.  This indicates the EntityManager was not initialized properly.  If not using EntityManager the cache must be explicity initialized");

    }
    return cfMapDef;
  }

  /**
   * For each class that should be managed, this method must be called to parse
   * its annotations and derive its meta-data.
   * 
   * @param clazz
   * @return
   */
  public <T, I> CFMappingDef<T, I> initializeCacheForClass(Class<T> clazz) {
    CFMappingDef<T, I> cfMapDef = initializeColumnFamilyMapDef(clazz);
    try {
      initializePropertiesMapDef(cfMapDef);
    } catch (IntrospectionException e) {
      throw new HectorObjectMapperException(e);
    } catch (InstantiationException e) {
      throw new HectorObjectMapperException(e);
    } catch (IllegalAccessException e) {
      throw new HectorObjectMapperException(e);
    }
    return cfMapDef;
  }

  private <T, I> void initializePropertiesMapDef(CFMappingDef<T, I> cfMapDef)
      throws IntrospectionException, InstantiationException, IllegalAccessException {

    // get descriptors for all properties in POJO
    PropertyDescriptor[] pdArr = Introspector.getBeanInfo(cfMapDef.getEffectiveClass(),
        cfMapDef.getEffectiveClass().getSuperclass()).getPropertyDescriptors();
    // if no property descriptors then return leaving empty annotation map
    if (null == pdArr || 0 == pdArr.length && null == cfMapDef.getCfBaseMapDef()) {
      throw new HectorObjectMapperException("could not find any properties annotated with @"
          + Column.class.getSimpleName());
    }

    // create tmp map for easy field -> descriptor mapping
    Map<String, PropertyDescriptor> pdMap = new HashMap<String, PropertyDescriptor>();
    for (PropertyDescriptor pd : pdArr) {
      pdMap.put(pd.getName(), pd);
    }

    Class<T> theType = cfMapDef.getEffectiveClass();
    Field[] fieldArr = theType.getDeclaredFields();

    // iterate over all declared fields (for this class only, no inherited
    // fields) processing annotations as we go
    for (Field f : fieldArr) {
      Annotation[] annoArr = f.getAnnotations();
      if (null == annoArr) {
        // TODO BTB:assume @Basic - fields are not required to be annotated to
        // be persisted
        continue;
      }

      for (Annotation anno : annoArr) {
        if (anno instanceof Column) {
          processColumnAnnotation(f, (Column) anno, cfMapDef, pdMap);
        } else if (anno instanceof me.prettyprint.hom.annotations.Column) {
          processColumnCustomAnnotation(f, (me.prettyprint.hom.annotations.Column) anno, cfMapDef,
              pdMap);
        } else if (anno instanceof Id) {
          processIdAnnotation(f, (Id) anno, cfMapDef, pdMap);
        } else if (anno instanceof me.prettyprint.hom.annotations.Id) {
          processIdCustomAnnotation(f, (me.prettyprint.hom.annotations.Id)anno, cfMapDef, pdMap);
        }
      }
    }
  }

  private <T, I> void processColumnAnnotation(Field f, Column anno, CFMappingDef<T, I> cfMapDef,
      Map<String, PropertyDescriptor> pdMap) throws InstantiationException, IllegalAccessException {
    PropertyDescriptor pd = pdMap.get(f.getName());
    if (null == pd) {
      throw new HectorObjectMapperException("Property, "
          + cfMapDef.getEffectiveClass().getSimpleName() + "." + f.getName()
          + ", does not have proper setter/getter");
    }

    PropertyMappingDefinition md = new PropertyMappingDefinition(pd, anno.name(),
        DefaultConverter.class);
    cfMapDef.addPropertyDefinition(md);
  }

  private <T, I> void processColumnCustomAnnotation(Field f,
      me.prettyprint.hom.annotations.Column anno, CFMappingDef<T, I> cfMapDef,
      Map<String, PropertyDescriptor> pdMap) throws InstantiationException, IllegalAccessException {
    PropertyDescriptor pd = pdMap.get(f.getName());
    if (null == pd) {
      throw new HectorObjectMapperException("Property, "
          + cfMapDef.getEffectiveClass().getSimpleName() + "." + f.getName()
          + ", does not have proper setter/getter");
    }

    PropertyMappingDefinition md = new PropertyMappingDefinition(pd, anno.name(),
        (Class<Converter<?>>) anno.converter());
    cfMapDef.addPropertyDefinition(md);
    cfMapDef.addPropertyDefinition(md);
  }

  private <T, I> void processIdAnnotation(Field f, Id anno, CFMappingDef<T, I> cfMapDef,
      Map<String, PropertyDescriptor> pdMap) throws InstantiationException, IllegalAccessException {
    // TODO lookup JPA 2 spec for class-level ids
    @SuppressWarnings("unchecked")
    PropertyMappingDefinition<I> md = new PropertyMappingDefinition(pdMap.get(f.getName()), null,
        DefaultConverter.class);
    if (null != md) {
      if (null == md.getPropDesc() || null == md.getPropDesc().getReadMethod()
          || null == md.getPropDesc().getWriteMethod()) {
        throw new HectorObjectMapperException("@" + Id.class.getSimpleName()
            + " is defined on property, " + f.getName() + ", but its missing proper setter/getter");
      }
    }
    cfMapDef.addIdPropertyMap(md);
  }

  private <T, I> void processIdCustomAnnotation(Field f, me.prettyprint.hom.annotations.Id anno,
      CFMappingDef<T, I> cfMapDef, Map<String, PropertyDescriptor> pdMap)
      throws InstantiationException, IllegalAccessException {
    // TODO lookup JPA 2 spec for class-level ids
    PropertyMappingDefinition<I> md = new PropertyMappingDefinition<I>(pdMap.get(f.getName()),
        null, (Class<Converter<I>>) anno.converter());
    if (null != md) {
      if (null == md.getPropDesc() || null == md.getPropDesc().getReadMethod()
          || null == md.getPropDesc().getWriteMethod()) {
        throw new HectorObjectMapperException("@" + Id.class.getSimpleName()
            + " is defined on property, " + f.getName() + ", but its missing proper setter/getter");
      }
    }
    cfMapDef.addIdPropertyMap(md);

    // @SuppressWarnings("unchecked")
    // PropertyMappingDefinition<I> md =
    // new PropertyMappingDefinition<I>(pdMap.get(f.getName()), null,
    // (Class<Converter<I>>) idAnno
    // .converter());
    // if (null != md) {
    // if (null == md.getPropDesc() || null == md.getPropDesc().getReadMethod()
    // || null == md.getPropDesc().getWriteMethod()) {
    // throw new IllegalStateException("@" + Id.class.getSimpleName() +
    // " is defined on property, "
    // + f.getName() + ", but its missing proper setter/getter");
    // }
    // }
    // cfMapDef.setIdPropertyMap(md);

  }

  private <T, I> CFMappingDef<T, I> initializeColumnFamilyMapDef(Class<T> realClass) {
    // if already init'd don't do it again - could have happened because of
    // inheritance - causes recursive processing for class hierarchy
    CFMappingDef<T, I> cfMapDef = getCfMapDef(realClass, false);
    if (null != cfMapDef) {
      return cfMapDef;
    }

    // try {
    cfMapDef = new CFMappingDef<T, I>(realClass);
    // }
    // catch ( HectorObjectMapperException e ) {
    // // ok, becuase may not have a super class that's an entity
    // return null;
    // }

    Class<T> effectiveType = cfMapDef.getEffectiveClass();
    CFMappingDef<? super T, I> cfSuperMapDef = null;

    // if this class extends a super, then process it first
    if (null != effectiveType.getSuperclass()) {
      try {
        cfSuperMapDef = initializeCacheForClass(effectiveType.getSuperclass());
        cfMapDef.setCfSuperMapDef(cfSuperMapDef);
      } catch (HectorObjectMapperException e) {
        // ok, becuase may not have a super class that's an entity
      }
    }

    Annotation[] annoArr = effectiveType.getAnnotations();
    if (null == annoArr) {
      // TODO:btb see if this might be an error
      return cfMapDef;
    }

    for (Annotation anno : annoArr) {
      if (anno instanceof Table) {
        tableParVal.parse(this, anno, cfMapDef);
      } else if (anno instanceof IdClass) {
        idClassParVal.parse(this, anno, cfMapDef);
      } else if (anno instanceof Inheritance) {
        inheritanceParVal.parse(this, anno, cfMapDef);
      } else if (anno instanceof DiscriminatorColumn) {
        inheritanceParVal.parse(this, anno, cfMapDef);
      } else if (anno instanceof DiscriminatorValue) {
        inheritanceParVal.parse(this, anno, cfMapDef);
      }
    }

    // by the time we get here, all super classes and their annotations have
    // been processed and validated, and all annotations for this class have
    // been processed. what's left to do is validate this class, set super
    // classes, and and set any defaults
    checkMappingAndSetDefaults(cfMapDef);

    // if this class is not a derived class, then map the ColumnFamily name
    if (!cfMapDef.isDerivedClassInheritance()) {
      cfMapByColFamName.put(cfMapDef.getEffectiveColFamName(), cfMapDef);
    }

    // always map the parsed class to its ColumnFamily map definition
    cfMapByClazz.put(realClass, cfMapDef);

    return cfMapDef;
  }

  private <T, I> void checkMappingAndSetDefaults(CFMappingDef<T, I> cfMapDef) {
    inheritanceParVal.validateAndSetDefaults(this, cfMapDef);
    tableParVal.validateAndSetDefaults(this, cfMapDef);
    idClassParVal.validateAndSetDefaults(this, cfMapDef);
  }
}
