package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;

public class BigIntegerSerializerTest {
  
  @Test
  public void testToAndFrom() {
    BigInteger bi = new BigInteger(new byte[]{0,0,0,1});
    ByteBuffer bb = BigIntegerSerializer.get().toByteBuffer(bi);
    assertTrue(bb != null);
    assertEquals(1,bb.array().length);
    assertEquals(bi, BigIntegerSerializer.get().fromByteBuffer(bb));    
  }

}
