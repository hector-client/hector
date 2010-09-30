package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

/**
 * @author  Jim Ancona
 */
public class DateSerializerTest {

  @Test
  public void testConversions() {
    test(new Date());
    test(new Date(0l));
    test(new Date(1l));
    test(new Date(-1l));
    test(new Date(Long.MAX_VALUE));
    test(new Date(Long.MIN_VALUE));
    test(null);
  }

  private void test(Date date) {
    DateSerializer ext = DateSerializer.get();
    assertEquals(date, ext.fromBytes(ext.toBytes(date)));
  }
}
