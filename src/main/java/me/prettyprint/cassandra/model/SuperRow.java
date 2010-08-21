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
      Serializer<SN> sNameExtractor, Serializer<N> nameExtractor, Serializer<V> valueExtractor) {
    Assert.noneNull(rowKey, thriftSuperColumns, nameExtractor, valueExtractor);
    this.rowKey = rowKey;
    slice = new SuperSlice<SN, N, V>(thriftSuperColumns, sNameExtractor, nameExtractor,
        valueExtractor);
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
