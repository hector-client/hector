package me.prettyprint.cassandra.serializers;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class StringSerializerTest {

  private final String str;
  private static final StringSerializer s = StringSerializer.get();

  public StringSerializerTest(String str) {
    this.str = str;
  }

  @Parameters
  public static Collection<Object[]> data() throws UnsupportedEncodingException {
    Object[][] data = new Object[][] { { "" }, { null }, { "123" }, { "QWER" },
        { "!@#$#$^%&^*fdghdfghdfgh%^&*" },
        { new String("\u05E9".getBytes(), "utf-8") },
        { RandomStringUtils.random(256 * 256) } };
    return Arrays.asList(data);
  }

  @Test
  public void test() throws Exception {
    Assert.assertEquals(str, s.fromByteBuffer(s.toByteBuffer(str)));
    if (str != null) {
      Assert
          .assertEquals(str, ByteBufferUtil.string(ByteBufferUtil.bytes(str)));
    }
    if (str != null) {
      UTF8Type.instance.validate(s.toByteBuffer(str));
    }
  }
}
