package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;

import org.apache.cassandra.thrift.Column;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Hector Column definition.
 *
 * @param <N> The type of the column name
 * @param <V> The type of the column value
 *
 * @author Ran Tavory (rantav@gmail.com)
 * @author zznate
 */
public final class HColumnImpl<N,V> extends HAbstractColumnImpl<N,V, HColumn<N,V>> implements HColumn<N, V> {

  public HColumnImpl(N name, V value, long clock, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);

    this.column = new Column(nameSerializer.toByteBuffer(name), 
        valueSerializer.toByteBuffer(value), clock);
  }

  public HColumnImpl(Column thriftColumn, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(thriftColumn, "thriftColumn is null");
    this.column = thriftColumn;
  }

  public HColumnImpl(Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(nameSerializer, valueSerializer);
  }

  public HColumnImpl(N name, V value, long clock) {
    this(name, value, clock, SerializerTypeInferer.<N>getSerializer(name),
        SerializerTypeInferer.<V>getSerializer(value));
  }
  
  @Override
  protected HColumn<N, V> getThis() {
  	return this;
  }

  @Override
  public HColumn<N,V> setClock(long clock) {
    this.column.setTimestamp(clock);
    return this;
  }


  @Override
  public V getValue() {       
    return column.isSetValue() ? valueSerializer.fromByteBuffer(column.value.duplicate()) : null;
  }

  @Override
  public long getClock() {
    return column.timestamp;
  }


  @Override
  public ByteBuffer getValueBytes() {    
    return column.isSetValue() ? column.value.duplicate() : null;
  }

  /**
   * Clear value, timestamp and ttl (the latter two set to '0') leaving only the column name
   */
  @Override
  public HColumn<N,V> clear() {    
    column.value = null;
    column.timestamp = 0;
    column.ttl = 0;
    column.setTimestampIsSet(false);
    column.setTtlIsSet(false);
    column.setValueIsSet(false);
    return this;
  }

  @Override
  public HColumn<N, V> apply(V value, long clock, int ttl) {
    setValue(value);
    column.setTimestamp(clock);
    column.setTtl(ttl);
    return this;
  }

  public HColumn<N, V> apply(Column c) {
    this.column = c;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("HColumn(%s=%s)",getName(), getValue());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getName()).append(getValue()).append(getClock()).toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    @SuppressWarnings("unchecked")
    HColumnImpl<N,V> other = (HColumnImpl<N,V>) obj;
    return new EqualsBuilder().appendSuper(super.equals(obj)).append(getName(), other.getName()).
        append(getValue(), other.getValue()).append(getClock(), other.getClock()).isEquals();
  }


}
