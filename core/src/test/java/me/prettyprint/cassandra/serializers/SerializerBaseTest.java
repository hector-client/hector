package me.prettyprint.cassandra.serializers;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import me.prettyprint.hector.api.Serializer;

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

  /**
   * @return the serializer under test.
   */
  protected abstract Serializer<T> getSerializer();

  /**
   * @return a collection of data to test for valid serialization roundtrip.
   */
  protected abstract Collection<T> getTestData();
}
