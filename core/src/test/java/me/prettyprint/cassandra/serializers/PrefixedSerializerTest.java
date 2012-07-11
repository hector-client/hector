package me.prettyprint.cassandra.serializers;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class PrefixedSerializerTest {

  private final String str;

  public PrefixedSerializerTest(String str) {
    this.str = str;
  }

  public static UUID prefixUUID = UUID.randomUUID();

  @Parameters
  public static Collection<Object[]> data() throws UnsupportedEncodingException {
    Object[][] data = new Object[][] { { "" }, { null }, { "123" }, { "QWER" },
        { "!@#$#$^%&^*fdghdfghdfgh%^&*" },
        { new String("\u05E9".getBytes(), "utf-8") } };
    return Arrays.asList(data);
  }

  @Test
  public void test() {
    UUIDSerializer ue = new UUIDSerializer();
    StringSerializer se = new StringSerializer();
    PrefixedSerializer<UUID, String> pe = new PrefixedSerializer<UUID, String>(
        prefixUUID, ue, se);
    Assert.assertEquals(str, pe.fromByteBuffer(pe.toByteBuffer(str)));
  }

  @Test
  public void testBadPrefix() {
    if (str == null) {
      // null serialization is always null,
      // so no prefix comparison takes place to test
      return;
    }
    UUIDSerializer ue = new UUIDSerializer();
    StringSerializer se = new StringSerializer();
    PrefixedSerializer<UUID, String> pe1 = new PrefixedSerializer<UUID, String>(
        prefixUUID, ue, se);
    UUID testUUID = UUID.randomUUID();
    Assert.assertNotSame(prefixUUID, testUUID);
    PrefixedSerializer<UUID, String> pe2 = new PrefixedSerializer<UUID, String>(
        testUUID, ue, se);
    try {
      pe2.fromByteBuffer(pe1.toByteBuffer(str));
      Assert.fail("Different prefixes should fail comparison");
    } catch (Exception e) {
    }
  }
}
