package me.prettyprint.cassandra.model;

/**
 * Hector Column definition.
 *
 * TODO(ran): This is still experimental; not production ready.
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class Column {

  private byte[] name;
  private byte[] value;
  private long timestamp;

  public Column(byte[] name, byte[] value, long timestamp) {
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
  }

  Column setName(byte[] name) {
    this.name = name;
    return this;
  }

  Column setValue(byte[] value) {
    this.value = value;
    return this;
  }

  Column setTimestamp(long timestamp) {
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
