package me.prettyprint.cassandra.model;

/**
 * Hector Column definition.
 *
 * TODO(ran): This is still experimental; not production ready; work in progress
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class HColumn {

  private byte[] name;
  private byte[] value;
  private long timestamp;

  public HColumn(byte[] name, byte[] value, long timestamp) {
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
  }

  HColumn setName(byte[] name) {
    this.name = name;
    return this;
  }

  HColumn setValue(byte[] value) {
    this.value = value;
    return this;
  }

  HColumn setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  byte[] getName() {
    return name;
  }

  byte[] getValue() {
    return value;
  }

  long getTimestamp() {
    return timestamp;
  }
}
