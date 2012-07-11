package me.prettyprint.cassandra.serializers;


import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * @author Patricio Echague
 * @author Ran Tavory
 *
 */
public class BytesArraySerializerTest {

  @Test
  public void testConversions() {
    test(null);
    test(new byte[]{});
    test(new byte[]{1});
    test(new byte[]{1,2,3,4,5});
    // and also some string oriented tests:
    test(bytes(""));
    test(bytes("123"));
  }

  private void test(byte[] bytes) {
    BytesArraySerializer e = new BytesArraySerializer();
    assertArrayEquals(bytes, e.fromByteBuffer(e.toByteBuffer(bytes))) ;
  }
}
