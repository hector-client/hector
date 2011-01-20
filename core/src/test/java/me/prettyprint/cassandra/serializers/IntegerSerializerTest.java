package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 
 * @author Bozhidar Bozhanov
 * 
 */
public class IntegerSerializerTest {

  @Test
  public void testConversions() {
    test(0);
    test(1);
    test(-1);
    test(Integer.MAX_VALUE);
    test(Integer.MIN_VALUE);
    test(null);
  }

  private void test(Integer number) {
    IntegerSerializer ext = IntegerSerializer.get();
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
