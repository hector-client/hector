package me.prettyprint.hom.converters;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyDescriptor;
import java.math.BigInteger;

import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.cache.HectorObjectMapperException;

import org.junit.Test;

public class VariableIntegerConverterTest {
  VariableIntegerConverter conv = new VariableIntegerConverter();

  @Test
  public void testByte() throws Exception {
    byte[] ba;

    byte b1 = 123;
    ba = conv.convertObjTypeToCassType(b1);
    assertEquals(b1, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("b1", TestClass.class), "foo", VariableIntegerConverter.class), ba));

    Byte b2 = 1;
    ba = conv.convertObjTypeToCassType(b2);
    assertEquals(b2, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("b2", TestClass.class), "foo", VariableIntegerConverter.class), ba));

  }

  @Test
  public void testInteger() throws Exception {
    byte[] ba;

    int i1 = 123;
    ba = conv.convertObjTypeToCassType(i1);
    assertEquals(i1, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("i1", TestClass.class), "foo", VariableIntegerConverter.class), ba));

    Integer i2 = 1;
    ba = conv.convertObjTypeToCassType(i2);
    assertEquals(i2, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("i2", TestClass.class), "foo", VariableIntegerConverter.class), ba));

  }

  @Test
  public void testShort() throws Exception {
    byte[] ba;

    short s1 = 123;
    ba = conv.convertObjTypeToCassType(s1);
    assertEquals(s1, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("s1", TestClass.class), "foo", VariableIntegerConverter.class), ba));

    Short s2 = 1;
    ba = conv.convertObjTypeToCassType(s2);
    assertEquals(s2, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("s2", TestClass.class), "foo", VariableIntegerConverter.class), ba));

  }

  @Test
  public void testLong() throws Exception {
    byte[] ba;

    long l1 = 123;
    ba = conv.convertObjTypeToCassType(l1);
    assertEquals(l1, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("l1", TestClass.class), "foo", VariableIntegerConverter.class), ba));

    Long l2 = 1L;
    ba = conv.convertObjTypeToCassType(l2);
    assertEquals(l2, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("l2", TestClass.class), "foo", VariableIntegerConverter.class), ba));

  }

  @Test
  public void testBigInteger() throws Exception {
    byte[] ba;

    BigInteger b1 = BigInteger.valueOf(123);
    ba = conv.convertObjTypeToCassType(b1);
    assertEquals(b1, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("bigInt", TestClass.class), "foo", VariableIntegerConverter.class), ba));
  }

  @Test(expected=HectorObjectMapperException.class)
  public void testStringToCassNotWork() throws Exception {
    byte[] ba;

    String s1 = new String("123");
    ba = conv.convertObjTypeToCassType(s1);
    assertEquals(s1, conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("str1", TestClass.class), "foo", VariableIntegerConverter.class), ba));
  }

  @Test(expected=HectorObjectMapperException.class)
  public void testStringFromCassNotWork() throws Exception {
    conv.convertCassTypeToObjType(new PropertyMappingDefinition(
        new PropertyDescriptor("str1", TestClass.class), "foo", VariableIntegerConverter.class), new String("123").getBytes());
  }

  class TestClass {
    byte b1;
    Byte b2;

    short s1;
    Short s2;

    int i1;
    Integer i2;

    long l1;
    Long l2;

    BigInteger bigInt;
    
    String str1; // should not work

    public String getStr1() {
      return str1;
    }

    public void setStr1(String str1) {
      this.str1 = str1;
    }

    public Byte getB2() {
      return b2;
    }

    public void setB2(Byte b2) {
      this.b2 = b2;
    }

    public short getS1() {
      return s1;
    }

    public void setS1(short s1) {
      this.s1 = s1;
    }

    public Short getS2() {
      return s2;
    }

    public void setS2(Short s2) {
      this.s2 = s2;
    }

    public int getI1() {
      return i1;
    }

    public void setI1(int i1) {
      this.i1 = i1;
    }

    public Integer getI2() {
      return i2;
    }

    public void setI2(Integer i2) {
      this.i2 = i2;
    }

    public long getL1() {
      return l1;
    }

    public void setL1(long l1) {
      this.l1 = l1;
    }

    public Long getL2() {
      return l2;
    }

    public void setL2(Long l2) {
      this.l2 = l2;
    }

    public BigInteger getBigInt() {
      return bigInt;
    }

    public void setBigInt(BigInteger bigInt) {
      this.bigInt = bigInt;
    }

    public byte getB1() {
      return b1;
    }

    public void setB1(byte b1) {
      this.b1 = b1;
    }
    
    
  }
}
