package me.prettyprint.hector.api.ddl;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * @author peter
 * @author Mikhail Mazursky
 */
public final class ComparatorType {

  public static final ComparatorType ASCIITYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.AsciiType");
  public static final ComparatorType BOOLEANTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.BooleanType");
  public static final ComparatorType BYTESTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.BytesType");
  public static final ComparatorType COLLECTIONTYPE = new ComparatorType(
	      "org.apache.cassandra.db.marshal.CollectionType");
  public static final ComparatorType DATETYPE = new ComparatorType(
	  "org.apache.cassandra.db.marshal.DateType");
  public static final ComparatorType DECIMALTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.DecimalType");
  public static final ComparatorType DOUBLETYPE = new ComparatorType(
	      "org.apache.cassandra.db.marshal.DoubleType");
  public static final ComparatorType FLOATTYPE = new ComparatorType(
	      "org.apache.cassandra.db.marshal.FloatType");
  public static final ComparatorType INTEGERTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.IntegerType");
  public static final ComparatorType INT32TYPE = new ComparatorType(
	  "org.apache.cassandra.db.marshal.Int32Type");
  public static final ComparatorType LEXICALUUIDTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.LexicalUUIDType");
  public static final ComparatorType LISTTYPE = new ComparatorType(
	      "org.apache.cassandra.db.marshal.ListType");
  public static final ComparatorType LOCALBYPARTITIONERTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.LocalByPartionerType");
  public static final ComparatorType LONGTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.LongType");
  public static final ComparatorType MAPTYPE = new ComparatorType(
	      "org.apache.cassandra.db.marshal.MapType");
  public static final ComparatorType SETTYPE = new ComparatorType(
	      "org.apache.cassandra.db.marshal.SetType");
  public static final ComparatorType TIMEUUIDTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.TimeUUIDType");
  public static final ComparatorType UTF8TYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.UTF8Type");
  public static final ComparatorType COMPOSITETYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.CompositeType");
  public static final ComparatorType DYNAMICCOMPOSITETYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.DynamicCompositeType");
  public static final ComparatorType UUIDTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.UUIDType");
  public static final ComparatorType COUNTERTYPE = new ComparatorType(
      "org.apache.cassandra.db.marshal.CounterColumnType");
  public static final ComparatorType REVERSEDTYPE = new ComparatorType(
	  "org.apache.cassandra.db.marshal.ReversedType");

  private static final Map<String, ComparatorType> valuesMap;

  static {
    ComparatorType[] types = { ASCIITYPE, BOOLEANTYPE, BYTESTYPE, COLLECTIONTYPE,
    	DATETYPE, DECIMALTYPE, DOUBLETYPE, FLOATTYPE, INTEGERTYPE, INT32TYPE,
        LEXICALUUIDTYPE, LISTTYPE, LOCALBYPARTITIONERTYPE, LONGTYPE,
        MAPTYPE, SETTYPE, TIMEUUIDTYPE, UTF8TYPE, COMPOSITETYPE,
        DYNAMICCOMPOSITETYPE, UUIDTYPE, COUNTERTYPE, REVERSEDTYPE};

    ImmutableMap.Builder<String, ComparatorType> builder =
        new ImmutableMap.Builder<String, ComparatorType>();

    for (ComparatorType type : types){
      builder.put(type.getClassName(), type).put(type.getTypeName(), type);
    }

    valuesMap = builder.build();
  }

  private final String className;
  private final String typeName;

  private ComparatorType(String className) {
    this.className = className;
    if (className.startsWith("org.apache.cassandra.db.marshal.")) {
      typeName = className.substring("org.apache.cassandra.db.marshal."
          .length());
    } else {
      typeName = className;
    }
  }

  public String getClassName() {
    return className;
  }

  public String getTypeName() {
    return typeName;
  }

  public static ComparatorType getByClassName(String className) {
    if (className == null) {
      return null;
    }
    ComparatorType type = valuesMap.get(className);
    if (type == null) {
      return new ComparatorType(className);
    }
    return type;
  }

  @Override
  public int hashCode() {
    return className.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ComparatorType other = (ComparatorType) obj;
    if (!className.equals(other.className)) {
      return false;
    }
    return true;
  }
}
