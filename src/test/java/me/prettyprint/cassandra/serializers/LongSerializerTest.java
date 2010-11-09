package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import me.prettyprint.cassandra.serializers.LongSerializer;

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
  }
}
