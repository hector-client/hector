package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;
import org.junit.Test;

/**
 * 
 * @author Bozhidar Bozhanov
 * 
 */
public class IntegerSerializerTest {

  static IntegerSerializer ext = IntegerSerializer.get();
  
  @Test
  public void testConversions() {
    test(0);
    test(1);
    test(-1);
    test(Integer.MAX_VALUE);
    test(Integer.MIN_VALUE);
    test(null);
  }

  @Test
  public void testFromCassandra() {
    assertEquals(new Integer(1), ext.fromByteBuffer(ByteBufferUtil.bytes(1)));
    assertEquals(new Integer(-1), ext.fromByteBuffer(ByteBufferUtil.bytes(-1)));
    assertEquals(new Integer(0), ext.fromByteBuffer(ByteBufferUtil.bytes(0)));
    assertEquals(new Integer(Integer.MAX_VALUE), ext.fromByteBuffer(ByteBufferUtil.bytes(Integer.MAX_VALUE)));
    assertEquals(new Integer(Integer.MIN_VALUE), ext.fromByteBuffer(ByteBufferUtil.bytes(Integer.MIN_VALUE)));
  }
  
  @Test
  public void testFromCassandraAsBytes() {
    assertEquals(new Integer(1), ext.fromBytes(ByteBufferUtil.bytes(1).array()));
    assertEquals(new Integer(-1), ext.fromBytes(ByteBufferUtil.bytes(-1).array()));
    assertEquals(new Integer(0), ext.fromBytes(ByteBufferUtil.bytes(0).array()));
    assertEquals(new Integer(Integer.MAX_VALUE), ext.fromBytes(ByteBufferUtil.bytes(Integer.MAX_VALUE).array()));
    assertEquals(new Integer(Integer.MIN_VALUE), ext.fromBytes(ByteBufferUtil.bytes(Integer.MIN_VALUE).array()));
  }
  
  
  private void test(Integer number) {
    
    assertEquals(number, ext.fromByteBuffer(ext.toByteBuffer(number)));

    // test compatibility with ByteBuffer default byte order
    if (number != null) {
      ByteBuffer b = ByteBuffer.allocate(4);
      b.putInt(number);
      b.rewind();
      assertEquals(number, ext.fromByteBuffer(b));
    }
  }
}
