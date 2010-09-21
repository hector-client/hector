package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;
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
  private long timestamp;
  private final Serializer<N> nameSerializer;
  private final Serializer<V> valueSerializer;

  public HColumnImpl(N name, V value, long timestamp, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(name, "name is null");
    notNull(value, "value is null");

    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
  }

  public HColumnImpl(Column thriftColumn, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    this(nameSerializer, valueSerializer);
    notNull(thriftColumn, "thriftColumn is null");
    name = nameSerializer.fromBytes(thriftColumn.getName());
    value = valueSerializer.fromBytes(thriftColumn.getValue());
    timestamp = thriftColumn.timestamp;
  }

  /*package*/ HColumnImpl(Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    notNull(nameSerializer, "nameSerializer is null");
    notNull(valueSerializer, "valueSerializer is null");
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  @Override
  public HColumn<N, V> setName(N name) {
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

  HColumn<N, V> setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
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
  public long getTimestamp() {
    return timestamp;
  }

  public Column toThrift() {
    return new Column(nameSerializer.toBytes(name), valueSerializer.toBytes(value), timestamp);
  }

  public HColumn<N, V> fromThrift(Column c) {
    notNull(c, "column is null");
    name = nameSerializer.fromBytes(c.name);
    value = valueSerializer.fromBytes(c.value);
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
    return new HashCodeBuilder().append(name).append(value).append(timestamp).toHashCode();
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
        append(value, other.value).append(timestamp, other.timestamp).isEquals();
  }
}
