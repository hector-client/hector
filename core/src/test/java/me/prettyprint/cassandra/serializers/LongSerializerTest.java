package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class LongSerializerTest {

  @Test
  public void testConversions() {
    test(0l);
    test(1l);
    test(-1l);
    test(Long.MAX_VALUE);
    test(Long.MIN_VALUE);
    test(null);
  }

  private void test(Long number) {
    LongSerializer ext = new LongSerializer();
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
