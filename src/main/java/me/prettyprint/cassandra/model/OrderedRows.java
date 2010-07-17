package me.prettyprint.cassandra.model;

import java.util.List;

// used by get_range_slices
public class OrderedRows<N,V> extends Rows<N,V> {

  // returns the rows as an ordered list.
  List<Row<N,V>> asList() {
    //TODO
    return null;
  }
}
