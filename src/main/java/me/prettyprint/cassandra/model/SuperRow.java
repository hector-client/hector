package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.SuperColumn;

/**
 * A SuperRow is a touple consisting of a Key and a SuperSlice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 *          Column name type
 * @param <V>
 *          Column value type
 *
 */
public final class SuperRow<SN, N, V> {

  private final String rowKey;
  private final SuperSlice<SN, N, V> slice;

  /*package*/SuperRow(String rowKey, List<SuperColumn> thriftSuperColumns,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    Assert.noneNull(rowKey, thriftSuperColumns, nameSerializer, valueSerializer);
    this.rowKey = rowKey;
    slice = new SuperSlice<SN, N, V>(thriftSuperColumns, sNameSerializer, nameSerializer,
        valueSerializer);
  }

  public String getKey() {
    return rowKey;
  }

  public SuperSlice<SN, N, V> getSuperSlice() {
    return slice;
  }

  @Override
  public String toString() {
    return "SuperRow(" + rowKey + "," + slice + ")";
  }
}
