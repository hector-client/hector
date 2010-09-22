package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;

import org.apache.cassandra.thrift.Column;

/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public class RowsImpl<K, N, V> implements Rows<K, N, V> {

  protected final Map<K, Row<K, N, V>> rows;

  public RowsImpl(Map<K, List<Column>> thriftRet, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(thriftRet, nameSerializer, valueSerializer);
    rows = new HashMap<K, Row<K, N, V>>(thriftRet.size());
    for (Map.Entry<K, List<Column>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new RowImpl<K, N, V>(entry.getKey(), entry.getValue(), nameSerializer,
          valueSerializer));
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

  @Override
  public String toString() {
    return "Rows(" + rows + ")";
  }
}
