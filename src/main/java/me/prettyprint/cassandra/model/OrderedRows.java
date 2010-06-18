package me.prettyprint.cassandra.model;

import java.util.List;

// used by get_range_slices
public interface OrderedRows extends Rows {

  // returns the rows as an ordered list.
  List<Row> asList();
}
