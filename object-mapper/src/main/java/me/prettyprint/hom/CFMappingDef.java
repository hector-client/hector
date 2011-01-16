package me.prettyprint.hom;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorType;
import javax.persistence.InheritanceType;

import org.apache.commons.collections.collection.CompositeCollection;


/**
 * Holder for the mapping between a Class annotated with {@link Entity} and the
 * Cassandra column family name.
 * 
 * @author Todd Burruss
 */
public class CFMappingDef<T, I> {
  private Class<T> clazz;
  private CFMappingDef<? super T, I> cfBaseMapDef;
  private CFMappingDef<? super T, I> cfSuperMapDef;
  private String colFamName;
  private InheritanceType inheritanceType;
  private String discColumn;
  private DiscriminatorType discType;
  private Object discValue; // this can be a variety of types
  private PropertyMappingDefinition<I> idPropertyMap;

  private Map<Object, CFMappingDef<? extends T, I>> derivedClassMap = new HashMap<Object, CFMappingDef<? extends T, I>>();
  private Collection<PropertyMappingDefinition<?>> allMappedProps;

  // provide caching by class object for the class' mapping definition
  private Map<String, PropertyMappingDefinition<?>> propertyCacheByPropName =
    new HashMap<String, PropertyMappingDefinition<?>>();

  // provide caching by class object for the class' mapping definition
  private Map<String, PropertyMappingDefinition<?>> propertyCacheByColName =
    new HashMap<String, PropertyMappingDefinition<?>>();

  public CFMappingDef(Class<T> clazz) {
    this.clazz = clazz;
  }

  public void addDerivedClassMap(CFMappingDef<? extends T, I> cfDerivedMapDef) {
    derivedClassMap.put(cfDerivedMapDef.getDiscValue(), cfDerivedMapDef);
  }

  public PropertyMappingDefinition<?> getPropMapByPropName(String propName) {
    return propertyCacheByPropName.get(propName);
  }

  public PropertyMappingDefinition<?> getPropMapByColumnName(String colName) {
    PropertyMappingDefinition<?> md = propertyCacheByColName.get(colName);
    if ( null != md ) {
      return md;
    }
    else if ( null != cfSuperMapDef ) {
      return cfSuperMapDef.getPropMapByColumnName(colName);
    }
    else {
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

  public Class<T> getClazz() {
    return clazz;
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
    }
    else {
      return cfBaseMapDef.getDiscColumn();
    }
  }

  public void setDiscColumn(String discColumn) {
    this.discColumn = discColumn;
  }

  public DiscriminatorType getDiscType() {
    if (null == cfBaseMapDef) {
      return discType;
    }
    else {
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

  public PropertyMappingDefinition<I> getIdPropertyDef() {
    if (null == cfBaseMapDef) {
      return idPropertyMap;
    }
    else {
      return cfBaseMapDef.getIdPropertyDef();
    }
  }

  public void setIdPropertyMap(PropertyMappingDefinition<I> idPropertyMap) {
    this.idPropertyMap = idPropertyMap;
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
      }
      else {
        allMappedProps = new CompositeCollection(new Collection[] {
            propSet, cfSuperMapDef.getAllProperties() });
      }
    }

    return allMappedProps;
  }

  public CFMappingDef<? super T, I> getCfBaseMapDef() {
    return cfBaseMapDef;
  }

  public void setCfBaseMapDef(CFMappingDef<? super T, I> cfSuperMapDef) {
    this.cfBaseMapDef = cfSuperMapDef;
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
}
