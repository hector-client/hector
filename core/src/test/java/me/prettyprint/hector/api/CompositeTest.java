package me.prettyprint.hector.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.BigIntegerSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.beans.Composite;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.UUIDGen;
import org.junit.Test;

public class CompositeTest {

  @Test
  public void testSerialization() throws Exception {

    Composite c = new Composite();
    c.add("String1");
    ByteBuffer b = c.serialize();
    assertEquals(b.remaining(), 12);

    c.add("String2");
    b = c.serialize();
    assertEquals(b.remaining(), 24);

    c = new Composite();
    c.deserialize(b);
    assertEquals(2, c.size());
    Object o = c.get(0);
    assertEquals("String1", o);
    o = c.get(1);
    assertEquals("String2", o);

    c = new Composite();
    c.add(new Long(10));
    b = c.serialize();
    c = new Composite();
    c.deserialize(b);
    o = c.get(0);
    assertTrue(o instanceof Long);

    b = createDynamicCompositeKey("Hello",
        TimeUUIDUtils.getUniqueTimeUUIDinMillis(), 10, false);
    c = new Composite();
    c.deserialize(b.slice());
    o = c.get(0);
    assertTrue(o instanceof ByteBuffer);
    assertEquals("Hello", c.get(0, StringSerializer.get()));

    o = c.get(1);
    assertEquals(UUID.class, o.getClass());

    o = c.get(2);
    assertEquals(BigInteger.class, o.getClass());
    assertEquals(BigInteger.valueOf(10), o);

    c = new Composite();
    c.setAutoDeserialize(false);
    c.deserialize(b.slice());
    assertTrue(c.get(0) instanceof ByteBuffer);
    assertTrue(c.get(1) instanceof ByteBuffer);
    assertTrue(c.get(2) instanceof ByteBuffer);

    c = new Composite();
    c.setSerializersByPosition(StringSerializer.get(), null,
        ByteBufferSerializer.get());
    c.deserialize(b.slice());
    assertTrue(c.get(0) instanceof String);
    assertTrue(c.get(1) instanceof UUID);
    assertTrue(c.get(2) instanceof ByteBuffer);

    b = createCompositeKey("Hello", TimeUUIDUtils.getUniqueTimeUUIDinMillis(),
        10, false);
    c = new Composite();
    c.setDynamic(false);
    c.setSerializersByPosition(StringSerializer.get(), UUIDSerializer.get(),
        BigIntegerSerializer.get());
    c.deserialize(b.slice());
    assertTrue(c.get(0) instanceof String);
    assertTrue(c.get(1) instanceof UUID);
    assertTrue(c.get(2) instanceof BigInteger);
  }

  // from the Casssandra DynamicCompositeTypeTest unit test
  private ByteBuffer createDynamicCompositeKey(String s, UUID uuid, int i,
      boolean lastIsOne) {
    ByteBuffer bytes = ByteBufferUtil.bytes(s);
    int totalSize = 0;
    if (s != null) {
      totalSize += 2 + 2 + bytes.remaining() + 1;
      if (uuid != null) {
        totalSize += 2 + 2 + 16 + 1;
        if (i != -1) {
          totalSize += 2 + "IntegerType".length() + 2 + 1 + 1;
        }
      }
    }

    ByteBuffer bb = ByteBuffer.allocate(totalSize);

    if (s != null) {
      bb.putShort((short) (0x8000 | 'b'));
      bb.putShort((short) bytes.remaining());
      bb.put(bytes);
      bb.put((uuid == null) && lastIsOne ? (byte) 1 : (byte) 0);
      if (uuid != null) {
        bb.putShort((short) (0x8000 | 't'));
        bb.putShort((short) 16);
        bb.put(UUIDGen.decompose(uuid));
        bb.put((i == -1) && lastIsOne ? (byte) 1 : (byte) 0);
        if (i != -1) {
          bb.putShort((short) "IntegerType".length());
          bb.put(ByteBufferUtil.bytes("IntegerType"));
          // We are putting a byte only because our test use ints that
          // fit in a byte *and* IntegerType.fromString() will
          // return something compatible (i.e, putting a full int here
          // would break 'fromStringTest')
          bb.putShort((short) 1);
          bb.put((byte) i);
          bb.put(lastIsOne ? (byte) 1 : (byte) 0);
        }
      }
    }
    bb.rewind();
    return bb;
  }

  // from the Casssandra CompositeTypeTest unit test
  static ByteBuffer createCompositeKey(String s, UUID uuid, int i,
      boolean lastIsOne) {
    ByteBuffer bytes = ByteBufferUtil.bytes(s);
    int totalSize = 0;
    if (s != null) {
      totalSize += 2 + bytes.remaining() + 1;
      if (uuid != null) {
        totalSize += 2 + 16 + 1;
        if (i != -1) {
          totalSize += 2 + 1 + 1;
        }
      }
    }

    ByteBuffer bb = ByteBuffer.allocate(totalSize);

    if (s != null) {
      bb.putShort((short) bytes.remaining());
      bb.put(bytes);
      bb.put((uuid == null) && lastIsOne ? (byte) 1 : (byte) 0);
      if (uuid != null) {
        bb.putShort((short) 16);
        bb.put(UUIDGen.decompose(uuid));
        bb.put((i == -1) && lastIsOne ? (byte) 1 : (byte) 0);
        if (i != -1) {
          // We are putting a byte only because our test use ints that fit in a
          // byte *and* IntegerType.fromString() will
          // return something compatible (i.e, putting a full int here would
          // break 'fromStringTest')
          bb.putShort((short) 1);
          bb.put((byte) i);
          bb.put(lastIsOne ? (byte) 1 : (byte) 0);
        }
      }
    }
    bb.rewind();
    return bb;
  }

}
