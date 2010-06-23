package me.prettyprint.cassandra.model;

import java.util.List;

// used by get_range_slices
public interface OrderedRows<R, K> extends Rows<R, K> {

  // returns the rows as an ordered list.
  List<Row<R, K>> asList();
}
