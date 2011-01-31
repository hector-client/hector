package me.prettyprint.hom;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Defines properties used for representing and constructing a cassandra key and mapping to/from POJO property(ies).  
 *
 * @author B. Todd Burruss
 */
public class KeyDefinition {
  private Map<String, PropertyMappingDefinition> idPropertyMap = new LinkedHashMap<String, PropertyMappingDefinition>();
  private Class<?> pkClazz;

  public void addIdPropertyMap(PropertyMappingDefinition idProperty) {
    idPropertyMap.put(idProperty.getPropDesc().getName(), idProperty);
  }

  public void setPkClass(Class<?> pkClazz) {
    this.pkClazz = pkClazz;
  }

  public Class<?> getPkClazz() {
    return pkClazz;
  }

  public Map<String, PropertyMappingDefinition> getIdPropertyMap() {
    return idPropertyMap;
  }

}
