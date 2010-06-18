package me.prettyprint.cassandra.model;

public interface Value {

  byte[] raw();
  String asString();
  long asLong();
  int asInt();
  //... etc, add more converters
}
