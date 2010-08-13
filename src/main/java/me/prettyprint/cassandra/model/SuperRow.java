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
public final class SuperRow<K, SN, N, V> {

  private final K rowKey;
  private final SuperSlice<SN, N, V> slice;

  /*package*/SuperRow(K bs, List<SuperColumn> thriftSuperColumns,
      Extractor<SN> sNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    Assert.noneNull(bs, thriftSuperColumns, nameExtractor, valueExtractor);
    this.rowKey = bs;
    slice = new SuperSlice<SN, N, V>(thriftSuperColumns, sNameExtractor, nameExtractor,
        valueExtractor);
  }

  public K getKey() {
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
