package me.prettyprint.cassandra.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Column;

/**
 * Return type from get_range_slices for simple columns
 * @author Ran Tavory
 * @author zznate
 *
 * @param <N>
 * @param <V>
 */
public final class OrderedRows<K,N,V> extends Rows<K,N,V> {

  private final ArrayDeque<Row<K,N,V>> rowsList;

  public OrderedRows(LinkedHashMap<K, List<Column>> thriftRet, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, nameSerializer, valueSerializer);
    rowsList = new ArrayDeque<Row<K,N,V>>(thriftRet.size());
    for (Map.Entry<K, List<Column>> entry: thriftRet.entrySet()) {
      rowsList.add(new Row<K,N,V>(entry.getKey(), entry.getValue(), nameSerializer, valueSerializer));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  public List<Row<K,N,V>> getList() {
    return Collections.unmodifiableList(new ArrayList<Row<K, N, V>>(rowsList));
  }
  
  /**
   * Returns the last element in this row result. Helpful for passing along for paging situations.
   * @return
   */
  public Row<K, N, V> peekLast() {
    return rowsList != null && rowsList.size() > 0 ? rowsList.peekLast() : null;
  }
}
