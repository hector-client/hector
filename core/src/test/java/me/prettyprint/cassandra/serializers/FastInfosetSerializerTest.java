package me.prettyprint.cassandra.serializers;

import me.prettyprint.hector.api.Serializer;

/**
 * @author shuzhang0@gmail.com
 * 
 */
public class FastInfosetSerializerTest extends JaxbSerializerTest {

  @Override
  protected Serializer<Object> getSerializer() {
    return new FastInfosetSerializer(JaxbString.class);
  }

}
