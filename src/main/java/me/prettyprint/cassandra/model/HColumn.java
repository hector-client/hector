package me.prettyprint.cassandra.model;

import org.apache.cassandra.thrift.Column;

/**
 * Hector Column definition.
 *
 * TODO(ran): This is still experimental; not production ready; work in progress
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class HColumn<K,V> {

  private K name;
  private V value;
  private long timestamp;
  private final Extractor<K> nameExtractor;
  private final Extractor<V> valueExtractor;

  public HColumn(K name, V value, long timestamp, Extractor<K> nameExtractor, 
      Extractor<V> valueExtractor) {
    this(nameExtractor, valueExtractor);
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
  }
  
  public HColumn(Column thriftColumn, Extractor<K> nameExtractor, 
      Extractor<V> valueExtractor) {
    this(nameExtractor, valueExtractor);
    if (thriftColumn == null) {
      return;
    }
    name = nameExtractor.fromBytes(thriftColumn.getName());
    value = valueExtractor.fromBytes(thriftColumn.getValue());
  }

  public HColumn(Extractor<K> nameExtractor, Extractor<V> valueExtractor) {
    this.nameExtractor = nameExtractor;
    this.valueExtractor = valueExtractor;
  }

  HColumn<K,V> setName(K name) {
    this.name = name;
    return this;
  }

  HColumn<K,V> setValue(V value) {
    this.value = value;
    return this;
  }

  HColumn<K,V> setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  K getName() {
    return name;
  }

  V getValue() {
    return value;
  }

  long getTimestamp() {
    return timestamp;
  }
  
  public Column toThrift() {
    return new Column(nameExtractor.toBytes(name), valueExtractor.toBytes(value), timestamp);
  }
  
  public HColumn<K, V> fromThrift(Column c) {
    if (c == null) {
      return this;
    }
    name = nameExtractor.fromBytes(c.name);
    value = valueExtractor.fromBytes(c.value);
    return this;
  }
}
