package me.prettyprint.cassandra.model;

import java.util.*;

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

  protected final Map<K, SuperRow<K, SN, N, V>> rows;
  protected final List<SuperRow<K,SN,N,V>> rowsList;

  Serializer<K> keySerializer;

  public SuperRowsImpl(Map<K, List<SuperColumn>> thriftRet, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    Assert.noneNull(thriftRet, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
    this.keySerializer = keySerializer;
    rows = new LinkedHashMap<K, SuperRow<K, SN, N, V>>(thriftRet.size(), 1);
    rowsList = new ArrayList<SuperRow<K,SN,N,V>>(thriftRet.size());
    for (Map.Entry<K, List<SuperColumn>> entry : thriftRet.entrySet()) {
      SuperRowImpl<K, SN, N, V> row = new SuperRowImpl<K, SN, N, V>(entry.getKey(), entry.getValue(),
              sNameSerializer, nameSerializer, valueSerializer);
      rows.put(entry.getKey(), row);
      rowsList.add(row);
    }
  }

  /**
   * Preserves rows order
   * @return a list of Rows
   */
  @Override
  public List<SuperRow<K,SN,N,V>> getList() {
    return rowsList;
  }

  @Override
  public SuperRow<K, SN, N, V> peekLast() {
    return rowsList != null && rowsList.size() > 0 ? rowsList.get(rowsList.size()-1) : null;
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
