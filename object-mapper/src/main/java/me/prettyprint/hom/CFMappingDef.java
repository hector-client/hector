package me.prettyprint.hom;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.InheritanceType;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.cache.HectorObjectMapperException;

import com.google.common.collect.Sets;

/**
 * Holder for the mapping between a Class annotated with {@link Entity} and the
 * Cassandra column family name.
 * 
 * @author Todd Burruss
 * 
 * @param <T>
 */
public class CFMappingDef<T> {
  private Class<T> realClass;
  private Class<T> effectiveClass;
  private CFMappingDef<? super T> cfBaseMapDef;
  private CFMappingDef<? super T> cfSuperMapDef;
  private String colFamName;
  private InheritanceType inheritanceType;
  private String discColumn;
  private DiscriminatorType discType;
  private Object discValue; // this can be a variety of types

  private Method anonymousPropertyAddHandler;
  private Method anonymousPropertyGetHandler;
  private Class<?> anonymousValueType;
  @SuppressWarnings("rawtypes")
  private Serializer anonymousValueSerializer;

  private String[] sliceColumnNameArr;
  private KeyDefinition keyDef;

  private Map<Object, CFMappingDef<? extends T>> derivedClassMap = new HashMap<Object, CFMappingDef<? extends T>>();
  private Collection<PropertyMappingDefinition> allMappedProps;

  // provide caching by class object for the class' mapping definition
  private Map<String, PropertyMappingDefinition> propertyCacheByPropName = new HashMap<String, PropertyMappingDefinition>();

  // provide caching by class object for the class' mapping definition
  private Map<String, PropertyMappingDefinition> propertyCacheByColName = new HashMap<String, PropertyMappingDefinition>();

  public CFMappingDef(Class<T> clazz) {
    setDefaults(clazz);
  }

  /**
   * Setup mapping with defaults for the given class. Does not parse all
   * annotations.
   * 
   * @param realClass
   */
  @SuppressWarnings("unchecked")
  public void setDefaults(Class<T> realClass) {
    this.realClass = realClass;
    this.keyDef = new KeyDefinition();

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
      throw new HomMissingEntityAnnotationException("class, " + realClass.getName()
          + ", not annotated with @" + Entity.class.getSimpleName());
    }
  }

  public boolean isColumnSliceRequired() {
    return !isAnonymousHandlerAvailable() && !isAnyCollections() && !isAbstract()
        && !isDerivedEntity();
  }

  public boolean isAnyCollections() {
    if (null == getAllProperties()) {
      return false;
    }

    for (PropertyMappingDefinition md : getAllProperties()) {
      if (md.isCollectionType()) {
        return true;
      }
    }
    return false;
  }

  public void addDerivedClassMap(CFMappingDef<? extends T> cfDerivedMapDef) {
    derivedClassMap.put(cfDerivedMapDef.getDiscValue(), cfDerivedMapDef);
  }

  public PropertyMappingDefinition getPropMapByPropName(String propName) {
    return propertyCacheByPropName.get(propName);
  }

  public PropertyMappingDefinition getPropMapByColumnName(String colName) {
    PropertyMappingDefinition md = propertyCacheByColName.get(colName);
    if (null != md) {
      return md;
    } else if (null != cfSuperMapDef) {
      return cfSuperMapDef.getPropMapByColumnName(colName);
    } else {
      return null;
    }
  }

  public void addPropertyDefinition(PropertyMappingDefinition propDef) {
    propertyCacheByColName.put(propDef.getColName(), propDef);
    propertyCacheByPropName.put(propDef.getPropDesc().getName(), propDef);
  }

  public String getColFamName() {
    return colFamName;
  }

  public String getEffectiveColFamName() {
    if (null != colFamName) {
      return colFamName;
    } else if (null != cfBaseMapDef) {
      return cfBaseMapDef.getColFamName();
    } else {
      throw new HectorObjectMapperException(
          "trying to get ColumnFamily name, but is missing for mapping, " + this.toString());
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

  public KeyDefinition getKeyDef() {
    if (null == cfBaseMapDef) {
      return keyDef;
    } else {
      return cfBaseMapDef.getKeyDef();
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Collection<PropertyMappingDefinition> getAllProperties() {
    if (null == allMappedProps) {
      Set<PropertyMappingDefinition> propSet = new HashSet<PropertyMappingDefinition>();
      for (PropertyMappingDefinition propMapDef : propertyCacheByColName.values()) {
        propSet.add(propMapDef);
      }

      if (null == cfSuperMapDef) {
        allMappedProps = propSet;
      } else {
        allMappedProps = Sets.union(propSet, (Set) cfSuperMapDef.getAllProperties());
      }
    }

    return allMappedProps;
  }

  public CFMappingDef<? super T> getCfBaseMapDef() {
    return cfBaseMapDef;
  }

  public void setCfBaseMapDef(CFMappingDef<? super T> cfBaseMapDef) {
    this.cfBaseMapDef = cfBaseMapDef;
  }

  public void setColFamName(String colFamName) {
    this.colFamName = colFamName;
  }

  public Map<Object, CFMappingDef<? extends T>> getDerivedClassMap() {
    return derivedClassMap;
  }

  public CFMappingDef<? super T> getCfSuperMapDef() {
    return cfSuperMapDef;
  }

  public void setCfSuperMapDef(CFMappingDef<? super T> cfSuperMapDef) {
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

  public boolean isAbstract() {
    return Modifier.isAbstract(effectiveClass.getModifiers());
  }

  public boolean isBaseEntity() {
    return null != inheritanceType;
  }

  public boolean isPersistableEntity() {
    return !isAbstract();
  }

  public boolean isPersistableDerivedEntity() {
    return !isBaseEntity() && null != getDiscValue() && !isAbstract();
  }

  public boolean isNonPersistableDerivedEntity() {
    return !isBaseEntity() && !isPersistableDerivedEntity() && isAbstract();
  }

  public boolean isDerivedEntity() {
    return isPersistableDerivedEntity() || isNonPersistableDerivedEntity();
  }

  public String[] getSliceColumnNameArr() {
    return sliceColumnNameArr;
  }

  public void setSliceColumnNameArr(String[] sliceColumnNameArr) {
    this.sliceColumnNameArr = sliceColumnNameArr;
  }

  public Method getAnonymousPropertyAddHandler() {
    if (null != anonymousPropertyAddHandler) {
      return anonymousPropertyAddHandler;
    } else if (null != cfSuperMapDef) {
      return cfSuperMapDef.getAnonymousPropertyAddHandler();
    } else {
      return null;
    }
  }

  public Method getAnonymousPropertyGetHandler() {
    if (null != anonymousPropertyGetHandler) {
      return anonymousPropertyGetHandler;
    } else if (null != cfSuperMapDef) {
      return cfSuperMapDef.getAnonymousPropertyGetHandler();
    } else {
      return null;
    }
  }

  public Class<?> getAnonymousValueType() {
    if (null != anonymousValueType) {
      return anonymousValueType;
    } else if (null != cfSuperMapDef) {
      return cfSuperMapDef.getAnonymousValueType();
    } else {
      return null;
    }
  }

  @SuppressWarnings("rawtypes")
  public Serializer getAnonymousValueSerializer() {
    if (null != anonymousValueSerializer) {
      return anonymousValueSerializer;
    } else if (null != cfSuperMapDef) {
      return cfSuperMapDef.getAnonymousValueSerializer();
    } else {
      return null;
    }

  }

  public void setAnonymousValueType(Class<?> anonymousValueType) {
    this.anonymousValueType = anonymousValueType;
  }

  public void setAnonymousValueSerializer(
      @SuppressWarnings("rawtypes") Serializer anonymousValueSerializer) {
    this.anonymousValueSerializer = anonymousValueSerializer;
  }

  public void setAnonymousPropertyAddHandler(Method anonymousPropertyAddHandler) {
    this.anonymousPropertyAddHandler = anonymousPropertyAddHandler;
  }

  public boolean isAnonymousHandlerAvailable() {
    return null != anonymousValueSerializer;
  }

  //
  // public boolean isSliceColumnArrayRequired() {
  // return null != sliceColumnNameArr && 0 < sliceColumnNameArr.length;
  // }

  public Collection<PropertyMappingDefinition> getCollectionProperties() {
    Set<PropertyMappingDefinition> collSet = new HashSet<PropertyMappingDefinition>();
    for (PropertyMappingDefinition md : getAllProperties()) {
      if (md.isCollectionType()) {
        collSet.add(md);
      }
    }

    return collSet;
  }

  public void setAnonymousPropertyGetHandler(Method anonymousPropertyGetHandler) {
    this.anonymousPropertyGetHandler = anonymousPropertyGetHandler;
  }
}
