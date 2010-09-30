package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Bozhidar Bozhanov
 *
 */
public class BooleanSerializerTest {

  @Test
  public void testConversions() {
    test(true);
    test(false);
    test(null);
  }

  private void test(Boolean bool) {
    BooleanSerializer ext = BooleanSerializer.get();
    assertEquals(bool, ext.fromBytes(ext.toBytes(bool)));
  }
}
