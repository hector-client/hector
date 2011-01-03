package me.prettyprint.hom;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


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

  /**
   * Retrieve class meta-data by <code>class</code> object.
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
      throw new IllegalStateException(
          "could not find property definitions for class, "
          + clazz.getSimpleName()
          + ", in class cache.  This indicates the EntityManager was not initialized properly.  If not using EntityManager the cache must be explicity initialized");

    }
    return cfMapDef;
  }

  /**
   * Retrieve class meta-data by column family name.
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
      throw new IllegalStateException(
          "could not find property definitions for column family, "
          + colFamName
          + ", in class cache.  This indicates the EntityManager was not initialized properly.  If not using EntityManager the cache must be explicity initialized");

    }
    return cfMapDef;
  }

  /**
   * For each class that should be managed, this method must be called to
   * parse its annotations and derive its meta-data.
   * 
   * @param clazz
   * @return
   */
  public <T, I> CFMappingDef<T, I> initializeCacheForClass(Class<T> clazz) {
    CFMappingDef<T, I> cfMapDef = initializeColumnFamilyMapDef(clazz);
    try {
      initializePropertiesMapDef(cfMapDef);
    }
    catch (IntrospectionException e) {
      throw new RuntimeException(e);
    }
    catch (InstantiationException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return cfMapDef;
  }

  private <T, I> void initializePropertiesMapDef(CFMappingDef<T, I> cfMapDef) throws IntrospectionException,
  InstantiationException, IllegalAccessException {
    // if no property descriptors then return leaving empty annotation map
    PropertyDescriptor[] pdArr =
      Introspector.getBeanInfo(cfMapDef.getClazz(), cfMapDef.getClazz().getSuperclass())
      .getPropertyDescriptors();
    if (null == pdArr || 0 == pdArr.length && null == cfMapDef.getCfBaseMapDef()) {
      throw new IllegalStateException("could not find any properties annotated with @"
          + Column.class.getSimpleName());
    }

    // create tmp map for easy field -> descriptor mapping
    Map<String, PropertyDescriptor> pdMap = new HashMap<String, PropertyDescriptor>();
    for (PropertyDescriptor pd : pdArr) {
      pdMap.put(pd.getName(), pd);
    }

    Class<T> theType = cfMapDef.getClazz();
    // do {
    Field[] fieldArr = theType.getDeclaredFields();
    for (Field f : fieldArr) {
      Column colAnno = f.getAnnotation(javax.persistence.Column.class);
      if (null != colAnno) {
        PropertyDescriptor pd = pdMap.get(f.getName());
        if (null == pd) {
          throw new IllegalStateException("Property, " + cfMapDef.getClazz().getSimpleName() + "."
              + f.getName() + ", does not have proper setter/getter");
        }

        @SuppressWarnings("unchecked")
        PropertyMappingDefinition md =
          new PropertyMappingDefinition(pd, colAnno.name(), DefaultConverter.class);
        cfMapDef.addPropertyDefinition(md);
      }

      // TODO lookup JPA 2 spec for class-level ids
      Id idAnno = f.getAnnotation(Id.class);
      if (null != idAnno) {
        @SuppressWarnings("unchecked")
        PropertyMappingDefinition md =
          new PropertyMappingDefinition(pdMap.get(f.getName()), null, DefaultConverter.class);
        if (null != md) {
          if (null == md.getPropDesc() || null == md.getPropDesc().getReadMethod()
              || null == md.getPropDesc().getWriteMethod()) {
            throw new IllegalStateException("@" + Id.class.getSimpleName() + " is defined on property, "
                + f.getName() + ", but its missing proper setter/getter");
          }
        }
        cfMapDef.setIdPropertyMap(md);
      }
    }

    // theType = (Class<T>) theType.getSuperclass();
    // } while (Object.class != theType);
  }

  private <T, I> CFMappingDef<T, I> initializeColumnFamilyMapDef(Class<T> clazz) {
    // if already init'd don't do it again - could have happened because of
    // inheritance
    CFMappingDef<T, I> cfMapDef = getCfMapDef(clazz, false);
    if (null != cfMapDef) {
      return cfMapDef;
    }

    // if anonymous class, then get the superclass (the real class)
    Class<T> theType = clazz;
    boolean entityFound;
    while (!(entityFound = (null != theType.getAnnotation(Entity.class)))) {
      if (!theType.isAnonymousClass()) {
        break;
      }
      else {
        theType = (Class<T>) theType.getSuperclass();
      }
    }

    // if class is missing @Entity, then proceed no further
    if (!entityFound) {
      throw new IllegalStateException("class, " + clazz.getName() + ", not annotated with @"
          + Entity.class.getSimpleName());
    }

    cfMapDef = new CFMappingDef<T, I>(theType);
    CFMappingDef<? super T, I> cfBaseMapDef = null;

    // if this class extends a super, then process it first
    if (null != theType.getSuperclass()) {
      try {
        cfBaseMapDef = initializeCacheForClass(theType.getSuperclass());
        cfMapDef.setCfSuperMapDef((CFMappingDef<? super T, I>) getCfMapDef(theType.getSuperclass(), false));
      }
      catch (IllegalStateException e) {
        // this is ok, super class might not be an enitity
      }
    }

    // determine column family name
    Table tbl = theType.getAnnotation(Table.class);
    if (null == cfBaseMapDef) {
      if (null != tbl) {
        // make sure the column family is not already used
        CFMappingDef<?, ?> tmpDef;
        if ( null != (tmpDef=getCfMapDef(tbl.name(), false))) {
          throw new IllegalStateException("classes, " + clazz.getName() + " and " + tmpDef.getClazz().getName() + ", are both mapped to ColumnFamily, " + tbl.name() + ".  Can only have one class/ColumnFamily mapping - if multiple classes can be derived from a single ColumnFamily, use @"+Inheritance.class.getSimpleName() );
        }
        cfMapDef.setColFamName(tbl.name());
      }
      else {
        cfMapDef.setColFamName(theType.getSimpleName());
      }
    }
    else if (null == tbl) {
      cfMapDef.setColFamName(cfBaseMapDef.getColFamName());
    }
    else {
      throw new IllegalStateException("Cannot specify @" + Table.class.getSimpleName() + " for derived class, "
          + cfMapDef.getClazz().getName());
    }

    processSuperclassInheritance(cfMapDef);
    processDerivedInheritance(cfMapDef);

    if (null == cfBaseMapDef) {
      cfMapByColFamName.put(cfMapDef.getColFamName(), cfMapDef);
    }
    cfMapByClazz.put(clazz, cfMapDef);

    return cfMapDef;
  }

  /**
   * Process a class marked with {@link Inheritance}.
   * 
   * @param <T>
   *            Class type
   * @param <I>
   *            Object ID type
   * @param cfMapDef
   */
  private <T, I> void processSuperclassInheritance(CFMappingDef<T, I> cfMapDef) {
    Class<T> clazz = cfMapDef.getClazz();

    // if inheritance is defined then we setup for subclasses
    Inheritance inheritance = clazz.getAnnotation(Inheritance.class);
    if (null == inheritance) {
      return;
    }
    // TODO not hard-coded
    cfMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);

    DiscriminatorColumn discriminatorCol = clazz.getAnnotation(DiscriminatorColumn.class);
    if (null != discriminatorCol) {
      cfMapDef.setDiscColumn(discriminatorCol.name());
      cfMapDef.setDiscType(discriminatorCol.discriminatorType());
    }
    else {
      cfMapDef.setDiscType(DiscriminatorType.STRING);
    }

    DiscriminatorValue discriminatorValue = clazz.getAnnotation(DiscriminatorValue.class);
    if (null == discriminatorValue) {
      throw new IllegalStateException("@" + Inheritance.class.getSimpleName() + " found, but no @"
          + DiscriminatorValue.class.getSimpleName() + " - cannot continue");
    }
    cfMapDef.setDiscValue(discriminatorValue.value());
  }

  /**
   * Process a derived class with a super class marked with
   * {@link Inheritance}.
   * 
   * @param <T>
   *            Super class type
   * @param <I>
   *            Object ID type
   * @param cfMapDef
   */
  private <T, I> void processDerivedInheritance(CFMappingDef<? extends T, I> cfMapDef) {
    Class<? extends T> derClazz = cfMapDef.getClazz();
    Class<T> superClazz = (Class<T>) derClazz.getSuperclass();
    if (null == superClazz) {
      return;
    }

    CFMappingDef<T, I> superCfMapDef = getCfMapDef(superClazz, false);
    if (null == superCfMapDef) {
      return;
    }

    while (null != superCfMapDef && null == superCfMapDef.getInheritanceType()) {
      superCfMapDef = (CFMappingDef<T, I>) superCfMapDef.getCfBaseMapDef();
    }

    if (null == superCfMapDef) {
      throw new IllegalStateException(derClazz.getName() + " has mapped super class, but " + superClazz.getName()
          + " isn't marked with @" + Inheritance.class.getSimpleName() + " - cannot continue");
    }

    DiscriminatorValue discriminatorValue = derClazz.getAnnotation(DiscriminatorValue.class);
    if (null == discriminatorValue) {
      throw new IllegalStateException(" found, but no @" + DiscriminatorValue.class.getSimpleName()
          + " - cannot continue");
    }
    cfMapDef.setDiscValue(discriminatorValue.value());

    cfMapDef.setCfBaseMapDef(superCfMapDef);
    superCfMapDef.addDerivedClassMap(cfMapDef);
  }
}
