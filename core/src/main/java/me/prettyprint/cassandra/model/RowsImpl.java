package me.prettyprint.cassandra.model;

import java.util.*;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;

import org.apache.cassandra.thrift.Column;

/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call), MultigetSubSliceQuery (multiget_slice), IndexedSlicesQuery (get_indexed_slices), RangeSlicesQuery (get_range_slices), RangeSubSlicesQuery (get_range_slices)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public class RowsImpl<K, N, V> implements Rows<K, N, V> {

  protected final Map<K, Row<K, N, V>> rows;
  protected final List<Row<K,N,V>> rowsList;

  public RowsImpl(Map<K, List<Column>> thriftRet, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(thriftRet, nameSerializer, valueSerializer);
    rows = new LinkedHashMap<K, Row<K, N, V>>(thriftRet.size(), 1);
    rowsList = new ArrayList<Row<K,N,V>>(thriftRet.size());
    for (Map.Entry<K, List<Column>> entry : thriftRet.entrySet()) {
      RowImpl<K, N, V> row = new RowImpl<K, N, V>(entry.getKey(), entry.getValue(), nameSerializer, valueSerializer);
      rows.put(entry.getKey(), row);
      rowsList.add(row);
    }
  }

  @Override
  public Row<K, N, V> getByKey(K key) {
    return rows.get(key);
  }

  @Override
  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<Row<K, N, V>> iterator() {
    return rows.values().iterator();
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

  @Override
  public String toString() {
    return "Rows(" + rows + ")";
  }
}
