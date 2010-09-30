package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import me.prettyprint.cassandra.serializers.LongSerializer;

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
    assertEquals(number, ext.fromBytes(ext.toBytes(number)));
  }
}
