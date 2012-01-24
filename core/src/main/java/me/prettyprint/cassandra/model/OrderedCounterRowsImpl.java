package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.OrderedCounterRows;
import org.apache.cassandra.thrift.CounterColumn;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Return type from get_range_slices for simple columns
 * @author Ran Tavory
 * @author zznate
 *
 * @param <N>
 */
public final class OrderedCounterRowsImpl<K,N> extends CounterRowsImpl<K,N> implements OrderedCounterRows<K, N> {

  private final List<CounterRow<K,N>> rowsList;

  public OrderedCounterRowsImpl(LinkedHashMap<K, List<CounterColumn>> thriftRet, Serializer<N> nameSerializer) {
    super(thriftRet, nameSerializer);
    rowsList = new ArrayList<CounterRow<K,N>>(rows.values());
  }

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  @Override
  public List<CounterRow<K,N>> getList() {
    return rowsList;
  }

  /**
   * Returns the last element in this row result. Helpful for passing along for paging situations.
   * @return
   */
  @Override
  public CounterRow<K, N> peekLast() {
    return rowsList != null && rowsList.size() > 0 ? rowsList.get(rowsList.size()-1) : null;
  }
}
