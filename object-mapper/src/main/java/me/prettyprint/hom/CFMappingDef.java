package me.prettyprint.hom;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.InheritanceType;

import me.prettyprint.hom.cache.HectorObjectMapperException;

import org.apache.commons.collections.collection.CompositeCollection;

/**
 * Holder for the mapping between a Class annotated with {@link Entity} and the
 * Cassandra column family name.
 * 
 * @author Todd Burruss
 */
public class CFMappingDef<T, I> {
  private Class<T> realClass;
  private Class<T> effectiveClass;
  private CFMappingDef<? super T, I> cfBaseMapDef;
  private CFMappingDef<? super T, I> cfSuperMapDef;
  private String colFamName;
  private InheritanceType inheritanceType;
  private String discColumn;
  private DiscriminatorType discType;
  private Object discValue; // this can be a variety of types
  private Set<PropertyMappingDefinition<I>> idPropertySet = new HashSet<PropertyMappingDefinition<I>>();

  private Map<Object, CFMappingDef<? extends T, I>> derivedClassMap = new HashMap<Object, CFMappingDef<? extends T, I>>();
  private Collection<PropertyMappingDefinition<?>> allMappedProps;

  // provide caching by class object for the class' mapping definition
  private Map<String, PropertyMappingDefinition<?>> propertyCacheByPropName = new HashMap<String, PropertyMappingDefinition<?>>();

  // provide caching by class object for the class' mapping definition
  private Map<String, PropertyMappingDefinition<?>> propertyCacheByColName = new HashMap<String, PropertyMappingDefinition<?>>();

  public CFMappingDef(Class<T> clazz) {
    setDefaults(clazz);
  }

  /**
   * Setup mapping with defaults for the given class. Does not parse all
   * annotations.
   * 
   * @param realClass
   */
  public void setDefaults(Class<T> realClass) {
    this.realClass = realClass;

    // find the "effective" class - skipping up the hierarchy over inner classes
    effectiveClass = realClass;
    boolean entityFound;
    while (!(entityFound = (null != effectiveClass.getAnnotation(Entity.class)))) {
      // TODO:BTB this might should be isSynthetic
      if (!effectiveClass.isAnonymousClass()) {
        break;
      } else {
        effectiveClass = (Class<T>) effectiveClass.getSuperclass();
      }
    }

    // if class is missing @Entity, then proceed no further
    if (!entityFound) {
      throw new HectorObjectMapperException("class, " + realClass.getName() + ", not annotated with @"
          + Entity.class.getSimpleName());
    }
  }

  public void addDerivedClassMap(CFMappingDef<? extends T, I> cfDerivedMapDef) {
    derivedClassMap.put(cfDerivedMapDef.getDiscValue(), cfDerivedMapDef);
  }

  public PropertyMappingDefinition<?> getPropMapByPropName(String propName) {
    return propertyCacheByPropName.get(propName);
  }

  public PropertyMappingDefinition<?> getPropMapByColumnName(String colName) {
    PropertyMappingDefinition<?> md = propertyCacheByColName.get(colName);
    if (null != md) {
      return md;
    } else if (null != cfSuperMapDef) {
      return cfSuperMapDef.getPropMapByColumnName(colName);
    } else {
      return null;
    }
  }

  public void addPropertyDefinition(PropertyMappingDefinition<?> propDef) {
    propertyCacheByColName.put(propDef.getColName(), propDef);
    propertyCacheByPropName.put(propDef.getPropDesc().getName(), propDef);
  }

  public String getColFamName() {
    return colFamName;
  }
  
  public String getEffectiveColFamName() {
    if ( null != colFamName ) {
      return colFamName;
    }
    else if ( null != cfBaseMapDef ) {
      return cfBaseMapDef.getColFamName();
    }
    else {
      throw new HectorObjectMapperException("trying to get ColumnFamily name, but is missing for mapping, " + this.toString());
    }
  }

  public Class<T> getEffectiveClass() {
    return effectiveClass;
  }

  public InheritanceType getInheritanceType() {
    return inheritanceType;
  }

  public void setInheritanceType(InheritanceType inheritanceType) {
    this.inheritanceType = inheritanceType;
  }

  public String getDiscColumn() {
    if (null == cfBaseMapDef) {
      return discColumn;
    } else {
      return cfBaseMapDef.getDiscColumn();
    }
  }

  public void setDiscColumn(String discColumn) {
    this.discColumn = discColumn;
  }

  public DiscriminatorType getDiscType() {
    if (null == cfBaseMapDef) {
      return discType;
    } else {
      return cfBaseMapDef.getDiscType();
    }
  }

  public void setDiscType(DiscriminatorType discType) {
    this.discType = discType;
  }

  public Object getDiscValue() {
    return discValue;
  }

  public void setDiscValue(Object discValue) {
    this.discValue = discValue;
  }

  public Set<PropertyMappingDefinition<I>> getIdPropertySet() {
    if (null == cfBaseMapDef) {
      return idPropertySet;
    } else {
      return cfBaseMapDef.getIdPropertySet();
    }
  }

  public void addIdPropertyMap(PropertyMappingDefinition<I> idProperty) {
    idPropertySet.add(idProperty);
  }

  @SuppressWarnings("unchecked")
  public Collection<PropertyMappingDefinition<?>> getAllProperties() {
    if (null == allMappedProps) {
      Set<PropertyMappingDefinition<?>> propSet = new HashSet<PropertyMappingDefinition<?>>();
      for (PropertyMappingDefinition<?> propMapDef : propertyCacheByColName.values()) {
        propSet.add(propMapDef);
      }

      if (null == cfSuperMapDef) {
        allMappedProps = propSet;
      } else {
        allMappedProps = new CompositeCollection(
            new Collection[] { propSet, cfSuperMapDef.getAllProperties() });
      }
    }

    return allMappedProps;
  }

  public CFMappingDef<? super T, I> getCfBaseMapDef() {
    return cfBaseMapDef;
  }

  public void setCfBaseMapDef(CFMappingDef<? super T, I> cfBaseMapDef) {
    this.cfBaseMapDef = cfBaseMapDef;
  }

  public void setColFamName(String colFamName) {
    this.colFamName = colFamName;
  }

  public Map<Object, CFMappingDef<? extends T, I>> getDerivedClassMap() {
    return derivedClassMap;
  }

  public CFMappingDef<? super T, I> getCfSuperMapDef() {
    return cfSuperMapDef;
  }

  public void setCfSuperMapDef(CFMappingDef<? super T, I> cfSuperMapDef) {
    this.cfSuperMapDef = cfSuperMapDef;
  }

  public Class<T> getRealClass() {
    return realClass;
  }

  @Override
  public String toString() {
    return "CFMappingDef [colFamName=" + colFamName + ", realClass=" + realClass
        + ", effectiveClass=" + effectiveClass + "]";
  }

  public boolean isBaseInheritanceClass() {
    return null != inheritanceType;
  }

  public boolean isDerivedClassInheritance() {
    return !isBaseInheritanceClass() && null != getDiscValue();
  }

  public boolean isStandaloneClass() {
    return !isBaseInheritanceClass() && !isDerivedClassInheritance();
  }
}
