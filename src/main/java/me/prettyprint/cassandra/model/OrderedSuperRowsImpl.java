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
public final class OrderedSuperRowsImpl<SN,N,V> extends SuperRowsImpl<SN,N,V> implements OrderedSuperRows<SN, N, V> {

  private final List<SuperRow<SN,N,V>> rowsList;

  public OrderedSuperRowsImpl(LinkedHashMap<String, List<SuperColumn>> thriftRet,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, sNameSerializer, nameSerializer, valueSerializer);
    rowsList = new ArrayList<SuperRow<SN,N,V>>(thriftRet.size());
    for (Map.Entry<String, List<SuperColumn>> entry: thriftRet.entrySet()) {
      rowsList.add(new SuperRowImpl<SN,N,V>(entry.getKey(), entry.getValue(), sNameSerializer,
          nameSerializer, valueSerializer));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  @Override
  public List<SuperRow<SN,N,V>> getList() {
    return Collections.unmodifiableList(rowsList);
  }
}
