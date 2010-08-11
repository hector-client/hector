package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;

import org.apache.cassandra.thrift.Clock;
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
public final class HColumn<N,V> {

  private N name;
  private V value;
  private Clock clock;
  private final Extractor<N> nameExtractor;
  private final Extractor<V> valueExtractor;

  /*package*/ HColumn(N name, V value, Clock clock, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    this(nameExtractor, valueExtractor);
    notNull(name, "name is null");
    notNull(value, "value is null");

    this.name = name;
    this.value = value;
    this.clock = clock;
  }

  /*package*/ HColumn(Column thriftColumn, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    this(nameExtractor, valueExtractor);
    notNull(thriftColumn, "thriftColumn is null");
    name = nameExtractor.fromBytes(thriftColumn.getName());
    value = valueExtractor.fromBytes(thriftColumn.getValue());
  }

  /*package*/ HColumn(Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    notNull(nameExtractor, "nameExtractor is null");
    notNull(valueExtractor, "valueExtractor is null");
    this.nameExtractor = nameExtractor;
    this.valueExtractor = valueExtractor;
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

  HColumn<N,V> setClock(Clock clock) {
    this.clock = clock;
    return this;
  }

  public N getName() {
    return name;
  }

  public V getValue() {
    return value;
  }

  Clock getClock() {
    return clock;
  }

  public Column toThrift() {
    return new Column(nameExtractor.toBytes(name), valueExtractor.toBytes(value), clock);
  }

  public HColumn<N, V> fromThrift(Column c) {
    notNull(c, "column is null");
    name = nameExtractor.fromBytes(c.name);
    value = valueExtractor.fromBytes(c.value);
    return this;
  }

  public Extractor<N> getNameExtractor() {
    return nameExtractor;
  }

  public Extractor<V> getValueExtractor() {
    return valueExtractor;
  }

  public byte[] getValueBytes() {
    return valueExtractor.toBytes(getValue());
  }

  public byte[] getNameBytes() {
    return nameExtractor.toBytes(getName());
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
