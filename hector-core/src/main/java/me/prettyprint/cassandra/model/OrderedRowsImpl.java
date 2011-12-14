package me.prettyprint.cassandra.model;

import java.util.*;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;

import org.apache.cassandra.thrift.Column;

/**
 * Return type from get_range_slices for simple columns
 * @author Ran Tavory
 * @author zznate
 *
 * @param <N>
 * @param <V>
 */
public class OrderedRowsImpl<K,N,V> extends RowsImpl<K,N,V> implements OrderedRows<K, N, V> {

  protected final List<Row<K,N,V>> rowsList;

  public OrderedRowsImpl(LinkedHashMap<K, List<Column>> thriftRet, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, nameSerializer, valueSerializer);
    rowsList = new ArrayList<Row<K,N,V>>(rows.values());
  }
  
  protected OrderedRowsImpl() {
    super();
    rowsList = new ArrayList<Row<K,N,V>>();
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  @Override
  public List<Row<K,N,V>> getList() {
    return rowsList;
  }

  /**
   * Returns the last element in this row result. Helpful for passing along for paging situations.
   * @return
   */
  @Override
  public Row<K, N, V> peekLast() {
    return rowsList != null && rowsList.size() > 0 ? rowsList.get(rowsList.size()-1) : null;
  }
}
