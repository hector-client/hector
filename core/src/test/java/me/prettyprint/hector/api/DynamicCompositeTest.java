package me.prettyprint.hector.api;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.beans.DynamicComposite;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicCompositeTest {

  private static final Logger log = LoggerFactory
      .getLogger(ClockResolutionTest.class);

  @Test
  public void testSerialization() throws Exception {

    DynamicComposite c = new DynamicComposite();
    c.add("String1");
    ByteBuffer b = c.serialize();
    assertEquals(b.remaining(), 12);

    c.add("String2");
    b = c.serialize();
    assertEquals(b.remaining(), 24);

    c = new DynamicComposite();
    c.deserialize(b);
    Object o = c.get(0);
    assertEquals("String1", o);
  }
}
