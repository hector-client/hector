package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedSuperRows;
import me.prettyprint.hector.api.beans.SuperRow;

import org.apache.cassandra.thrift.SuperColumn;

/**
 * Return type from get_range_slices for super columns
 * @author Ran Tavory
 *
 */
public final class OrderedSuperRowsImpl<K,SN,N,V> extends SuperRowsImpl<K,SN,N,V>
    implements OrderedSuperRows<K, SN, N, V>{

  private final List<SuperRow<K,SN,N,V>> rowsList;

  public OrderedSuperRowsImpl(LinkedHashMap<K, List<SuperColumn>> thriftRet, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
    rowsList = new ArrayList<SuperRow<K,SN,N,V>>(thriftRet.size());
    for (Map.Entry<K, List<SuperColumn>> entry: thriftRet.entrySet()) {
      rowsList.add(new SuperRowImpl<K,SN,N,V>(entry.getKey(), entry.getValue(), sNameSerializer,
          nameSerializer, valueSerializer));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  @Override
  public List<SuperRow<K,SN,N,V>> getList() {
    return Collections.unmodifiableList(rowsList);
  }
}
