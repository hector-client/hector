package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import me.prettyprint.hector.api.beans.Composite;
import org.junit.Test;

public class CompositeSerializerTest {

    @Test
    public void testCompositeSerializeAndBack() {
      Composite c = new Composite("hello", "world", 3L);
      CompositeSerializer cs = new CompositeSerializer();
      ByteBuffer bb = cs.toByteBuffer(c);
      Composite c1 = cs.fromByteBuffer(bb);
      assertEquals(c.hashCode(), c1.hashCode());
    }
    
    @Test
    public void testCreateSerializer() {
      Composite c = new Composite("hello", "world", 1L);
      CompositeSerializer cs = new CompositeSerializer();
      ByteBuffer bb = cs.toByteBuffer(c);
        
      CompositeSerializer cs1 = new CompositeSerializer();
      cs1.addSerializers(StringSerializer.get());
      cs1.addSerializers(StringSerializer.get());
      cs1.addSerializers(LongSerializer.get());
      Composite c1 = cs1.fromByteBuffer(bb);
      assertEquals(c.hashCode(), c1.hashCode());
    }
}
