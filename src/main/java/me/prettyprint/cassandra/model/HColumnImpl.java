package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;
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
 *
 */
public final class HColumnImpl<N,V> implements HColumn<N, V> {

  private N name;
  private V value;
  private long clock;
  private int ttl;
  private Serializer<N> nameSerializer;
  private Serializer<V> valueSerializer;

  public HColumnImpl(N name, V value, long clock, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(name, "name is null");
    notNull(value, "value is null");

    this.name = name;
    this.value = value;
    this.clock = clock;
  }

  public HColumnImpl(Column thriftColumn, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(thriftColumn, "thriftColumn is null");
    name = nameSerializer.fromBytes(thriftColumn.getName());
    value = valueSerializer.fromBytes(thriftColumn.getValue());
    clock = thriftColumn.timestamp;
  }

  public HColumnImpl(Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    notNull(nameSerializer, "nameSerializer is null");
    notNull(valueSerializer, "valueSerializer is null");
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  public HColumnImpl(N name, V value, long clock) {
    this(name, value, clock, SerializerTypeInferer.<N>getSerializer(name),
        SerializerTypeInferer.<V>getSerializer(value));
  }

  @Override
  public HColumn<N,V> setName(N name) {
    notNull(name, "name is null");
    this.name = name;
    return this;
  }

  @Override
  public HColumn<N, V> setValue(V value) {
    notNull(value, "value is null");
    this.value = value;
    return this;
  }

  @Override
  public HColumn<N,V> setClock(long clock) {
    this.clock = clock;
    return this;
  }

  @Override
  public HColumn<N,V> setTtl(int ttl) {
    this.ttl = ttl;
    return this;
  }

  @Override
  public int getTtl() {
    return ttl;
  }

  @Override
  public N getName() {
    return name;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
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

  @Override
  public Serializer<N> getNameSerializer() {
    return nameSerializer;
  }

  @Override
  public Serializer<V> getValueSerializer() {
    return valueSerializer;
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
    HColumnImpl<N,V> other = (HColumnImpl<N,V>) obj;
    return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, other.name).
        append(value, other.value).append(clock, other.clock).isEquals();
  }
}
