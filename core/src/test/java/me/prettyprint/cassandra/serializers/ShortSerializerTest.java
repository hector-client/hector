package me.prettyprint.cassandra.serializers;

import java.util.Collection;
import java.util.HashSet;

import me.prettyprint.hector.api.Serializer;

/**
 * Unit tests for {@link ShortSerializer}.
 * 
 * @author shuzhang0@gmail.com
 * 
 */
public class ShortSerializerTest extends SerializerBaseTest<Short> {

  @Override
  protected Serializer<Short> getSerializer() {
    return ShortSerializer.get();
  }

  @Override
  protected Collection<Short> getTestData() {
    Collection<Short> testData = new HashSet<Short>();
    testData.add((short) 1);
    testData.add((short) 0);
    testData.add(Short.MAX_VALUE);
    testData.add(Short.MIN_VALUE);

    return testData;
  }

}
