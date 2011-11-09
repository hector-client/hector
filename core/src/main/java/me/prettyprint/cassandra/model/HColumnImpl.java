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
public final class HColumnImpl<N,V> implements HColumn<N, V> {

  private Column column;
  private Serializer<N> nameSerializer;
  private Serializer<V> valueSerializer;
  
  private N cachedName;
  private V cachedValue;

  public HColumnImpl(N name, V value, long clock, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(name, "name is null");
    notNull(value, "value is null");

    this.column = new Column(nameSerializer.toByteBuffer(name));
    this.column.setValue(valueSerializer.toByteBuffer(value));
    this.column.setTimestamp(clock);
  }

  public HColumnImpl(N name, V value, long clock, int ttl,
		Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	  this(name, value, clock, nameSerializer, valueSerializer);
	  setTtl(ttl);
  }

  public HColumnImpl(Column thriftColumn, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(thriftColumn, "thriftColumn is null");
    this.column = thriftColumn;
  }

  public HColumnImpl(Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    notNull(nameSerializer, "nameSerializer is null");
    notNull(valueSerializer, "valueSerializer is null");
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
    this.column = new Column();
  }

  public HColumnImpl(N name, V value, long clock) {
    this(name, value, clock, SerializerTypeInferer.<N>getSerializer(name),
        SerializerTypeInferer.<V>getSerializer(value));
  }

  @Override
  public HColumn<N,V> setName(N name) {
    notNull(name, "name is null");
    this.column.setName(nameSerializer.toByteBuffer(name));
    this.cachedName = null;
    return this;
  }

  @Override
  public HColumn<N, V> setValue(V value) {
    notNull(value, "value is null");
    this.column.setValue(valueSerializer.toByteBuffer(value));
    this.cachedValue = null;
    return this;
  }

  @Override
  public HColumn<N,V> setClock(long clock) {
    this.column.setTimestamp(clock);
    return this;
  }

  /**
   * Set the time-to-live value for this column in seconds.
   * The server will mark this column as deleted once the number of seconds has elapsed.
   */
  @Override
  public HColumn<N,V> setTtl(int ttl) {
    this.column.setTtl(ttl);
    return this;
  }

  @Override
  public int getTtl() {
    return this.column.ttl;
  }

  @Override
  public N getName() {
    if ( column.isSetName() ) {
      if ( null == cachedName) {
        cachedName = nameSerializer.fromByteBuffer(column.name.duplicate());
      }
      return cachedName;
    }
    else {
      return null;
    }
  }

  @Override
  public V getValue() {
    if ( column.isSetValue() ) {
      if ( null == cachedValue) {
        cachedValue = valueSerializer.fromByteBuffer(column.value.duplicate());
      }
      return cachedValue;
    }
    else {
      return null;
    }
  }


  @Override
  public long getClock() {
    return column.timestamp;
  }

  public Column toThrift() {
    return column;
  }

  public HColumn<N, V> fromThrift(Column c) {
    notNull(c, "column is null");
    this.column = c;
    return this;
  }

  @Override
  public Serializer<N> getNameSerializer() {
    return nameSerializer;
  }

  @Override
  public Serializer<V> getValueSerializer() {
    return valueSerializer;
  }

  @Override
  public ByteBuffer getNameBytes() {
    return column.isSetName() ? column.name.duplicate() : null;
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
    cachedName = null;
    cachedValue = null;
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
