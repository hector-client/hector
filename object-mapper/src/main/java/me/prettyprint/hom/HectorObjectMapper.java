package me.prettyprint.hom;


import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.DiscriminatorType;
import javax.persistence.Id;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.cache.HectorObjectMapperException;
import me.prettyprint.hom.converters.Converter;
import me.prettyprint.hom.converters.DefaultConverter;

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
  private KeyConcatenationStrategy keyConcatStrategy = new KeyConcatenationDelimiterStrategyImpl();
  private CollectionMapperHelper collMapperHelper = new CollectionMapperHelper();
  private ReflectionHelper reflectionHelper = new ReflectionHelper();

  public HectorObjectMapper(ClassCacheMgr cacheMgr) {
    this.cacheMgr = cacheMgr;
  }

  /**
   * Retrieve columns from cassandra keyspace and column family, instantiate a
   * new object of required type, and then map them to the object's properties.
   * 
   * @param <T>
   * 
   * @param keyspace
   * @param colFamName
   * @param pkObj
   * @return
   */
  public <T, I> T getObject(Keyspace keyspace, String colFamName, I pkObj) {
    if (null == pkObj) {
      throw new IllegalArgumentException("object ID cannot be null or empty");
    }

    CFMappingDef<T> cfMapDef = cacheMgr.getCfMapDef(colFamName, true);

    byte[] colFamKey = generateColumnFamilyKeyFromPkObj(cfMapDef, pkObj);

    SliceQuery<byte[], String, byte[]> q = HFactory.createSliceQuery(keyspace,
        BytesArraySerializer.get(), StringSerializer.get(), BytesArraySerializer.get());
    q.setColumnFamily(colFamName);
    q.setKey(colFamKey);

    // if no anonymous handler then use specific columns
    if (cfMapDef.isColumnSliceRequired()) {
      q.setColumnNames(cfMapDef.getSliceColumnNameArr());
    } else {
      q.setRange("", "", false, maxNumColumns);
    }

    QueryResult<ColumnSlice<String, byte[]>> result = q.execute();
    if (null == result || null == result.get()) {
      return null;
    }

    T obj = createObject(cfMapDef, pkObj, result.get());
    return obj;
  }

  /**
   * 
   * @param keyspace
   * @param obj
   * @return
   */
  public <T> T saveObj(Keyspace keyspace, T obj) {
    if (null == obj) {
      throw new IllegalArgumentException("object cannot be null");
    }
    Mutator<byte[]> m = HFactory.createMutator(keyspace, BytesArraySerializer.get());
    saveObj(keyspace, m, obj);
    m.execute();
    return obj;
  }

  /**
   * Persists the object collection using a single batch mutate. It is up to the
   * client to partition large collections appropriately so not to cause timeout
   * or buffer overflow issues. The objects can be heterogenous (mapping to
   * multiple column families, etc.)
   * 
   * @param keyspace
   * @param objColl
   * @return
   */
  public Collection<?> saveObjCollection(Keyspace keyspace, Collection<?> objColl) {
    Mutator<byte[]> m = HFactory.createMutator(keyspace, BytesArraySerializer.get());
    Collection<?> retColl = saveObjCollection(keyspace, objColl, m);
    m.execute();
    return retColl;
  }

  public Collection<?> saveObjCollection(Keyspace keyspace, Collection<?> objColl, Mutator<byte[]> m) {
    if (null == objColl) {
      throw new IllegalArgumentException("object cannot be null");
    }
    if (objColl.isEmpty()) {
      return objColl;
    }

    for (Object obj : objColl) {
      saveObj(keyspace, m, obj);
    }
    return objColl;
  }

  private void saveObj(Keyspace keyspace, Mutator<byte[]> m, Object obj) {
    if (null == obj) {
      throw new IllegalArgumentException("object cannot be null");
    }

    @SuppressWarnings("unchecked")
    CFMappingDef<Object> cfMapDef = (CFMappingDef<Object>) cacheMgr.getCfMapDef(obj.getClass(),
        true);

    byte[] colFamKey = generateColumnFamilyKeyFromPojo(obj, cfMapDef);
    String colFamName = cfMapDef.getEffectiveColFamName();

    // if object contains collection, then must delete everything first - easier
    // than reading the row and selectively deleting, which is an alternative if
    // this is too destructive
    if (cfMapDef.isAnyCollections()) {
      m.addDeletion(colFamKey, colFamName);
    }

    // must create the "add" columns after the delete to insure proper
    // processing order
    Collection<HColumn<String, byte[]>> colColl = createColumnSet(obj);

    for (HColumn<String, byte[]> col : colColl) {
      if (null == col.getName() || col.getName().isEmpty()) {
        throw new HectorObjectMapperException(
            "Column name cannot be null or empty - trying to persist to ColumnFamily, "
                + colFamName);
      }
      m.addInsertion(colFamKey, colFamName, col);
    }
  }

  // private void addDeletionsIfNecessary(Keyspace keyspace, CFMappingDef<?>
  // cfMapDef, Object obj, Mutator<byte[]> m ) {
  // // get collection properties and convert to columns of "info" records so we
  // know what can/if be deleted
  // Collection<PropertyMappingDefinition> collColl =
  // cfMapDef.getCollectionProperties();
  //
  //
  // SliceQuery<byte[], String, byte[]> q = HFactory.createSliceQuery(keyspace,
  // BytesArraySerializer.get(), StringSerializer.get(),
  // BytesArraySerializer.get());
  // q.setColumnFamily(cfMapDef.getColFamName());
  // q.setColumnNames(columnNames);
  // q.setKey(key);
  //
  // }

  @SuppressWarnings("unchecked")
  private byte[] generateColumnFamilyKeyFromPkObj(CFMappingDef<?> cfMapDef, Object pkObj) {
    List<byte[]> segmentList = new ArrayList<byte[]>(cfMapDef.getKeyDef().getIdPropertyMap().size());
    
    if (cfMapDef.getKeyDef().isComplexKey()) {	
      Map<String, PropertyDescriptor> propertyDescriptorMap = cfMapDef.getKeyDef().getPropertyDescriptorMap(); 	 
      for (String key : cfMapDef.getKeyDef().getIdPropertyMap().keySet()) {
    	  PropertyDescriptor pd = propertyDescriptorMap.get(key);
    	  segmentList.add(callMethodAndConvertToCassandraType(pkObj, pd.getReadMethod(),
    	            new DefaultConverter()));
      }	 
    } else {
      PropertyMappingDefinition md = cfMapDef.getKeyDef().getIdPropertyMap().values().iterator()
                                             .next();
      segmentList.add(md.getConverter().convertObjTypeToCassType(pkObj));
    }

    return keyConcatStrategy.concat(segmentList);
  }

  private byte[] generateColumnFamilyKeyFromPojo(Object obj, CFMappingDef<?> cfMapDef) {
    List<byte[]> segmentList = new ArrayList<byte[]>(cfMapDef.getKeyDef().getIdPropertyMap().size());

    for (PropertyMappingDefinition md : cfMapDef.getKeyDef().getIdPropertyMap().values()) {
      Method meth = md.getPropDesc().getReadMethod();
      segmentList.add(callMethodAndConvertToCassandraType(obj, meth, md.getConverter()));
    }

    return keyConcatStrategy.concat(segmentList);
  }

  @SuppressWarnings("unchecked")
  private byte[] callMethodAndConvertToCassandraType(Object obj, Method meth,
      @SuppressWarnings("rawtypes") Converter converter) {
    try {
      Object retVal = meth.invoke(obj, (Object[]) null);
      return converter.convertObjTypeToCassType(retVal);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
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
  <T> T createObject(CFMappingDef<T> cfMapDef, Object pkObj, ColumnSlice<String, byte[]> slice) {
    if (slice.getColumns().isEmpty()) {
      return null;
    }

    CFMappingDef<? extends T> cfMapDefInstance = determineClassType(cfMapDef, slice);

    T obj;
    try {
      try {
        obj = cfMapDefInstance.getEffectiveClass().newInstance();
      } catch (InstantiationException e) {
        throw new HectorObjectMapperException(cfMapDefInstance.getEffectiveClass().getName()
            + ", must have default constructor", e);
      }

      setIdIfCan(cfMapDef, obj, pkObj);

      for (HColumn<String, byte[]> col : slice.getColumns()) {
        String colName = col.getName();
        PropertyMappingDefinition md = cfMapDefInstance.getPropMapByColumnName(colName);
        if (null != md && null != md.getPropDesc()) {
          if (!md.isCollectionType()) {
            setPropertyUsingColumn(obj, col, md);
          } else {
            collMapperHelper.instantiateCollection(obj, col, md);
          }
        } else if (collMapperHelper.addColumnToCollection(cfMapDefInstance, obj, colName,
            col.getValue())) {
          continue;
        }
        // if this is a derived class then don't need to save discriminator
        // column value
        else if (null != cfMapDef.getDiscColumn() && colName.equals(cfMapDef.getDiscColumn())) {
          continue;
        } else {
          addToExtraIfCan(obj, cfMapDef, col);
        }
      }

      return obj;
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    } catch (SecurityException e) {
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
  <T> Map<String, HColumn<String, byte[]>> createColumnMap(T obj) {
    if (null == obj) {
      throw new IllegalArgumentException("Class type cannot be null");
    }

    @SuppressWarnings("unchecked")
    CFMappingDef<T> cfMapDef = (CFMappingDef<T>) cacheMgr.getCfMapDef((Class<T>) obj.getClass(),
        true);
    try {
      Map<String, HColumn<String, byte[]>> colSet = new HashMap<String, HColumn<String, byte[]>>();
      Collection<PropertyMappingDefinition> coll = cfMapDef.getAllProperties();
      for (PropertyMappingDefinition md : coll) {
        Collection<HColumn<String, byte[]>> colColl = createColumnsFromProperty(obj, md);
        if (null != colColl) {
          for (HColumn<String, byte[]> col : colColl) {
            colSet.put(col.getName(), col);
          }
        }
      }

      if (null != cfMapDef.getCfBaseMapDef()) {
        CFMappingDef<?> cfSuperMapDef = cfMapDef.getCfBaseMapDef();
        String discColName = cfSuperMapDef.getDiscColumn();
        DiscriminatorType discType = cfSuperMapDef.getDiscType();

        colSet.put(
            discColName,
            createHColumn(discColName, convertDiscTypeToColValue(discType, cfMapDef.getDiscValue())));
      }

      addAnonymousProperties(obj, cfMapDef, colSet);
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

  @SuppressWarnings("unchecked")
  private <T> void addAnonymousProperties(T obj, CFMappingDef<T> cfMapDef,
      Map<String, HColumn<String, byte[]>> colSet) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException {
    Method meth = cfMapDef.getAnonymousPropertyGetHandler();
    if (null == meth) {
      return;
    }

    Collection<Entry<String, Object>> propColl = (Collection<Entry<String, Object>>) meth.invoke(
        obj, (Object[]) null);
    if (null == propColl || propColl.isEmpty()) {
      return;
    }

    for (Entry<String, Object> entry : propColl) {
      if (!(entry.getKey() instanceof String)
          || !entry.getValue().getClass().equals(cfMapDef.getAnonymousValueType())) {
        throw new HectorObjectMapperException("Class, " + cfMapDef.getRealClass()
            + ",  anonymous properties must have entry.key of type, " + String.class.getName()
            + ", and entry.value of type, " + cfMapDef.getAnonymousValueType().getName()
            + ", to properly map to Cassandra column name/value");
      }
    }

    for (Entry<String, Object> entry : propColl) {
      colSet.put(
          entry.getKey(),
          HFactory.createColumn(entry.getKey(),
              cfMapDef.getAnonymousValueSerializer().toBytes(entry.getValue()),
              StringSerializer.get(), BytesArraySerializer.get()));
    }
  }

  @SuppressWarnings({ "unchecked" })
  private <T> Collection<HColumn<String, byte[]>> createColumnsFromProperty(T obj,
      PropertyMappingDefinition md) throws SecurityException, NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    if (!md.isCollectionType()) {
      byte[] colValue = createBytesFromPropertyValue(obj, md);
      if (null == colValue) {
        return null;
      }
      return Arrays.asList(createHColumn(md.getColName(), colValue));
    } else {
      return createColumnsFromCollectionProperty(obj, md);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <T> Collection<HColumn<String, byte[]>> createColumnsFromCollectionProperty(T obj,
      PropertyMappingDefinition md) throws SecurityException, NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    // get collection
    Object tmpColl = reflectionHelper.invokeGetter(obj, md);
    if (!(tmpColl instanceof Collection)) {
      throw new HectorObjectMapperException("property, " + md.getColName()
          + ", is marked as a collection type, but not actually a Collection.  Property is type, "
          + md.getPropDesc().getPropertyType());
    }

    LinkedList<HColumn<String, byte[]>> colList = new LinkedList<HColumn<String, byte[]>>();

    // save collection info
    Collection coll = (Collection) tmpColl;
    colList.add(createHColumn(md.getColName(), collMapperHelper.createCollectionInfoColValue(coll)));

    // iterate over collection applying converter to its elements
    int count = 0;
    for (Object elem : coll) {
      byte[] bytes = collMapperHelper.serializeCollectionValue(elem);
      if (null == bytes) {
        return null;
      }
      colList.add(createHColumn(
          collMapperHelper.createCollectionItemColName(md.getColName(), count), bytes));
      count++;
    }
    return colList;
  }

  private byte[] createBytesFromPropertyValue(Object obj, PropertyMappingDefinition md)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    Object retVal = reflectionHelper.invokeGetter(obj, md);

    // if no value, then signal with null bytes
    if (null == retVal) {
      return null;
    }

    @SuppressWarnings("unchecked")
    byte[] bytes = md.getConverter().convertObjTypeToCassType(retVal);
    return bytes;
  }

  private HColumn<String, byte[]> createHColumn(String name, byte[] value) {
    return HFactory.createColumn(name, value, StringSerializer.get(), BytesArraySerializer.get());
  }

  private <T> CFMappingDef<? extends T> determineClassType(CFMappingDef<T> cfMapDef,
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
    Map<Object, CFMappingDef<? extends T>> derivedClasses = cfMapDef.getDerivedClassMap();

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
    CFMappingDef<? extends T> derivedCfMapDef = derivedClasses.get(discValue);
    if (null == derivedCfMapDef) {
      throw new RuntimeException("Cannot find derived class of "
          + cfMapDef.getEffectiveClass().getName() + " with discriminator value of " + discValue);
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

  private <T> void setIdIfCan(CFMappingDef<T> cfMapDef, T obj, Object pkObj)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    if (cfMapDef.getKeyDef().isComplexKey()) {
      setComplexId(cfMapDef, obj, pkObj);
    } else {
      setSimpleId(cfMapDef, obj, pkObj);
    }
  }

  private <T> void setSimpleId(CFMappingDef<T> cfMapDef, T obj, Object pkObj)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    PropertyMappingDefinition md = cfMapDef.getKeyDef().getIdPropertyMap().values().iterator()
                                           .next();
    if (null == md) {
      throw new HectorObjectMapperException(
          "Trying to build new object but haven't annotated a field with @"
              + Id.class.getSimpleName());
    }

    Method meth = md.getPropDesc().getWriteMethod();
    if (null == meth) {
      logger.debug("@Id annotation found - but can't find setter for property, "
          + md.getPropDesc().getName());
      throw new HectorObjectMapperException(
          "Trying to build new object but can't find setter for property, "
              + md.getPropDesc().getName());
    }

    meth.invoke(obj, pkObj);
  }

  private <T> void setComplexId(CFMappingDef<T> cfMapDef, T obj, Object pkObj)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

    KeyDefinition keyDef = cfMapDef.getKeyDef();
    if (!pkObj.getClass().equals(keyDef.getPkClazz())) {
      throw new HectorObjectMapperException("primary key object, " + pkObj.getClass().getName()
          + ", must be of type " + keyDef.getPkClazz().getName());
    }

    for (PropertyDescriptor pd : keyDef.getPropertyDescriptorMap().values()) {
      PropertyMappingDefinition md = keyDef.getIdPropertyMap().get(pd.getName());
      if (null == md) {
        throw new HectorObjectMapperException("Trying to set complex key type, but field, "
            + pd.getName() + ", is not annotated with @" + Id.class.getSimpleName() + " in POJO, "
            + cfMapDef.getRealClass().getName());
      }

      Method meth = md.getPropDesc().getWriteMethod();
      if (null == meth) {
        logger.debug("@Id annotation found - but can't find setter for property, "
            + md.getPropDesc().getName());
      }

      meth.invoke(obj, pd.getReadMethod().invoke(pkObj, (Object[]) null));
    }
  }

  private <T> void setPropertyUsingColumn(T obj, HColumn<String, byte[]> col,
      PropertyMappingDefinition md) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    PropertyDescriptor pd = md.getPropDesc();
    if (null == pd.getWriteMethod()) {
      throw new RuntimeException("property, " + pd.getName() + ", on class, "
          + obj.getClass().getName() + ", does not have a setter and therefore cannot be set");
    }

    Object value = md.getConverter().convertCassTypeToObjType(md, col.getValue());
    pd.getWriteMethod().invoke(obj, value);
  }

  public static Serializer<?> determineSerializer(Class<?> theType) {
    Serializer<?> s = SerializerTypeInferer.getSerializer(theType);
    if (null == s) {
      throw new RuntimeException(
          "unsupported property type, "
              + theType.getName()
              + ".  Create custom converter or petition Hector Core team to add another converter to SerializerTypeInferer");
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
      if (interfa.equals(target)) {
        return true;
      }
    }
    return false;
  }

  private <T> void addToExtraIfCan(Object obj, CFMappingDef<T> cfMapDef, HColumn<String, byte[]> col)
      throws SecurityException, IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    Method meth = cfMapDef.getAnonymousPropertyAddHandler();
    if (null == meth) {
      throw new IllegalArgumentException(
          "Object type, "
              + obj.getClass()
              + ", does not have a property named, "
              + col.getName()
              + ".  either add a setter for this property or use @AnonymousPropertyHandler to annotate a method for handling anonymous properties");
    }

    meth.invoke(obj, col.getName(), cfMapDef.getAnonymousValueSerializer()
                                            .fromBytes(col.getValue()));
  }

  public void setKeyConcatStrategy(KeyConcatenationStrategy keyConcatStrategy) {
    this.keyConcatStrategy = keyConcatStrategy;
  }

}
