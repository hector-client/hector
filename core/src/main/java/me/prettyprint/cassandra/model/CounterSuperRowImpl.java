package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSuperRow;
import me.prettyprint.hector.api.beans.CounterSuperSlice;

import org.apache.cassandra.thrift.CounterSuperColumn;

/**
 * A SuperRow is a touple consisting of a Key and a SuperSlice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 *          Column name type
 *
 */
public final class CounterSuperRowImpl<K, SN, N> implements CounterSuperRow<K, SN, N> {

  private final K rowKey;
  private final CounterSuperSlice<SN, N> slice;

  /*package*/CounterSuperRowImpl(K bs, List<CounterSuperColumn> thriftSuperColumns,
                                 Serializer<SN> sNameSerializer, Serializer<N> nameSerializer) {
    Assert.noneNull(bs, thriftSuperColumns, nameSerializer);
    this.rowKey = bs;
    slice = new CounterSuperSliceImpl<SN, N>(thriftSuperColumns, sNameSerializer, nameSerializer);
  }

  @Override
  public K getKey() {
    return rowKey;
  }

  @Override
  public CounterSuperSlice<SN, N> getSuperSlice() {
    return slice;
  }

  @Override
  public String toString() {
    return "SuperRow(" + rowKey + "," + slice + ")";
  }
}
