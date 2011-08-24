package me.prettyprint.hector.api;

import static me.prettyprint.hector.api.ddl.ComparatorType.UUIDTYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.BigIntegerSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.DynamicCompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.DynamicComposite;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.DynamicCompositeType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LexicalUUIDType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.db.marshal.UUIDType;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.UUIDGen;
import org.junit.Test;

public class CompositeTest {

  @Test
  public void testDynamicSerialization() throws Exception {

    // test correct serialization sizes for strings
    DynamicComposite c = new DynamicComposite();
    c.add("String1");
    ByteBuffer b = c.serialize();
    assertEquals(b.remaining(), 12);

    c.add("String2");
    b = c.serialize();
    assertEquals(b.remaining(), 24);

    // test deserialization of strings
    c = new DynamicComposite();
    c.deserialize(b);
    assertEquals(2, c.size());
    Object o = c.get(0);
    assertEquals("String1", o);
    o = c.get(1);
    assertEquals("String2", o);

    // test serialization and deserialization of longs
    c = new DynamicComposite();
    c.add(new Long(10));
    b = c.serialize();
    c = new DynamicComposite();
    c.deserialize(b);
    o = c.get(0);
    assertTrue(o instanceof Long);

    // test serialization and deserialization of random UUIDS
    c = new DynamicComposite();
    c.add(UUID.randomUUID());
    b = c.serialize();
    c = DynamicComposite.fromByteBuffer(b);
    o = c.get(0);
    assertTrue(o instanceof UUID);
    assertEquals(UUIDTYPE.getTypeName(), c.getComponent(0).getComparator());

    // test serialization and deserialization of time-based UUIDS
    c = new DynamicComposite();
    c.add(TimeUUIDUtils.getUniqueTimeUUIDinMillis());
    b = c.serialize();
    c = DynamicComposite.fromByteBuffer(b);
    o = c.get(0);
    assertTrue(o instanceof UUID);
    assertEquals(UUIDTYPE.getTypeName(), c.getComponent(0).getComparator());

    // test compatibility with Cassandra unit tests
    b = createDynamicCompositeKey("Hello",
        TimeUUIDUtils.getUniqueTimeUUIDinMillis(), 10, false);
    c = new DynamicComposite();
    c.deserialize(b.slice());
    o = c.get(0);
    assertTrue(o instanceof ByteBuffer);
    assertEquals("Hello", c.get(0, StringSerializer.get()));

    o = c.get(1);
    assertEquals(UUID.class, o.getClass());

    o = c.get(2);
    assertEquals(BigInteger.class, o.getClass());
    assertEquals(BigInteger.valueOf(10), o);

    // test using supplied deserializer rather than auto-mapped
    c = new DynamicComposite();
    c.deserialize(b.slice());
    assertTrue(c.get(0, ByteBufferSerializer.get()) instanceof ByteBuffer);
    assertTrue(c.get(1, ByteBufferSerializer.get()) instanceof ByteBuffer);
    assertTrue(c.get(2, ByteBufferSerializer.get()) instanceof ByteBuffer);

    // test setting a deserializer for specific components
    c = new DynamicComposite();
    c.setSerializersByPosition(StringSerializer.get(), null,
        ByteBufferSerializer.get());
    c.deserialize(b.slice());
    assertTrue(c.get(0) instanceof String);
    assertTrue(c.get(1) instanceof UUID);
    assertTrue(c.get(2) instanceof ByteBuffer);

    b = DynamicComposite.toByteBuffer(1, "string",
        TimeUUIDUtils.getUniqueTimeUUIDinMillis());
    c = DynamicComposite.fromByteBuffer(b);
    assertTrue(c.get(0) instanceof BigInteger);
    assertTrue(c.get(1) instanceof String);
    assertTrue(c.get(2) instanceof UUID);

    b = DynamicComposite.toByteBuffer((long) 1, "string",
        TimeUUIDUtils.getUniqueTimeUUIDinMillis());
    c = DynamicComposite.fromByteBuffer(b);
    assertTrue(c.get(0) instanceof Long);
    assertTrue(c.get(1) instanceof String);
    assertTrue(c.get(2) instanceof UUID);

    b = DynamicComposite.toByteBuffer((byte) 1, "string", UUID.randomUUID());
    c = DynamicComposite.fromByteBuffer(b);
    assertTrue(c.get(0) instanceof BigInteger);
    assertTrue(c.get(1) instanceof String);
    assertTrue(c.get(2) instanceof UUID);

    b = DynamicComposite.toByteBuffer(Arrays.asList(Arrays.asList(0, 1, 2), 3,
        4, 5, Arrays.asList(6, 7, 8)));
    c = DynamicComposite.fromByteBuffer(b);
    for (int i = 0; i < 9; i++) {
      o = c.get(i);
      assertTrue(o instanceof BigInteger);
      assertEquals(i, ((BigInteger) o).intValue());
    }

    b = DynamicComposite.toByteBuffer("foo");
    c = DynamicComposite.fromByteBuffer(b);
    b = c.getComponent(0).getBytes();
    UTF8Type.instance.validate(b);

  }

  @Test
  public void testNullValueSerialization() throws Exception {

    // test correct serialization with null values and user specified
    // serialization
    DynamicComposite c = new DynamicComposite();
    c.addComponent(null, StringSerializer.get());

    DynamicCompositeSerializer serializer = new DynamicCompositeSerializer();

    ByteBuffer buff = serializer.toByteBuffer(c);

    DynamicComposite result = serializer.fromByteBuffer(buff);

    assertNull(result.get(0));
  }

  @Test
  public void testStaticSerialization() throws Exception {

    ByteBuffer b = createCompositeKey("Hello",
        TimeUUIDUtils.getUniqueTimeUUIDinMillis(), 10, false);
    Composite c = new Composite();
    c.setSerializersByPosition(StringSerializer.get(), UUIDSerializer.get(),
        BigIntegerSerializer.get());
    c.deserialize(b.slice());
    assertTrue(c.get(0) instanceof String);
    assertTrue(c.get(1) instanceof UUID);
    assertTrue(c.get(2) instanceof BigInteger);
  }

  @Test
  public void testEquality() throws Exception {
    DynamicCompositeType instance = getDefaultDynamicComparator();

    DynamicComposite c1 = new DynamicComposite(10, "foo");
    DynamicComposite c2 = new DynamicComposite(10, "foo");

    assertEquals(0, instance.compare(c1.serialize(), c2.serialize()));

    c2.setEquality(ComponentEquality.GREATER_THAN_EQUAL);
    assertEquals(-1, instance.compare(c1.serialize(), c2.serialize()));

    c2.setEquality(ComponentEquality.LESS_THAN_EQUAL);
    assertEquals(1, instance.compare(c1.serialize(), c2.serialize()));

    c2.setEquality(ComponentEquality.EQUAL);
    assertEquals(0, instance.compare(c1.serialize(), c2.serialize()));

    c1.setEquality(ComponentEquality.LESS_THAN_EQUAL);
    assertEquals(-1, instance.compare(c1.serialize(), c2.serialize()));

    c1.setEquality(ComponentEquality.GREATER_THAN_EQUAL);
    assertEquals(1, instance.compare(c1.serialize(), c2.serialize()));
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

  @SuppressWarnings("rawtypes")
  public DynamicCompositeType getDefaultDynamicComparator() {
    Map<Byte, AbstractType> aliases = new HashMap<Byte, AbstractType>();
    aliases.put((byte) 'a', AsciiType.instance);
    aliases.put((byte) 'b', BytesType.instance);
    aliases.put((byte) 'i', IntegerType.instance);
    aliases.put((byte) 'x', LexicalUUIDType.instance);
    aliases.put((byte) 'l', LongType.instance);
    aliases.put((byte) 't', TimeUUIDType.instance);
    aliases.put((byte) 's', UTF8Type.instance);
    aliases.put((byte) 'u', UUIDType.instance);
    DynamicCompositeType comparator = DynamicCompositeType.getInstance(aliases);
    return comparator;
  }

}
