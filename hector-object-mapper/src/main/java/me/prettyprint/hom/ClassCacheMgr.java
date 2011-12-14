package me.prettyprint.hom;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import me.prettyprint.hom.annotations.AnonymousPropertyHandling;
import me.prettyprint.hom.cache.AnonymousParserValidator;
import me.prettyprint.hom.cache.ColumnParser;
import me.prettyprint.hom.cache.HectorObjectMapperException;
import me.prettyprint.hom.cache.IdClassParserValidator;
import me.prettyprint.hom.cache.InheritanceParserValidator;
import me.prettyprint.hom.cache.TableParserValidator;
import me.prettyprint.hom.converters.DefaultConverter;

/**
 * Manage parsing and caching of class meta-data.
 * 
 * @author
 */
public class ClassCacheMgr {
  private Map<String, CFMappingDef<?>> cfMapByColFamName = new HashMap<String, CFMappingDef<?>>();
  private Map<Class<?>, CFMappingDef<?>> cfMapByClazz = new HashMap<Class<?>, CFMappingDef<?>>();

  private InheritanceParserValidator inheritanceParVal = new InheritanceParserValidator();
  private TableParserValidator tableParVal = new TableParserValidator();
  private IdClassParserValidator idClassParVal = new IdClassParserValidator();
  private ColumnParser columnPar = new ColumnParser();
  private AnonymousParserValidator anonymousParVal = new AnonymousParserValidator();

  /**
   * Examine class hierarchy using {@link CFMappingDef} objects to discover the
   * given class' "base inheritance" class. A base inheritance class is
   * determined by {@link CFMappingDef#isBaseEntity()}
   * 
   * @param <T>
   * 
   * @param cfMapDef
   * @return returns the base in the ColumnFamily mapping hierarchy
   */
  public <T> CFMappingDef<? super T> findBaseClassViaMappings(CFMappingDef<T> cfMapDef) {
    CFMappingDef<? super T> tmpDef = cfMapDef;
    CFMappingDef<? super T> cfSuperDef;
    while (null != (cfSuperDef = tmpDef.getCfSuperMapDef())) {
      if (cfSuperDef.isBaseEntity()) {
        return cfSuperDef;
      }
      tmpDef = cfSuperDef;
    }
    return null;
  }

  /**
   * Retrieve class mapping meta-data by <code>Class</code> object.
   * 
   * @param <T>
   * @param clazz
   * @param throwException
   * @return CFMappingDef if found, exception if throwException = true, and null
   *         otherwise
   */
  public <T> CFMappingDef<T> getCfMapDef(Class<T> clazz, boolean throwException) {
    @SuppressWarnings("unchecked")
    CFMappingDef<T> cfMapDef = (CFMappingDef<T>) cfMapByClazz.get(clazz);
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
   * @param colFamName
   * @param throwException
   * @return CFMappingDef if found, exception if throwException = true, and null
   *         otherwise
   */
  public <T> CFMappingDef<T> getCfMapDef(String colFamName, boolean throwException) {
    @SuppressWarnings("unchecked")
    CFMappingDef<T> cfMapDef = (CFMappingDef<T>) cfMapByColFamName.get(colFamName);
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
   * @param <T>
   * 
   * @param clazz
   * @return CFMapping describing the initialized class.
   */
  public <T> CFMappingDef<T> initializeCacheForClass(Class<T> clazz) {
    CFMappingDef<T> cfMapDef = initializeColumnFamilyMapDef(clazz);
    try {
      initializePropertiesMapDef(cfMapDef);
    } catch (IntrospectionException e) {
      throw new HectorObjectMapperException(e);
    } catch (InstantiationException e) {
      throw new HectorObjectMapperException(e);
    } catch (IllegalAccessException e) {
      throw new HectorObjectMapperException(e);
    }

    // by the time we get here, all super classes and their annotations have
    // been processed and validated, and all annotations for this class have
    // been processed. what's left to do is validate this class, set super
    // classes, and and set any defaults
    checkMappingAndSetDefaults(cfMapDef);

    // if this class is not a derived class, then map the ColumnFamily name
    if (!cfMapDef.isDerivedEntity()) {
      cfMapByColFamName.put(cfMapDef.getEffectiveColFamName(), cfMapDef);
    }

    // always map the parsed class to its ColumnFamily map definition
    cfMapByClazz.put(cfMapDef.getRealClass(), cfMapDef);

    return cfMapDef;
  }

  public Map<String, PropertyDescriptor> getFieldPropertyDescriptorMap(Class<?> clazz)
      throws IntrospectionException {
    Map<String, PropertyDescriptor> pdMap = new HashMap<String, PropertyDescriptor>();

    // get descriptors for all properties in POJO
    PropertyDescriptor[] pdArr = Introspector.getBeanInfo(clazz, clazz.getSuperclass())
                                             .getPropertyDescriptors();

    // if no property descriptors then return leaving empty annotation map
    if (null == pdArr || 0 == pdArr.length) {
      return pdMap;
    }

    // create tmp map for easy field -> descriptor mapping
    for (PropertyDescriptor pd : pdArr) {
      pdMap.put(pd.getName(), pd);
    }

    return pdMap;
  }

  private <T> void initializePropertiesMapDef(CFMappingDef<T> cfMapDef)
      throws IntrospectionException, InstantiationException, IllegalAccessException {
    Class<T> theType = cfMapDef.getEffectiveClass();

    Map<String, PropertyDescriptor> pdMap = getFieldPropertyDescriptorMap(theType);
    if (pdMap.isEmpty() && !cfMapDef.isPersistableDerivedEntity()) {
      throw new HectorObjectMapperException("could not find any properties annotated with @"
          + Column.class.getSimpleName());
    }

    Field[] fieldArr = theType.getDeclaredFields();

    // iterate over all declared fields (for this class only, no inherited
    // fields) processing annotations as we go
    for (Field f : fieldArr) {
      Annotation[] annoArr = f.getAnnotations();
      if (null == annoArr) {
        // TODO BTB:assume @Basic - fields are not required to be annotated to
        // be persisted if they are a "basic type" - see 2.8 and 11.1.6
        // @Transient to ignore a field
        continue;
      }

      for (Annotation anno : annoArr) {
        PropertyDescriptor pd = pdMap.get(f.getName());
        if (null == pd) {
          throw new HectorObjectMapperException("Property, "
              + cfMapDef.getEffectiveClass().getSimpleName() + "." + f.getName()
              + ", does not have proper setter/getter");
        }

        if (anno instanceof Column || anno instanceof me.prettyprint.hom.annotations.Column) {
          columnPar.parse(f, anno, pd, cfMapDef);
        } else if (anno instanceof Basic) {
          // basicPar.parse(f, anno, pd, cfMapDef);
        } else if (anno instanceof Id) {
          processIdAnnotation(f, (Id) anno, cfMapDef, pdMap);
        } else if (anno instanceof me.prettyprint.hom.annotations.Id) {
          processIdCustomAnnotation(f, (me.prettyprint.hom.annotations.Id) anno, cfMapDef, pdMap);
          // } else if (anno instanceof me.prettyprint.hom.annotations.List) {
          // processListCustomAnnotation(f,
          // (me.prettyprint.hom.annotations.List) anno, cfMapDef,
          // pdMap);
        }
      }
    }
  }

  private <T> void processIdAnnotation(Field f, Id anno, CFMappingDef<T> cfMapDef,
      Map<String, PropertyDescriptor> pdMap) throws InstantiationException, IllegalAccessException {
    // TODO lookup JPA 2 spec for class-level ids
    PropertyMappingDefinition md = new PropertyMappingDefinition(pdMap.get(f.getName()), null,
        DefaultConverter.class);
    if (null == md.getPropDesc() || null == md.getPropDesc().getReadMethod()
        || null == md.getPropDesc().getWriteMethod()) {
      throw new HectorObjectMapperException("@" + Id.class.getSimpleName()
          + " is defined on property, " + f.getName() + ", but its missing proper setter/getter");
    }
    cfMapDef.getKeyDef().addIdPropertyMap(md);
  }

  private <T> void processIdCustomAnnotation(Field f, me.prettyprint.hom.annotations.Id anno,
      CFMappingDef<T> cfMapDef, Map<String, PropertyDescriptor> pdMap)
      throws InstantiationException, IllegalAccessException {
    // TODO lookup JPA 2 spec for class-level ids
    PropertyMappingDefinition md = new PropertyMappingDefinition(pdMap.get(f.getName()), null,
        anno.converter());
    if (null == md.getPropDesc() || null == md.getPropDesc().getReadMethod()
        || null == md.getPropDesc().getWriteMethod()) {
      throw new HectorObjectMapperException("@" + Id.class.getSimpleName()
          + " is defined on property, " + f.getName() + ", but its missing proper setter/getter");
    }

    cfMapDef.getKeyDef().addIdPropertyMap(md);
  }

  private <T> CFMappingDef<T> initializeColumnFamilyMapDef(Class<T> realClass) {
    // if already init'd don't do it again - could have happened because of
    // inheritance - causes recursive processing for class hierarchy
    CFMappingDef<T> cfMapDef = getCfMapDef(realClass, false);
    if (null != cfMapDef) {
      return cfMapDef;
    }

    cfMapDef = new CFMappingDef<T>(realClass);

    Class<T> effectiveType = cfMapDef.getEffectiveClass();
    CFMappingDef<? super T> cfSuperMapDef = null;

    // if this class extends a super, then process it first
    if (null != effectiveType.getSuperclass()) {
      try {
        cfSuperMapDef = initializeCacheForClass(effectiveType.getSuperclass());
        cfMapDef.setCfSuperMapDef(cfSuperMapDef);
      } catch (HomMissingEntityAnnotationException e) {
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
      } else if (anno instanceof AnonymousPropertyHandling) {
        anonymousParVal.parse(this, anno, cfMapDef);
      }
    }

    return cfMapDef;
  }

  private <T> void checkMappingAndSetDefaults(CFMappingDef<T> cfMapDef) {
    inheritanceParVal.validateAndSetDefaults(this, cfMapDef);
    tableParVal.validateAndSetDefaults(this, cfMapDef);
    idClassParVal.validateAndSetDefaults(this, cfMapDef);
    anonymousParVal.validateAndSetDefaults(this, cfMapDef);

    // must do this after tabeParVal validate
    checkForPojoPrimaryKey(cfMapDef);

//    checkForAnonymousHandler(cfMapDef);

    generateColumnSliceIfNeeded(cfMapDef);
  }

  private void checkForPojoPrimaryKey(CFMappingDef<?> cfMapDef) {
    // if we know it's a complex key then it must be present so we only check
    // case for simple one field key

    // SimpleTestBean breaks this check right now because it uses method
    // annotations which isn't supported by the ClassCacheMgr at this time
    // if (!cfMapDef.getKeyDef().isComplexKey()) {
    // if (!cfMapDef.getKeyDef().isSimpleIdPresent()) {
    // throw new HectorObjectMapperException("Entity, " +
    // cfMapDef.getRealClass().getName()
    // + ", is missing a primary key.  Must annotate at least one field with @"
    // + Id.class.getSimpleName() + " or use a complex primary key");
    // }
    // }
  }

//  private <T> void checkForAnonymousHandler(CFMappingDef<T> cfMapDef) {
//    CFMappingDef<? super T> tmpDef = cfMapDef;
//    while (null != tmpDef) {
//      Method meth = findAnnotatedMethod(cfMapDef.getEffectiveClass(),
//          AnonymousPropertyAddHandler.class);
//      if (null != meth) {
//        Class<?>[] typeArr = meth.getParameterTypes();
//        if (2 != typeArr.length || !(typeArr[0] == String.class) || !(typeArr[1] == byte[].class)) {
//          throw new HectorObjectMapperException(AnonymousPropertyAddHandler.class.getSimpleName()
//              + " expects a method with exactly two paramters of types, String and byte[]");
//        }
//
//        cfMapDef.setAnonymousPropertyAddHandler(meth);
//        return;
//      }
//      tmpDef = tmpDef.getCfSuperMapDef();
//    }
//  }

  private void generateColumnSliceIfNeeded(CFMappingDef<?> cfMapDef) {
    if (cfMapDef.isColumnSliceRequired()) {
      Collection<PropertyMappingDefinition> propColl = cfMapDef.getAllProperties();

      String[] columnNames = new String[cfMapDef.isPersistableEntity() ? propColl.size()
                                                                      : propColl.size() + 1];
      Iterator<PropertyMappingDefinition> iter = propColl.iterator();
      int pos = 0;
      while (iter.hasNext()) {
        columnNames[pos++] = iter.next().getColName();
      }

      // if an inheritance hierarchy exists we need to add in the discriminator
      // column
      if (!cfMapDef.isPersistableEntity()) {
        columnNames[pos] = cfMapDef.getDiscColumn();
      }
      cfMapDef.setSliceColumnNameArr(columnNames);
    }
  }

  /**
   * Find method annotated with the given annotation.
   * 
   * @param clazz
   * @param anno
   * @return returns Method if found, null otherwise
   */
  public Method findAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> anno) {
    for (Method meth : clazz.getMethods()) {
      if (meth.isAnnotationPresent(anno)) {
        return meth;
      }
    }
    return null;
  }

}
