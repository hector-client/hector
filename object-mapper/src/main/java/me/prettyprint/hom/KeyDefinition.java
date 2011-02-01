package me.prettyprint.hom;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;

import me.prettyprint.hom.cache.HectorObjectMapperException;

/**
 * Defines properties used for representing and constructing a cassandra key and
 * mapping to/from POJO property(ies).
 * 
 * @author B. Todd Burruss
 */
public class KeyDefinition {
  private Map<String, PropertyMappingDefinition> idPropertyMap = new LinkedHashMap<String, PropertyMappingDefinition>();
  private Class<?> pkClazz;
  private Map<String, PropertyDescriptor> propertyDescriptorMap;

  public void addIdPropertyMap(PropertyMappingDefinition idProperty) {
    idPropertyMap.put(idProperty.getPropDesc().getName(), idProperty);
  }

  public void setPkClass(Class<?> pkClazz) {
    this.pkClazz = pkClazz;
    if (null == this.pkClazz) {
      throw new IllegalArgumentException("Primary Key Class cannot be null");
    }

    PropertyDescriptor[] pdArr;
    try {
      pdArr = Introspector.getBeanInfo(pkClazz, Object.class).getPropertyDescriptors();
    } catch (IntrospectionException e) {
      throw new HectorObjectMapperException(e);
    }

    propertyDescriptorMap = new LinkedHashMap<String, PropertyDescriptor>();
    for ( PropertyDescriptor pd : pdArr) {
      propertyDescriptorMap.put(pd.getName(), pd);
    }
  }

  public Class<?> getPkClazz() {
    return pkClazz;
  }

  public Map<String, PropertyMappingDefinition> getIdPropertyMap() {
    return idPropertyMap;
  }

  public Map<String, PropertyDescriptor> getPropertyDescriptorMap() {
    return propertyDescriptorMap;
  }

  /**
   * Determines if the key is complex (IdClass, Embedded, etc) or a simple one field type.
   *
   * @return true if complex, false otherwise
   */
  public boolean isComplexKey() {
    return null != getPkClazz();
  }

  /**
   * Determines if a simple ID is present.
   *
   * @return true if the key is not a complex ID and at least one field was annotated with @Id.
   */
  public boolean isSimpleIdPresent() {
    return !isComplexKey() && !idPropertyMap.isEmpty();
  }

}
