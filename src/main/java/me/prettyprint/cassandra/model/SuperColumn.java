package me.prettyprint.cassandra.model;

import java.util.List;

// get
public interface SuperColumn extends List<Column> {

  Value getName();
  
}
