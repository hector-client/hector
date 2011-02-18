package me.prettyprint.cassandra.serializers;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class StringSerializerTest {

  private final String str;

  public StringSerializerTest(String str) {
    this.str = str;
  }

  @Parameters
  public static Collection<Object[]> data() throws UnsupportedEncodingException {
    Object[][] data = new Object[][] {
                                      {"" },
                                      {null},
                                      {"123"},
                                      {"QWER"},
                                      {"!@#$#$^%&^*fdghdfghdfgh%^&*"},
                                      {new String("\u05E9".getBytes(), "utf-8")},
                                      {RandomStringUtils.random(256*256)}
                                      };
    return Arrays.asList(data);
  }

  @Test
  public void test() {
    StringSerializer e = new StringSerializer();
    Assert.assertEquals(str, e.fromByteBuffer(e.toByteBuffer(str))) ;
  }
}
