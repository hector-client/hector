package me.prettyprint.hector.api.beans;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.Serializer;

/**
 * Hector Counter Column definition.
 *
 * @param <N> The type of the column name
 *
 * @author patricioe (patricioe@gmail.com)
 */
public interface HCounterColumn<N> {

  HCounterColumn<N> setName(N name);

  HCounterColumn<N> setValue(Long value);

  N getName();

  Long getValue();

  
  /**
   * (Advanced) Returns the underlying ByteBuffer for the name via ByteBuffer.duplicate().
   */
  ByteBuffer getNameBytes();
  
  HCounterColumn<N> clear();
  
  int getTtl();

  HCounterColumn<N> setTtl(int ttl);
  
  HCounterColumn<N> apply(Long value, int ttl);

  Serializer<N> getNameSerializer();

}