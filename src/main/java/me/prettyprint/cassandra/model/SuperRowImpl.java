package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.SuperRow;
import me.prettyprint.hector.api.beans.SuperSlice;

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
public final class SuperRowImpl<K, SN, N, V> implements SuperRow<K, SN, N, V>{

  private final K rowKey;
  private final SuperSlice<SN, N, V> slice;

  /*package*/SuperRowImpl(K bs, List<SuperColumn> thriftSuperColumns,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    Assert.noneNull(bs, thriftSuperColumns, nameSerializer, valueSerializer);
    this.rowKey = bs;
    slice = new SuperSliceImpl<SN, N, V>(thriftSuperColumns, sNameSerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public K getKey() {
    return rowKey;
  }

  @Override
  public SuperSlice<SN, N, V> getSuperSlice() {
    return slice;
  }

  @Override
  public String toString() {
    return "SuperRow(" + rowKey + "," + slice + ")";
  }
}
