package me.prettyprint.hector.api.ddl;

/**
 * @author: peter
 */
public enum ComparatorType {

  BYTESTYPE("org.apache.cassandra.db.marshal.BytesType"),
  ASCIITYPE("org.apache.cassandra.db.marshal.AsciiType"),
  UTF8TYPE("org.apache.cassandra.db.marshal.UTF8Type"),
  LEXICALUUIDTYPE("org.apache.cassandra.db.marshal.LexicalUUIDType"),
  TIMEUUIDTYPE("org.apache.cassandra.db.marshal.TimeUUIDType"),
  LONGTYPE("org.apache.cassandra.db.marshal.LongType"),
  INTEGERTYPE("org.apache.cassandra.db.marshal.IntegerType");

  private String className;

  private ComparatorType(String className) {
    this.className = className;
  }

  public String getClassName() {
    return this.className;
  }

  public static ComparatorType getByClassName(String className) {

    for (int a = 0; a < values().length; a++) {
      ComparatorType type = values()[a];
      if (type.getClassName().equals(className)) {
        return type;
      }
      if (type.getClassName().equals("org.apache.cassandra.db.marshal." + className)) {
        return type;
      }
    }
    return null;
  }
}
