package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.SuperRow;
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
public class SuperRowsImpl<K, SN, N, V> implements SuperRows<K, SN, N, V> {

  private final Map<K, SuperRow<K, SN, N, V>> rows;

  Serializer<K> keySerializer;

  public SuperRowsImpl(Map<K, List<SuperColumn>> thriftRet, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    Assert.noneNull(thriftRet, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
    this.keySerializer = keySerializer;
    rows = new HashMap<K, SuperRow<K, SN, N, V>>(thriftRet.size());
    for (Map.Entry<K, List<SuperColumn>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new SuperRowImpl<K, SN, N, V>(entry.getKey(), entry.getValue(),
          sNameSerializer, nameSerializer, valueSerializer));
    }
  }

  @Override
  public SuperRow<K, SN, N, V> getByKey(K key) {
    return rows.get(key);
  }

  @Override
  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<SuperRow<K, SN, N, V>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "SuperRows(" + rows + ")";
  }
}
