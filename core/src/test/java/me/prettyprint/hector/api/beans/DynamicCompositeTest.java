package me.prettyprint.hector.api.beans;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.Date;

import me.prettyprint.cassandra.serializers.AsciiSerializer;
import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.DoubleSerializer;
import me.prettyprint.cassandra.serializers.DynamicCompositeSerializer;
import me.prettyprint.cassandra.serializers.FloatSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;
import org.junit.Test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DynamicCompositeTest {

  @Test
  public void allTypesSerialize() {
    DynamicComposite composite = new DynamicComposite();

    UUID lexUUID = UUID.randomUUID();
    com.eaio.uuid.UUID timeUUID = new com.eaio.uuid.UUID();

    Date date = new Date();

    //add all forward comparators
    composite.addComponent(0, "AsciiText", AsciiSerializer.get(), "AsciiType", ComponentEquality.EQUAL);
    composite.addComponent(1, new byte[]{0, 1, 2, 3}, BytesArraySerializer.get(), "BytesType", ComponentEquality.EQUAL);
    composite.addComponent(2, -1, IntegerSerializer.get(), "IntegerType", ComponentEquality.EQUAL);
    composite.addComponent(3, lexUUID, UUIDSerializer.get(), "LexicalUUIDType", ComponentEquality.EQUAL);
    composite.addComponent(4, -1l, LongSerializer.get(), "LongType", ComponentEquality.EQUAL);
    composite.addComponent(5, timeUUID, TimeUUIDSerializer.get(), "TimeUUIDType", ComponentEquality.EQUAL);
    composite.addComponent(6, "UTF8Text", StringSerializer.get(), "UTF8Type", ComponentEquality.EQUAL);
    composite.addComponent(7, lexUUID, UUIDSerializer.get(), "UUIDType", ComponentEquality.EQUAL);

    //  new types:    DateType, BooleanType, FloatType, DoubleType
    composite.addComponent(8, date, DateSerializer.get(), "DateType", ComponentEquality.EQUAL);
    composite.addComponent(9, true, BooleanSerializer.get(), "BooleanType", ComponentEquality.EQUAL);
    composite.addComponent(10, 1.1f, FloatSerializer.get(), "FloatType", ComponentEquality.EQUAL);
    composite.addComponent(11, 1.2d, DoubleSerializer.get(), "DoubleType", ComponentEquality.EQUAL);


    //add all reverse comparators
    composite.addComponent(12, "AsciiText", AsciiSerializer.get(), "AsciiType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(13, new byte[]{0, 1, 2, 3}, BytesArraySerializer.get(), "BytesType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(14, -1, IntegerSerializer.get(), "IntegerType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(15, lexUUID, UUIDSerializer.get(), "LexicalUUIDType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(16, -1l, LongSerializer.get(), "LongType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(17, timeUUID, TimeUUIDSerializer.get(), "TimeUUIDType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(18, "UTF8Text", StringSerializer.get(), "UTF8Type(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(19, lexUUID, UUIDSerializer.get(), "UUIDType(reversed=true)", ComponentEquality.EQUAL);

    composite.addComponent(20, date, DateSerializer.get(), "DateType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(21, true, BooleanSerializer.get(), "BooleanType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(22, 1.1f, FloatSerializer.get(), "FloatType(reversed=true)", ComponentEquality.EQUAL);
    composite.addComponent(23, 1.2d, DoubleSerializer.get(), "DoubleType(reversed=true)", ComponentEquality.EQUAL);


    //serialize to the native bytes value

    ByteBuffer buffer = DynamicCompositeSerializer.get().toByteBuffer(composite);


    //now deserialize and ensure the values are the same
    DynamicComposite parsed = DynamicCompositeSerializer.get().fromByteBuffer(buffer);

    assertEquals("AsciiText", parsed.get(0, AsciiSerializer.get()));
    assertArrayEquals(new byte[]{0, 1, 2, 3}, parsed.get(1, BytesArraySerializer.get()));
    assertEquals(Integer.valueOf(-1), parsed.get(2, IntegerSerializer.get()));
    assertEquals(lexUUID, parsed.get(3, UUIDSerializer.get()));
    assertEquals(Long.valueOf(-1l), parsed.get(4, LongSerializer.get()));
    assertEquals(timeUUID, parsed.get(5, TimeUUIDSerializer.get()));
    assertEquals("UTF8Text", parsed.get(6, StringSerializer.get()));
    assertEquals(lexUUID, parsed.get(7, UUIDSerializer.get()));
    assertEquals(date, parsed.get(8, DateSerializer.get()));
    assertEquals(Boolean.TRUE, parsed.get(9, BooleanSerializer.get()));
    assertEquals(new Float(1.1f), parsed.get(10, FloatSerializer.get()));
    assertEquals(new Double(1.2d), parsed.get(11, DoubleSerializer.get()));


    //now test all the reversed values
    assertEquals("AsciiText", parsed.get(12, AsciiSerializer.get()));
    assertArrayEquals(new byte[]{0, 1, 2, 3}, parsed.get(13, BytesArraySerializer.get()));
    assertEquals(Integer.valueOf(-1), parsed.get(14, IntegerSerializer.get()));
    assertEquals(lexUUID, parsed.get(15, UUIDSerializer.get()));
    assertEquals(Long.valueOf(-1l), parsed.get(16, LongSerializer.get()));
    assertEquals(timeUUID, parsed.get(17, TimeUUIDSerializer.get()));
    assertEquals("UTF8Text", parsed.get(18, StringSerializer.get()));
    assertEquals(lexUUID, parsed.get(19, UUIDSerializer.get()));
    assertEquals(date, parsed.get(20, DateSerializer.get()));
    assertEquals(Boolean.TRUE, parsed.get(21, BooleanSerializer.get()));
    assertEquals(new Float(1.1f), parsed.get(22, FloatSerializer.get()));
    assertEquals(new Double(1.2d), parsed.get(23, DoubleSerializer.get()));


  }

}
