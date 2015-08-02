package me.prettyprint.cassandra.serializers;

import me.prettyprint.hector.api.beans.Composite;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * @author Eric Zoerner <a href="mailto:ezoerner@ebuddy.com">ezoerner@ebuddy.com</a>
 */
public class CompositeSerializerTest {

  /** Tests deserializing a composite with the singleton CompositeSerializer, which fails. */
  @Test
  public void testFromByteBufferWithSingletonCompositeSerializer() throws Exception {
    Composite composite = new Composite("test", 42);
    CompositeSerializer ser = CompositeSerializer.get();
    ByteBuffer byteBuffer = ser.toByteBuffer(composite);

    assertEquals(composite, ser.fromByteBuffer(byteBuffer));
  }

  /** Tests deserializing a composite with a CompositeSerializer constructed with Serializers. */
  @Test
  public void testFromByteBufferWithConstructedSerializer() throws Exception {
    Composite composite = new Composite("test", 42);
    CompositeSerializer ser = new CompositeSerializer(StringSerializer.get(), BigIntegerSerializer.get());
    ByteBuffer byteBuffer = ser.toByteBuffer(composite);

    assertEquals(composite, ser.fromByteBuffer(byteBuffer));

  }
}
