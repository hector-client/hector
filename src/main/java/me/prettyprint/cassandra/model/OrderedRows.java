package me.prettyprint.cassandra.model;

import java.util.List;

// used by get_range_slices
public interface OrderedRows<K,N,V> extends Rows<K,N,V> {

  // returns the rows as an ordered list.
  List<Row<K,N,V>> asList();
}
