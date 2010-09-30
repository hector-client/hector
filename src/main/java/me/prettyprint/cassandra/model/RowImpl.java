package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Row;

import org.apache.cassandra.thrift.Column;

/**
 * A Row is a touple consisting of a Key and a Column Slice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N> Column name type
 * @param <V> Column value type
 *
 */
public final class RowImpl<K,N,V> implements Row<K, N, V>{

  private final K rowKey;
  private final ColumnSlice<N,V> columnSlice;

  /*package*/ RowImpl(K k, List<Column> columns, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(k, columns, nameSerializer, valueSerializer);
    this.rowKey = k;
    columnSlice = new ColumnSliceImpl<N,V>(columns, nameSerializer, valueSerializer);
  }

  @Override
  public K getKey() {
    return rowKey;
  }

  @Override
  public ColumnSlice<N, V> getColumnSlice() {
    return columnSlice;
  }

  @Override
  public String toString() {
    return "Row(" + rowKey + "," + columnSlice + ")";
  }
}
