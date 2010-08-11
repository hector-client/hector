package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Column;

/**
 * Return type from get_range_slices for simple columns
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class OrderedRows<N,V> extends Rows<N,V> {

  private final List<Row<N,V>> rowsList;

  public OrderedRows(LinkedHashMap<String, List<Column>> thriftRet, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    super(thriftRet, nameExtractor, valueExtractor);
    rowsList = new ArrayList<Row<N,V>>(thriftRet.size());
    for (Map.Entry<String, List<Column>> entry: thriftRet.entrySet()) {
      rowsList.add(new Row<N,V>(entry.getKey(), entry.getValue(), nameExtractor, valueExtractor));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  public List<Row<N,V>> getList() {
    return Collections.unmodifiableList(rowsList);
  }
}
