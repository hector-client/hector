package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;
import java.util.Collection;

import junit.framework.Assert;
import me.prettyprint.hector.api.Serializer;

import org.junit.Test;

/**
 * A base test class for {@link Serializer}s. <br>
 * <br>
 * Tests for: <br>
 * 1. Valid serialization roundtrip for a collection of objects. <br>
 * 2. Proper handling of NULL.
 * 
 * @author shuzhang0@gmail.com
 * 
 * @param <T>
 *          the type which the Serializer under test can serialize.
 */
public abstract class SerializerBaseTest<T> {

  /**
   * Validate serialization roundtrip for a collection of objects (defined by
   * subclasses).
   */
  @Test
  public void testRoundTrip() {
    for (T object : getTestData()) {
      Assert.assertEquals(object, getSerializer().fromByteBuffer(
          getSerializer().toByteBuffer(object)));
    }
  }

  /** Validate handling of NULL. */
  @Test
  public void testNullObjectRoundTrip() {
    Assert.assertNull(getSerializer().fromByteBuffer(
        getSerializer().toByteBuffer(null)));
  }
  
  @Test
  public void testByteBufferWithSharedBackingArrayIsOk() {
	  for (T object : getTestData()) {
		  byte[] bytes = getSerializer().toBytes(object);
		  ByteBuffer byteBufferWithSharedBackingArray = copyIntoLargerArrayAndWrap(bytes);
	      T deserialized = getSerializer().fromByteBuffer(byteBufferWithSharedBackingArray);
	      Assert.assertEquals(object, deserialized);
	  }
  }

  private ByteBuffer copyIntoLargerArrayAndWrap(byte[] bytes) {
	int paddingLeft = 5;
	  int paddingRight = 5;		  
	  byte[] sharedBytesWithOtherStuff = new byte[bytes.length+paddingLeft+paddingRight];
	  for (int i=0; i<bytes.length; i++)
	  {
		  sharedBytesWithOtherStuff[i+paddingLeft] = bytes[i];
	  }
	  ByteBuffer byteBufferWithSharedBackingArray = ByteBuffer.wrap(sharedBytesWithOtherStuff, paddingLeft, bytes.length);
	return byteBufferWithSharedBackingArray;
  }

  /**
   * @return the serializer under test.
   */
  protected abstract Serializer<T> getSerializer();

  /**
   * @return a collection of data to test for valid serialization roundtrip.
   */
  protected abstract Collection<T> getTestData();
}
