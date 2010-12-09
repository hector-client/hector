package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Serializer;


/**
 * The BytesExtractor is a simple identity function. It supports the Extractor interface and
 * implements the fromBytes and toBytes as simple identity functions.
 *
 * @author Ran Tavory
 *
 */
public final class ByteBufferSerializer extends AbstractSerializer<ByteBuffer> implements Serializer<ByteBuffer>{

  private static ByteBufferSerializer instance = new ByteBufferSerializer();

  public static ByteBufferSerializer get() {
    return instance;
  }

  @Override
  public ByteBuffer fromByteBuffer(ByteBuffer bytes) {
    return bytes;
  }

  @Override
  public ByteBuffer toByteBuffer(ByteBuffer obj) {
    return obj;
  }

  @Override
  public List<ByteBuffer> toBytesList(List<ByteBuffer> list) {
    return list;
  }

  @Override
  public List<ByteBuffer> fromBytesList(List<ByteBuffer> list) {
    return list;
  }

  @Override
  public <V> Map<ByteBuffer, V> toBytesMap(Map<ByteBuffer, V> map) {
    return map;
  }

  @Override
  public <V> Map<ByteBuffer, V> fromBytesMap(Map<ByteBuffer, V> map) {
    return map;
  }
}
