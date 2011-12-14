package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSuperRow;
import me.prettyprint.hector.api.beans.OrderedCounterSuperRows;

import org.apache.cassandra.thrift.CounterSuperColumn;

/**
 * Return type from get_range_slices for super columns
 * 
 *
 */
public final class OrderedCounterSuperRowsImpl<K,SN,N> extends CounterSuperRowsImpl<K,SN,N>
    implements OrderedCounterSuperRows<K, SN, N> {

  private final List<CounterSuperRow<K,SN,N>> rowsList;

  public OrderedCounterSuperRowsImpl(LinkedHashMap<K, List<CounterSuperColumn>> thriftRet, Serializer<K> keySerializer,
                                     Serializer<SN> sNameSerializer, Serializer<N> nameSerializer) {
    super(thriftRet, keySerializer, sNameSerializer, nameSerializer);
    rowsList = new ArrayList<CounterSuperRow<K,SN,N>>(rows.values());
  }

  /**
   * Preserves rows order
   * @return a list of Rows
   */
  @Override
  public List<CounterSuperRow<K,SN,N>> getList() {
    return rowsList;
  }
}
