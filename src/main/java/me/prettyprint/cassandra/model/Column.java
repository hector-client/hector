package me.prettyprint.cassandra.model;

// get
public interface Column {

  Value getName();
  Value getValue();
  long getTimestamp();
}
