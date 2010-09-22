package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;

import me.prettyprint.cassandra.serializers.SerializerTypeInferer;

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
 *
 */
public class HColumn<N,V> {

  private N name;
  private V value;
  private long clock;
  private int ttl;
  private Serializer<N> nameSerializer;
  private Serializer<V> valueSerializer;

  public HColumn(N name, V value, long clock, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(name, "name is null");
    notNull(value, "value is null");
    
    this.name = name;
    this.value = value;
    this.clock = clock;
  }

  public HColumn(Column thriftColumn, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(thriftColumn, "thriftColumn is null");
    name = nameSerializer.fromBytes(thriftColumn.getName());
    value = valueSerializer.fromBytes(thriftColumn.getValue());
    clock = thriftColumn.timestamp;
  }

  public HColumn(Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    notNull(nameSerializer, "nameSerializer is null");
    notNull(valueSerializer, "valueSerializer is null");
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  public HColumn(N name, V value, long clock) {
    this(name, value, clock, SerializerTypeInferer.<N>getSerializer(name),
        SerializerTypeInferer.<V>getSerializer(value));
  }

  public HColumn<N,V> setName(N name) {
    notNull(name, "name is null");
    this.name = name;
    return this;
  }

  public HColumn<N,V> setValue(V value) {
    notNull(value, "value is null");
    this.value = value;
    return this;
  }

  HColumn<N,V> setClock(long clock) {
    this.clock = clock;
    return this;
  }
  
  HColumn<N,V> setTtl(int ttl) {
    this.ttl = ttl;
    return this;
  }

  public int getTtl() {
    return ttl;
  }
  
  public N getName() {
    return name;
  }

  public V getValue() {
    return value;
  }

  public long getClock() {
    return clock;
  }

  public Column toThrift() {
    return ttl > 0 ? new Column(nameSerializer.toBytes(name), valueSerializer.toBytes(value), clock).setTtl(ttl) :
      new Column(nameSerializer.toBytes(name), valueSerializer.toBytes(value), clock);
  }

  public HColumn<N, V> fromThrift(Column c) {
    notNull(c, "column is null");
    name = nameSerializer.fromBytes(c.name);
    value = valueSerializer.fromBytes(c.value);
    clock = c.timestamp;
    ttl = c.ttl;
    return this;
  }

  public Serializer<N> getNameSerializer() {
    return nameSerializer;
  }

  public Serializer<V> getValueSerializer() {
    return valueSerializer;
  }

  public byte[] getValueBytes() {
    return valueSerializer.toBytes(getValue());
  }

  public byte[] getNameBytes() {
    return nameSerializer.toBytes(getName());
  }

  @Override
  public String toString() {
    return "HColumn(" + name + "=" + value + ")";
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(name).append(value).append(clock).toHashCode();
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
    HColumn<N,V> other = (HColumn<N,V>) obj;
    return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, other.name).
        append(value, other.value).append(clock, other.clock).isEquals();
  }
}
