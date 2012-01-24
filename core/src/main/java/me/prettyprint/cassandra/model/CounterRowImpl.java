package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterSlice;

import org.apache.cassandra.thrift.CounterColumn;

/**
 * A Row is a touple consisting of a Key and a Column Slice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N> Column name type
 *
 */
public final class CounterRowImpl<K,N> implements CounterRow<K, N> {

  private final K rowKey;
  private final CounterSlice<N> columnSlice;

  /*package*/
  CounterRowImpl(K k, List<CounterColumn> columns, Serializer<N> nameSerializer) {
    Assert.noneNull(k, columns, nameSerializer);
    this.rowKey = k;
    columnSlice = new CounterSliceImpl<N>(columns, nameSerializer);
  }

  @Override
  public K getKey() {
    return rowKey;
  }

  @Override
  public CounterSlice<N> getColumnSlice() {
    return columnSlice;
  }

  @Override
  public String toString() {
    return "Row(" + rowKey + "," + columnSlice + ")";
  }
}
