package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.beans.OrderedRows;

import org.apache.cassandra.thrift.Column;

/**
 * Return type from get_range_slices for simple columns
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class OrderedRowsImpl<N,V> extends RowsImpl<N,V> implements OrderedRows<N, V> {

  private final List<Row<N,V>> rowsList;

  public OrderedRowsImpl(LinkedHashMap<String, List<Column>> thriftRet, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, nameSerializer, valueSerializer);
    rowsList = new ArrayList<Row<N,V>>(thriftRet.size());
    for (Map.Entry<String, List<Column>> entry: thriftRet.entrySet()) {
      rowsList.add(new Row<N,V>(entry.getKey(), entry.getValue(), nameSerializer, valueSerializer));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  @Override
  public List<Row<N,V>> getList() {
    return Collections.unmodifiableList(rowsList);
  }
}
