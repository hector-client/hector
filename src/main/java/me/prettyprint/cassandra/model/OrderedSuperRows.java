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
public final class OrderedSuperRows<SN,N,V> extends SuperRows<SN,N,V> {

  private final List<SuperRow<SN,N,V>> rowsList;

  public OrderedSuperRows(LinkedHashMap<String, List<SuperColumn>> thriftRet,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(thriftRet, sNameSerializer, nameSerializer, valueSerializer);
    rowsList = new ArrayList<SuperRow<SN,N,V>>(thriftRet.size());
    for (Map.Entry<String, List<SuperColumn>> entry: thriftRet.entrySet()) {
      rowsList.add(new SuperRow<SN,N,V>(entry.getKey(), entry.getValue(), sNameSerializer,
          nameSerializer, valueSerializer));
    }
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  public List<SuperRow<SN,N,V>> getList() {
    return Collections.unmodifiableList(rowsList);
  }
}
