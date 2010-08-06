package me.prettyprint.cassandra.extractors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongExtractorTest {

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
    LongExtractor ext = new LongExtractor();
    assertEquals(number, ext.fromBytes(ext.toBytes(number)));
  }
}
