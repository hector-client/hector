package me.prettyprint.hom;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.persistence.DiscriminatorType;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.DoubleSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hom.annotations.AnonymousPropertyAddHandler;
import me.prettyprint.hom.annotations.AnonymousPropertyCollectionGetter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps a slice of <code>HColumn<String, byte[]></code>s to an object's
 * properties. See {@link #createObject(Object, ColumnSlice)} for more details.
 * <p/>
 * As mentioned above all column names must be <code>String</code>s - doesn't
 * really make sense to have other types when mapping to object properties.
 * 
 * @param <T>
 *          Type of object mapping to cassandra row
 * 
 * @author Todd Burruss
 */
public class HectorObjectMapper {
  private static Logger logger = LoggerFactory.getLogger(HectorObjectMapper.class);

  private static final int MAX_NUM_COLUMNS = 100;

  private int maxNumColumns = MAX_NUM_COLUMNS;
  private ClassCacheMgr cacheMgr;

  public HectorObjectMapper(ClassCacheMgr cacheMgr) {
    this.cacheMgr = cacheMgr;
  }

  /**
   * Retrieve columns from cassandra keyspace and column family, instantiate a
   * new object of required type, and then map them to the object's properties.
   * 
   * @param keyspace
   * @param colFamName
   * @param id
   * @return
   */
  public <T, I> T getObject(Keyspace keyspace, String colFamName, Class<T> clazz, I id) {
    if (null == id) {
      throw new IllegalArgumentException("object ID cannot be null or empty");
    }

    CFMappingDef<T, I> cfMapDef = cacheMgr.getCfMapDef(colFamName, true);
    PropertyMappingDefinition<I> md = cfMapDef.getIdPropertyDef();
    if (null == md) {
      throw new IllegalStateException(
          "Trying to build new object but haven't annotated a field with @Id");
    }

    byte[] idAsBytes = md.getConverter().convertObjTypeToCassType(id);

    SliceQuery<byte[], String, byte[]> q = HFactory.createSliceQuery(keyspace,
        BytesArraySerializer.get(), StringSerializer.get(), BytesArraySerializer.get());
    q.setColumnFamily(colFamName);
    q.setKey(idAsBytes);
    q.setRange("", "", false, maxNumColumns);
    QueryResult<ColumnSlice<String, byte[]>> result = q.execute();
    if (null == result || null == result.get()) {
      return null;
    }

    T obj = createObject(cfMapDef, id, result.get());
    return obj;
  }

  public <T, I> T saveObj(Keyspace keyspace, T obj) {
    if (null == obj) {
      throw new IllegalArgumentException("object cannot be null");
    }

    @SuppressWarnings("unchecked")
    CFMappingDef<T, I> cfMapDef = (CFMappingDef<T, I>) cacheMgr.getCfMapDef(obj.getClass(), true);
    PropertyMappingDefinition<I> md = cfMapDef.getIdPropertyDef();
    if (null == md) {
      throw new IllegalStateException(
          "Trying to save object but haven't annotated a field with @Id");
    }

    Method meth = md.getPropDesc().getReadMethod();
    if (null == meth) {
      logger.debug("@Id annotation found - but can't find getter for property, "
          + md.getPropDesc().getName());
    }

    byte[] bytes;
    try {
      @SuppressWarnings("unchecked")
      I retVal = (I) meth.invoke(obj, (Object[]) null);
      bytes = md.getConverter().convertObjTypeToCassType(retVal);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }

    if (null == bytes) {
      throw new IllegalArgumentException("object ID cannot be null or empty");
    }

    Collection<HColumn<String, byte[]>> colColl = createColumnSet(obj);

    String colFamName = cfMapDef.getColFamName();
    Mutator<byte[]> m = HFactory.createMutator(keyspace, BytesArraySerializer.get());
    for (HColumn<String, byte[]> col : colColl) {
      m.addInsertion(bytes, colFamName, col);
    }

    m.execute();

    return obj;
  }

  /**
   * Given a column slice from Hector/Cassandra and a type, this method
   * instantiates the object and sets the properties on the object using the
   * slice data. If a column doesn't map to an specific property in the object,
   * it will see if the object has implemented the interface,
   * {@link HectorExtraProperties}. If so call
   * {@link HectorExtraProperties#addExtraProperty(String, String)}, on the
   * object.
   * 
   * @param id
   *          ID (row key) of the object we are retrieving from Cassandra
   * @param clazz
   *          type of object to instantiate and populate
   * @param slice
   *          column slice from Hector of type
   *          <code>ColumnSlice<String, byte[]></code>
   * 
   * @return instantiated object if success, null if slice is empty,
   *         RuntimeException otherwise
   */
  <T, I> T createObject(CFMappingDef<T, I> cfMapDef, I id, ColumnSlice<String, byte[]> slice) {
    if (slice.getColumns().isEmpty()) {
      return null;
    }

    CFMappingDef<? extends T, I> cfMapDefInstance = determineClassType(cfMapDef, slice);

    try {
      T obj = cfMapDefInstance.getClazz().newInstance();

      setIdIfCan(cfMapDef, obj, id);

      for (HColumn<String, byte[]> col : slice.getColumns()) {
        String colName = col.getName();
        PropertyMappingDefinition<?> md = cfMapDefInstance.getPropMapByColumnName(colName);
        if (null != md && null != md.getPropDesc()) {
          setPropertyUsingColumn(obj, col, md);
        }
        // if this is a derived class then don't need to save disc
        // column value
        else if (null != cfMapDef.getDiscColumn() && colName.equals(cfMapDef.getDiscColumn())) {
          continue;
        } else {
          addToExtraIfCan(obj, col);
        }
      }

      return obj;
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Create Set of HColumns for the given Object. The Object must be annotated
   * with {@link Column} on the desired fields.
   * 
   * @param obj
   * @return
   */
  private Collection<HColumn<String, byte[]>> createColumnSet(Object obj) {
    Map<String, HColumn<String, byte[]>> map = createColumnMap(obj);
    if (null != map) {
      return map.values();
    } else {
      return null;
    }
  }

  /**
   * Creates a Map of property names as key and HColumns as value. See #
   * 
   * @param obj
   * @return
   */
  <T, I> Map<String, HColumn<String, byte[]>> createColumnMap(T obj) {
    if (null == obj) {
      throw new IllegalArgumentException("Class type cannot be null");
    }

    @SuppressWarnings("unchecked")
    CFMappingDef<T, I> cfMapDef = (CFMappingDef<T, I>) cacheMgr.getCfMapDef(
        (Class<T>) obj.getClass(), true);
    try {
      Map<String, HColumn<String, byte[]>> colSet = new HashMap<String, HColumn<String, byte[]>>();
      Collection<PropertyMappingDefinition<?>> coll = cfMapDef.getAllProperties();
      for (PropertyMappingDefinition<?> md : coll) {
        HColumn<String, byte[]> col = createColumnFromProperty(obj, md);
        if (null != col) {
          colSet.put(col.getName(), col);
        }
      }

      if (null != cfMapDef.getCfBaseMapDef()) {
        CFMappingDef<?, I> cfSuperMapDef = cfMapDef.getCfBaseMapDef();
        String discColName = cfSuperMapDef.getDiscColumn();
        DiscriminatorType discType = cfSuperMapDef.getDiscType();

        colSet.put(discColName, createHColumn(discColName, convertDiscTypeToColValue(discType,
            cfMapDef.getDiscValue())));
      }

      addAnonymousProperties(obj, colSet);
      return colSet;
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private void addAnonymousProperties(Object obj, Map<String, HColumn<String, byte[]>> colSet)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    Method meth = findAnnotatedMethod(obj.getClass(), AnonymousPropertyCollectionGetter.class);
    if (null == meth) {
      return;
    }

    @SuppressWarnings("unchecked")
    Collection<Entry<String, String>> propColl = (Collection<Entry<String, String>>) meth.invoke(
        obj, (Object[]) null);
    if (null == propColl || propColl.isEmpty()) {
      return;
    }

    for (Entry<String, String> entry : propColl) {
      colSet.put(entry.getKey(), HFactory.createColumn(entry.getKey(), entry.getValue().getBytes(),
          StringSerializer.get(), BytesArraySerializer.get()));
    }
  }

  private <T, P> HColumn<String, byte[]> createColumnFromProperty(T obj,
      PropertyMappingDefinition<P> md) throws SecurityException, NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    byte[] colValue = createBytesFromPropertyValue(obj, md);
    if (null == colValue) {
      return null;
    }
    HColumn<String, byte[]> col = createHColumn(md.getColName(), colValue);
    return col;
  }

  private <P> byte[] createBytesFromPropertyValue(Object obj, PropertyMappingDefinition<P> md)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    PropertyDescriptor pd = md.getPropDesc();
    Method getter = pd.getReadMethod();
    if (null == getter) {
      throw new RuntimeException("missing getter method for property, " + pd.getName());
    }

    @SuppressWarnings("unchecked")
    P retVal = (P) getter.invoke(obj, (Object[]) null);

    // if no value, then signal with null bytes
    if (null == retVal) {
      return null;
    }

    byte[] bytes = md.getConverter().convertObjTypeToCassType(retVal);
    return bytes;
  }

  private HColumn<String, byte[]> createHColumn(String name, byte[] value) {
    return HFactory.createColumn(name, value, StringSerializer.get(), BytesArraySerializer.get());
  }

  private <T, I> CFMappingDef<? extends T, I> determineClassType(CFMappingDef<T, I> cfMapDef,
      ColumnSlice<String, byte[]> slice) {
    if (null == cfMapDef.getInheritanceType()) {
      return cfMapDef;
    }

    // if no columns we assume base class
    if (null == slice || null == slice.getColumns() || slice.getColumns().isEmpty()) {
      return cfMapDef;
    }

    // only support single table so use discriminator information

    String discColName = cfMapDef.getDiscColumn();
    DiscriminatorType discType = cfMapDef.getDiscType();
    Map<Object, CFMappingDef<? extends T, I>> derivedClasses = cfMapDef.getDerivedClassMap();

    // search for
    HColumn<String, byte[]> discCol = null;
    for (HColumn<String, byte[]> col : slice.getColumns()) {
      if (col.getName().equals(discColName)) {
        discCol = col;
        break;
      }
    }

    // if not found or empty value, then indicates use base class
    // TODO:BTB check for abstract base before allowing this
    if (null == discCol || 0 == discCol.getValue().length) {
      return cfMapDef;
    }

    // if discriminator column found, then lookup class type by
    // discriminator value
    Object discValue = convertColValueToDiscType(discType, discCol.getValue());
    CFMappingDef<? extends T, I> derivedCfMapDef = derivedClasses.get(discValue);
    if (null == derivedCfMapDef) {
      throw new RuntimeException("Cannot find derived class of " + cfMapDef.getClazz().getName()
          + " with discriminator value of " + discValue);
    }

    return derivedCfMapDef;
  }

  private Object convertColValueToDiscType(DiscriminatorType discType, byte[] value) {
    switch (discType) {
    case STRING:
      return new String(value);
    case CHAR:
      return ByteBuffer.wrap(value).asCharBuffer().get();
    case INTEGER:
      return IntegerSerializer.get().fromBytes(value);
    }

    throw new RuntimeException("must have added a new discriminator type, " + discType
        + ", because don't know how to convert db value - cannot continue");
  }

  private byte[] convertDiscTypeToColValue(DiscriminatorType discType, Object value) {
    switch (discType) {
    case STRING:
      return StringSerializer.get().toBytes((String) value);
    case CHAR:
      return String.valueOf((Character) value).getBytes();
    case INTEGER:
      return IntegerSerializer.get().toBytes((Integer) value);
    }

    throw new RuntimeException("must have added a new discriminator type, " + discType
        + ", because don't know how to convert db value - cannot continue");
  }

  private <T, I> void setIdIfCan(CFMappingDef<T, I> cfMapDef, T obj, I id)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    PropertyMappingDefinition<I> md = cfMapDef.getIdPropertyDef();
    if (null == md) {
      throw new IllegalStateException(
          "Trying to build new object but haven't annotated a field with @Id");
    }

    Method meth = md.getPropDesc().getWriteMethod();
    if (null == meth) {
      logger.debug("@Id annotation found - but can't find setter for property, "
          + md.getPropDesc().getName());
    }

    meth.invoke(obj, id);
  }

  private <T, P> void setPropertyUsingColumn(T obj, HColumn<String, byte[]> col,
      PropertyMappingDefinition<P> md) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    PropertyDescriptor pd = md.getPropDesc();
    if (null == pd.getWriteMethod()) {
      throw new RuntimeException("property, " + pd.getName()
          + ", does not have a setter and therefore cannot be set");
    }

    @SuppressWarnings("unchecked")
    P value = md.getConverter().convertCassTypeToObjType((Class<P>) pd.getPropertyType(),
        col.getValue());
    pd.getWriteMethod().invoke(obj, value);
  }

  public static Serializer<?> determineSerializer(Class<?> theType) {
    Serializer<?> s = null;
    if (theType == Long.class || theType == long.class) {
      s = LongSerializer.get();
    } else if (theType == String.class) {
      s = StringSerializer.get();
    } else if (theType == Integer.class || theType == int.class) {
      s = IntegerSerializer.get();
    } else if (theType == UUID.class) {
      s = UUIDSerializer.get();
    } else if (theType == Boolean.class || theType == boolean.class) {
      s = BooleanSerializer.get();
    } else if (theType == Date.class) {
      s = DateSerializer.get();
    } else if (theType == byte[].class) {
      s = BytesArraySerializer.get();
    }
    // no float serializer at the moment
    // else if ( theType== Float.class) {
    // s = FloatSerializer.get();
    // }
    else if (theType == Double.class || theType == double.class) {
      s = DoubleSerializer.get();
    } else if (isSerializable(theType)) {
      s = ObjectSerializer.get();
    } else {
      throw new RuntimeException("unsupported property type, " + theType.getName());
    }
    return s;
  }

  public static boolean isSerializable(Class<?> clazz) {
    return isImplementedBy(clazz, Serializable.class);
  }

  public static boolean isImplementedBy(Class<?> clazz, Class<?> target) {
    if (null == clazz || null == target) {
      return false;
    }

    Class<?>[] interArr = clazz.getInterfaces();
    if (null == interArr) {
      return false;
    }

    for (Class<?> interfa : interArr) {
      if ( interfa.equals(target)) {
        return true;
      }
    }
    return false;
  }

  private void addToExtraIfCan(Object obj, HColumn<String, byte[]> col) throws SecurityException,
      NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    Method meth = findAnnotatedMethod(obj.getClass(), AnonymousPropertyAddHandler.class);
    if (null == meth) {
      throw new IllegalArgumentException(
          "Object type, "
              + obj.getClass()
              + ", does not have a property named, "
              + col.getName()
              + ".  either add a setter for this property or use @AnonymousPropertyHandler to annotate a method for handling anonymous properties");
    }

    meth.invoke(obj, col.getName(), StringSerializer.get().fromBytes(col.getValue()));
  }

  private Method findAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> anno) {
    for (Method meth : clazz.getMethods()) {
      if (meth.isAnnotationPresent(anno)) {
        return meth;
      }
    }
    return null;
  }

}
