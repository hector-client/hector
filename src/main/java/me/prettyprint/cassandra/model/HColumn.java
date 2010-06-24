package me.prettyprint.cassandra.model;

import org.apache.cassandra.thrift.Column;

/**
 * Hector Column definition.
 * 
 * @param <N> The type of the column name
 * @param <V> The type of the column value
 *
 * TODO(ran): This is still experimental; not production ready; work in progress
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class HColumn<N,V> {

  private N name;
  private V value;
  private long timestamp;
  private final Extractor<N> nameExtractor;
  private final Extractor<V> valueExtractor;

  public HColumn(N name, V value, long timestamp, Extractor<N> nameExtractor, 
      Extractor<V> valueExtractor) {
    this(nameExtractor, valueExtractor);
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
  }
  
  public HColumn(Column thriftColumn, Extractor<N> nameExtractor, 
      Extractor<V> valueExtractor) {
    this(nameExtractor, valueExtractor);
    if (thriftColumn == null) {
      return;
    }
    name = nameExtractor.fromBytes(thriftColumn.getName());
    value = valueExtractor.fromBytes(thriftColumn.getValue());
  }

  public HColumn(Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    this.nameExtractor = nameExtractor;
    this.valueExtractor = valueExtractor;
  }

  public HColumn<N,V> setName(N name) {
    this.name = name;
    return this;
  }

  public HColumn<N,V> setValue(V value) {
    this.value = value;
    return this;
  }

  HColumn<N,V> setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public N getName() {
    return name;
  }

  public V getValue() {
    return value;
  }

  long getTimestamp() {
    return timestamp;
  }
  
  public Column toThrift() {
    return new Column(nameExtractor.toBytes(name), valueExtractor.toBytes(value), timestamp);
  }
  
  public HColumn<N, V> fromThrift(Column c) {
    if (c == null) {
      return this;
    }
    name = nameExtractor.fromBytes(c.name);
    value = valueExtractor.fromBytes(c.value);
    return this;
  }
}
