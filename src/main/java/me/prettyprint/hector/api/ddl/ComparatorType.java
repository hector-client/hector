package me.prettyprint.hector.api.ddl;

/**
 * @author: peter
 */
public class ComparatorType {

  public static ComparatorType BYTESTYPE = new ComparatorType("org.apache.cassandra.db.marshal.BytesType");
  public static ComparatorType ASCIITYPE = new ComparatorType("org.apache.cassandra.db.marshal.AsciiType");
  public static ComparatorType UTF8TYPE = new ComparatorType("org.apache.cassandra.db.marshal.UTF8Type");
  public static ComparatorType LEXICALUUIDTYPE = new ComparatorType("org.apache.cassandra.db.marshal.LexicalUUIDType");
  public static ComparatorType TIMEUUIDTYPE = new ComparatorType("org.apache.cassandra.db.marshal.TimeUUIDType");
  public static ComparatorType LONGTYPE = new ComparatorType("org.apache.cassandra.db.marshal.LongType");
  public static ComparatorType INTEGERTYPE = new ComparatorType("org.apache.cassandra.db.marshal.IntegerType");

  private static ComparatorType[] values = {BYTESTYPE, ASCIITYPE, UTF8TYPE, LEXICALUUIDTYPE, TIMEUUIDTYPE, LONGTYPE,INTEGERTYPE};

  private final String className;

  private ComparatorType(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  public static ComparatorType getByClassName(String className) {

    for (int a = 0; a < values.length; a++) {
      ComparatorType type = values[a];
      if (type.getClassName().equals(className)) {
        return type;
      }
      if (type.getClassName().equals("org.apache.cassandra.db.marshal." + className)) {
        return type;
      }
    }
    return new ComparatorType(className);
  }
}
