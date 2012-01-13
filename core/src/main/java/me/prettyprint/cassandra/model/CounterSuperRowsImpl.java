package me.prettyprint.cassandra.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSuperRow;
import me.prettyprint.hector.api.beans.CounterSuperRows;

import org.apache.cassandra.thrift.CounterSuperColumn;

/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns)
 *
 * @author Ran Tavory
 *
 * @param <N>
 */
public class CounterSuperRowsImpl<K, SN, N> implements CounterSuperRows<K, SN, N> {

  protected final Map<K, CounterSuperRow<K, SN, N>> rows;

  Serializer<K> keySerializer;

  public CounterSuperRowsImpl(Map<K, List<CounterSuperColumn>> thriftRet, Serializer<K> keySerializer,
                              Serializer<SN> sNameSerializer, Serializer<N> nameSerializer) {
    Assert.noneNull(thriftRet, keySerializer, sNameSerializer, nameSerializer);
    this.keySerializer = keySerializer;
    rows = new LinkedHashMap<K, CounterSuperRow<K, SN, N>>(thriftRet.size());
    for (Map.Entry<K, List<CounterSuperColumn>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new CounterSuperRowImpl<K, SN, N>(entry.getKey(), entry.getValue(),
          sNameSerializer, nameSerializer));
    }
  }

  @Override
  public CounterSuperRow<K, SN, N> getByKey(K key) {
    return rows.get(key);
  }

  @Override
  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<CounterSuperRow<K, SN, N>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "SuperRows(" + rows + ")";
  }
}
