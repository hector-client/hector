package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;
import org.junit.Test;

public class LongSerializerTest {

  static LongSerializer ext = LongSerializer.get();
  
  @Test
  public void testConversions() {
    test(0l);
    test(1l);
    test(-1l);
    test(Long.MAX_VALUE);
    test(Long.MIN_VALUE);
    test(null);
  }

  @Test
  public void testFromCassandra() {
    assertEquals(new Long(1), ext.fromByteBuffer(ByteBufferUtil.bytes(1L)));
    assertEquals(new Long(0), ext.fromByteBuffer(ByteBufferUtil.bytes(0L)));
    assertEquals(new Long(-1), ext.fromByteBuffer(ByteBufferUtil.bytes(-1L)));
    assertEquals(new Long(Long.MIN_VALUE), ext.fromByteBuffer(ByteBufferUtil.bytes(Long.MIN_VALUE)));
    assertEquals(new Long(Long.MAX_VALUE), ext.fromByteBuffer(ByteBufferUtil.bytes(Long.MAX_VALUE)));
  }
  
  private void test(Long number) {
    
    assertEquals(number, ext.fromByteBuffer(ext.toByteBuffer(number)));

    // test compatibility with ByteBuffer default byte order
    if (number != null) {
      ByteBuffer b = ByteBuffer.allocate(8);
      b.putLong(number);
      b.rewind();
      assertEquals(number, ext.fromByteBuffer(b));
    }
  }
}
