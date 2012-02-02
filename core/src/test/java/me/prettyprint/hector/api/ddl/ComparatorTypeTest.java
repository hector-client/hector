package me.prettyprint.hector.api.ddl;

import static me.prettyprint.hector.api.ddl.ComparatorType.ASCIITYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.BYTESTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.INTEGERTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.LEXICALUUIDTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.LOCALBYPARTITIONERTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.LONGTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.TIMEUUIDTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.UTF8TYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.COMPOSITETYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.DYNAMICCOMPOSITETYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.UUIDTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.COUNTERTYPE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComparatorTypeTest {

  @Test
  public void getByClassNameShouldReturnNullWhenNullClassName() {
    assertEquals(null, ComparatorType.getByClassName(null));
  }

  @Test
  public void getByClassNameShouldReturnCorrectCustomComaparator() {
    ComparatorType comparator = ComparatorType.getByClassName("com.custom.Comparator");
    assertEquals("com.custom.Comparator", comparator.getClassName());
    assertEquals("com.custom.Comparator", comparator.getTypeName());
  }

  @Test
  public void getByClassNameShouldReturnCorrectUnknownComaparator() {
    ComparatorType comparator = ComparatorType.getByClassName(
        "org.apache.cassandra.db.marshal.SomeNewType");
    assertEquals("org.apache.cassandra.db.marshal.SomeNewType", comparator.getClassName());
    assertEquals("SomeNewType", comparator.getTypeName());
  }

  @Test
  public void getByClassNameShouldReturnCorrectKnownComaparators() {
    assertEquals(ASCIITYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.AsciiType"));
    assertEquals(ASCIITYPE,
        ComparatorType.getByClassName("AsciiType"));

    assertEquals(BYTESTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.BytesType"));
    assertEquals(BYTESTYPE,
        ComparatorType.getByClassName("BytesType"));

    assertEquals(INTEGERTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.IntegerType"));
    assertEquals(INTEGERTYPE,
        ComparatorType.getByClassName("IntegerType"));

    assertEquals(LEXICALUUIDTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.LexicalUUIDType"));
    assertEquals(LEXICALUUIDTYPE,
        ComparatorType.getByClassName("LexicalUUIDType"));

    assertEquals(LOCALBYPARTITIONERTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.LocalByPartionerType"));
    assertEquals(LOCALBYPARTITIONERTYPE,
        ComparatorType.getByClassName("LocalByPartionerType"));

    assertEquals(LONGTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.LongType"));
    assertEquals(LONGTYPE,
        ComparatorType.getByClassName("LongType"));

    assertEquals(TIMEUUIDTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.TimeUUIDType"));
    assertEquals(TIMEUUIDTYPE,
        ComparatorType.getByClassName("TimeUUIDType"));

    assertEquals(UTF8TYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.UTF8Type"));
    assertEquals(UTF8TYPE,
        ComparatorType.getByClassName("UTF8Type"));

    assertEquals(COMPOSITETYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.CompositeType"));
    assertEquals(COMPOSITETYPE,
        ComparatorType.getByClassName("CompositeType"));

    assertEquals(DYNAMICCOMPOSITETYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.DynamicCompositeType"));
    assertEquals(DYNAMICCOMPOSITETYPE,
        ComparatorType.getByClassName("DynamicCompositeType"));

    assertEquals(UUIDTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.UUIDType"));
    assertEquals(UUIDTYPE,
        ComparatorType.getByClassName("UUIDType"));

    assertEquals(COUNTERTYPE,
        ComparatorType.getByClassName("org.apache.cassandra.db.marshal.CounterColumnType"));
    assertEquals(COUNTERTYPE,
        ComparatorType.getByClassName("CounterColumnType"));
  }
}
