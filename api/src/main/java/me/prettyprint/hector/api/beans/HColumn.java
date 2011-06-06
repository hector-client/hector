package me.prettyprint.hector.api.beans;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.Serializer;

/**
 * Hector Column definition.
 *
 * @param <N> The type of the column name
 * @param <V> The type of the column value
 *
 * @author Ran Tavory (rantav@gmail.com)
 * @author zznate
 */
public interface HColumn<N, V> {

  HColumn<N, V> setName(N name);

  HColumn<N, V> setValue(V value);

  N getName();

  V getValue();

  /**
   * (Advanced) Returns the underlying ByteBuffer for the value via ByteBuffer.duplicate().
   */
  ByteBuffer getValueBytes();
  
  /**
   * (Advanced) Returns the underlying ByteBuffer for the name via ByteBuffer.duplicate().
   */
  ByteBuffer getNameBytes();
  
  long getClock();

  HColumn<N,V> setClock(long clock);

  int getTtl();

  HColumn<N,V> setTtl(int ttl);
  
  HColumn<N,V> clear();
  
  HColumn<N,V> apply(V value, long clock, int ttl);

  Serializer<N> getNameSerializer();

  Serializer<V> getValueSerializer();

}