package me.prettyprint.hom;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hom.cache.HectorObjectMapperException;

import com.google.common.base.Splitter;

public class CollectionMapperHelper {
  private ReflectionHelper reflectionHelper = new ReflectionHelper();
  private ObjectSerializer objSer = ObjectSerializer.get();

  public String createCollectionItemColName(String propName, int order) {
    return propName + ":" + order;
  }

  public CollectionItemColName parseCollectionItemColName(String colName) {
    try {
      Iterable<String> split = Splitter.on(':').split(colName);
      Iterator<String> iter = split.iterator();
      return new CollectionItemColName(iter.next(), Integer.parseInt(iter.next()));
    } catch (Throwable e) {
      throw new HectorObjectMapperException("exception while parsing collection item column name, "
          + colName, e);
    }
  }

  public byte[] createCollectionInfoColValue(Collection<Object> coll) {
    // translate some classes that don't make as much sense when loaded
    String className = coll.getClass().getName();
    if ( className.endsWith("$SingletonList")) {
      className = "java.util.ArrayList";
    }
    else if ( className.endsWith("$SingletonMap")) {
      className = "java.util.HashMap";
    }
    else if ( className.endsWith("$SingletonSet")) {
      className = "java.util.HashSet";
    }
    return String.valueOf(className + ":" + coll.size()).getBytes();
  }

  public CollectionInfoColValue parseCollectionInfoColValue(byte[] val) {
    try {
      String tmp = new String(val);
      Iterable<String> split = Splitter.on(':').split(tmp);
      Iterator<String> iter = split.iterator();
      String className = iter.next();
      return new CollectionInfoColValue(className, Integer.parseInt(iter.next()));
    } catch (Throwable e) {
      throw new HectorObjectMapperException("exception while parsing collection info column value",
          e);
    }
  }

  @SuppressWarnings("rawtypes")
  public void instantiateCollection(Object obj, HColumn<String, byte[]> col,
      PropertyMappingDefinition md) {
    CollectionInfoColValue colValue = parseCollectionInfoColValue(col.getValue());
    try {
      // type name is Class that needs instantiating. examine for special class
      // features, like ArrayList taking a size hint in constructor
      Collection<?> collObj;
      Class clazz = Class.forName(colValue.getCollTypeName());
      if (ArrayList.class.isAssignableFrom(clazz)) {
        Constructor<ArrayList> cons = ArrayList.class.getConstructor(int.class);
        collObj = cons.newInstance(colValue.getSize());
      } else {
        collObj = (Collection<?>) clazz.newInstance();
      }

      // now save collection to object
      PropertyDescriptor pd = md.getPropDesc();
      if (null == pd.getWriteMethod()) {
        throw new RuntimeException("property, " + pd.getName()
            + ", does not have a setter and therefore cannot be set");
      }

      pd.getWriteMethod().invoke(obj, collObj);
    } catch (Throwable e) {
      throw new HectorObjectMapperException("exception while instantiating Collection type, "
          + colValue.getCollTypeName(), e);
    }
  }

  @SuppressWarnings("unchecked")
  public boolean addColumnToCollection(CFMappingDef<?> cfMapDef, Object obj, String colName,
      byte[] colValue) {
    // if can parse, then at least adheres to formatting
    CollectionItemColName collColumnName;
    try {
      collColumnName = parseCollectionItemColName(colName);
    } catch (HectorObjectMapperException e) {
      return false;
    }

    // get property from mapping def - if not there, then isn't a collection
    // (but probably a problem elsewhere)
    PropertyMappingDefinition md = cfMapDef.getPropMapByColumnName(collColumnName.getPropertyName());
    if (null == md) {
      return false;
    }

    Collection<Object> coll;
    try {
      coll = (Collection<Object>) reflectionHelper.invokeGetter(obj, md);
    } catch (HectorObjectMapperException e) {
      return false;
    }

    Object value = deserializeCollectionValue(colValue);
    coll.add(value);
    return true;
  }

  public byte[] serializeCollectionValue(Object obj) {
    return objSer.toBytes(obj);
  }

  public Object deserializeCollectionValue(byte[] bytes) {
    return objSer.fromBytes(bytes);
  }

  // ------------------------------------------------

  public class CollectionItemColName {
    private String propertyName;
    private int order;

    public CollectionItemColName(String propertyName, int order) {
      this.propertyName = propertyName;
      this.order = order;
    }

    public String getPropertyName() {
      return propertyName;
    }

    public int getOrder() {
      return order;
    }

    private CollectionMapperHelper getOuterType() {
      return CollectionMapperHelper.this;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + order;
      result = prime * result + ((propertyName == null) ? 0 : propertyName.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      CollectionItemColName other = (CollectionItemColName) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (order != other.order)
        return false;
      if (propertyName == null) {
        if (other.propertyName != null)
          return false;
      } else if (!propertyName.equals(other.propertyName))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CollectionItemColName [propertyName=" + propertyName + ", order=" + order + "]";
    }
  }

  public class CollectionInfoColValue {
    private String collTypeName;
    private int size;

    public CollectionInfoColValue(String collTypeName, int size) {
      this.collTypeName = collTypeName;
      this.size = size;
    }

    public String getCollTypeName() {
      return collTypeName;
    }

    public int getSize() {
      return size;
    }

    private CollectionMapperHelper getOuterType() {
      return CollectionMapperHelper.this;
    }

    @Override
    public String toString() {
      return "CollectionInfoColValue [collTypeName=" + collTypeName + ", size=" + size + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + ((collTypeName == null) ? 0 : collTypeName.hashCode());
      result = prime * result + size;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      CollectionInfoColValue other = (CollectionInfoColValue) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (collTypeName == null) {
        if (other.collTypeName != null)
          return false;
      } else if (!collTypeName.equals(other.collTypeName))
        return false;
      if (size != other.size)
        return false;
      return true;
    }
  }
}

