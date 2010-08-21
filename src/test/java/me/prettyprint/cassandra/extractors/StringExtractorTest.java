package me.prettyprint.cassandra.extractors;


import static org.junit.Assert.assertEquals;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.junit.Test;

public class StringExtractorTest {

  @Test
  public void testConversions() {
    test("");
    test(null);
    test("123");
    test("QWER");
    test("!@#$#$^%&^*fdghdfghdfgh%^&*");
    // unicode
    test("\u1234");
  }

  private void test(String str) {
    StringSerializer e = new StringSerializer();
    assertEquals(str, e.fromBytes(e.toBytes(str))) ;
  }
}
