package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;

import org.apache.cassandra.thrift.Column;

/**
 * Base class for different column implementations
 * 
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 * @param <T>
 * 
 */
public abstract class HAbstractColumnImpl<N, V, T> {

  protected Column column;
  protected Serializer<N> nameSerializer;
  protected Serializer<V> valueSerializer;

  public HAbstractColumnImpl(Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    notNull(nameSerializer, "nameSerializer is null");
    notNull(valueSerializer, "valueSerializer is null");
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
    this.column = new Column();
  }

  protected abstract T getThis();

  public T setName(N name) {
    notNull(name, "name is null");
    this.column.setName(nameSerializer.toByteBuffer(name));
    return getThis();
   }
  
  public N getName() {    
    return column.isSetName() ? nameSerializer.fromByteBuffer(column.name.duplicate()) : null;
  }

  public T setValue(V value) {
    notNull(value, "value is null");
    this.column.setValue(valueSerializer.toByteBuffer(value));
    return getThis();
  }

  public Serializer<N> getNameSerializer() {
    return nameSerializer;
  }

  public Serializer<V> getValueSerializer() {
    return valueSerializer;
  }

  /**
   * Set the time-to-live value for this column in seconds. The server will
   * mark this column as deleted once the number of seconds has elapsed.
   */
  public T setTtl(int ttl) {
    this.column.setTtl(ttl);
    return getThis();
  }
  
  public int getTtl() {
    return this.column.ttl;
  }

  public ByteBuffer getNameBytes() {  
    return column.isSetName() ? column.name.duplicate() : null;
  }
  
  public Column toThrift() {
    return column;
  }

  public T fromThrift(Column c) {
    notNull(c, "column is null");
    this.column = c;
    return getThis();
  }

}
