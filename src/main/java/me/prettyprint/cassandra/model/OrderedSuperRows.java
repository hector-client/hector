package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.SuperColumn;

/**
 * Return type from get_range_slices for super columns
 * @author Ran Tavory
 *
 */
public final class OrderedSuperRows<K,SN,N,V> extends SuperRows<K,SN,N,V> {

  private final List<SuperRow<K,SN,N,V>> rowsList;

  public OrderedSuperRows(LinkedHashMap<K, List<SuperColumn>> thriftRet, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
    rowsList = new ArrayList<SuperRow<K,SN,N,V>>(thriftRet.size());
    for (Map.Entry<K, List<SuperColumn>> entry: thriftRet.entrySet()) {
      rowsList.add(new SuperRow<K,SN,N,V>(entry.getKey(), entry.getValue(), sNameSerializer,
          nameSerializer, valueSerializer));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  public List<SuperRow<K,SN,N,V>> getList() {
    return Collections.unmodifiableList(rowsList);
  }
}
