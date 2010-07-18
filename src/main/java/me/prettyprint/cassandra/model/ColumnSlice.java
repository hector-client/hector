package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;

/**
 * A ColumnSlice represents a set of columns as returned by calls such as get_slice
 */
public class ColumnSlice<N,V> {

  private final Map<N,HColumn<N,V>> columnsMap;
  private final List<HColumn<N,V>> columnsList;

  /*package*/ ColumnSlice(List<Column> tColumns, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    Assert.noneNull(tColumns, nameExtractor, valueExtractor);
    columnsMap = new HashMap<N,HColumn<N,V>>(tColumns.size());
    columnsList = new ArrayList<HColumn<N,V>>(tColumns.size());
    for (Column c: tColumns) {
      HColumn<N, V> column = new HColumn<N,V>(c, nameExtractor, valueExtractor);
      columnsMap.put(column.getName(), column);
      columnsList.add(column);
    }
  }

  public List<HColumn<N,V>> getColumns() {
    return columnsList;
  }

  public HColumn<N,V> getColumnByName(N columnName) {
    return columnsMap.get(columnName);
  }

  @Override
  public String toString() {
    return "ColumnSlice(" + columnsList + ")";
  }
}
