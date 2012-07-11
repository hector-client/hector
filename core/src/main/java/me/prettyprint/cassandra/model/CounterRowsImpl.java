package me.prettyprint.cassandra.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;

import org.apache.cassandra.thrift.CounterColumn;

/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 *
 * @param <N>
 */
public class CounterRowsImpl<K, N> implements CounterRows<K, N> {

  protected final Map<K, CounterRow<K, N>> rows;

  public CounterRowsImpl(Map<K, List<CounterColumn>> thriftRet, Serializer<N> nameSerializer) {
    Assert.noneNull(thriftRet, nameSerializer);
    rows = new LinkedHashMap<K, CounterRow<K, N>>(thriftRet.size());
    for (Map.Entry<K, List<CounterColumn>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new CounterRowImpl<K, N>(entry.getKey(), entry.getValue(), nameSerializer));
    }
  }

  @Override
  public CounterRow<K, N> getByKey(K key) {
    return rows.get(key);
  }

  @Override
  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<CounterRow<K, N>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "Rows(" + rows + ")";
  }
}
