package me.prettyprint.cassandra.model;

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

  public HColumn(K name, V value, long timestamp) {
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
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
}
