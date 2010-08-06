package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;

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
public class Row<N,V> {

  private final String rowKey;
  private final ColumnSlice<N,V> columnSlice;

  /*package*/ Row(String rowKey, List<Column> columns, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    Assert.noneNull(rowKey, columns, nameExtractor, valueExtractor);
    this.rowKey = rowKey;
    columnSlice = new ColumnSlice<N,V>(columns, nameExtractor, valueExtractor);
  }

  public String getKey() {
    return rowKey;
  }

  public ColumnSlice<N,V> getColumnSlice() {
    return columnSlice;
  }

  @Override
  public String toString() {
    return "Row(" + rowKey + "," + columnSlice + ")";
  }
}
