package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.beans.SuperRows;

import org.apache.cassandra.thrift.SuperColumn;

/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public class SuperRowsImpl<SN, N, V> implements SuperRows<SN, N, V> {

  private final Map<String, SuperRow<SN, N, V>> rows;

  public SuperRowsImpl(Map<String, List<SuperColumn>> thriftSuperColumns, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    Assert.noneNull(thriftSuperColumns, sNameSerializer, nameSerializer, valueSerializer);
    rows = new HashMap<String, SuperRow<SN, N, V>>(thriftSuperColumns.size());
    for (Map.Entry<String, List<SuperColumn>> entry : thriftSuperColumns.entrySet()) {
      rows.put(entry.getKey(), new SuperRow<SN, N, V>(entry.getKey(), entry.getValue(),
          sNameSerializer, nameSerializer, valueSerializer));
    }
  }

  @Override
  public SuperRow<SN, N, V> getByKey(String key) {
    return rows.get(key);
  }

  @Override
  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<SuperRow<SN, N, V>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "SuperRows(" + rows + ")";
  }
}
