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
public class RowsImpl<N, V> implements Rows<N, V> {

  protected final Map<String, Row<N, V>> rows;

  public RowsImpl(Map<String, List<Column>> thriftRet, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(thriftRet, nameSerializer, valueSerializer);
    rows = new HashMap<String, Row<N, V>>(thriftRet.size());
    for (Map.Entry<String, List<Column>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new RowImpl<N, V>(entry.getKey(), entry.getValue(), nameSerializer,
          valueSerializer));
    }
  }

  @Override
  public Row<N, V> getByKey(String key) {
    return rows.get(key);
  }

  @Override
  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<Row<N, V>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "Rows(" + rows + ")";
  }
}
