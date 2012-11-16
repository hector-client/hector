package me.prettyprint.cassandra.serializers;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import me.prettyprint.hector.api.exceptions.HectorSerializationException;
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
    String result = pe2.fromByteBuffer(pe1.toByteBuffer(str));
    Assert.assertNull("Different prefixes should fail comparison", result);
  }

  @Test
  public void testNoPrefix() {
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
      ByteBuffer bb = pe1.toByteBuffer(str);
      bb.limit(5);
//      bb.get(new byte[bb.limit() - 5]);
      pe2.fromByteBuffer(bb);
      Assert.fail("Lack of prefix should raise exception");
    } catch (HectorSerializationException e) {
      // yea
    }
  }
}
