package me.prettyprint.cassandra.model;

import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Column;

// used by get_range_slices
public class OrderedRows<N,V> extends Rows<N,V> {

  public OrderedRows(Map<String, List<Column>> thriftRet, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    super(thriftRet, nameExtractor, valueExtractor);
  }

  // returns the rows as an ordered list.
  List<Row<N,V>> getList() {
    //TODO
    return null;
  }
}
