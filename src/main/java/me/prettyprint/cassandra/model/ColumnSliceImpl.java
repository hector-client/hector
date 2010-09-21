package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;

import org.apache.cassandra.thrift.Column;

public final class ColumnSliceImpl<N,V> implements ColumnSlice<N, V> {

  private final Map<N,HColumn<N,V>> columnsMap;
  private final List<HColumn<N,V>> columnsList;

  public ColumnSliceImpl(List<Column> tColumns, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    Assert.noneNull(tColumns, nameSerializer, valueSerializer);
    columnsMap = new HashMap<N,HColumn<N,V>>(tColumns.size());
    List<HColumn<N,V>> list = new ArrayList<HColumn<N,V>>(tColumns.size());
    for (Column c: tColumns) {
      HColumn<N, V> column = new HColumnImpl<N,V>(c, nameSerializer, valueSerializer);
      columnsMap.put(column.getName(), column);
      list.add(column);
    }
    columnsList = Collections.unmodifiableList(list);
  }

  /**
   *
   * @return an unmodifiable list of the columns
   */
  @Override
  public List<HColumn<N,V>> getColumns() {
    return columnsList;
  }

  @Override
  public HColumn<N, V> getColumnByName(N columnName) {
    return columnsMap.get(columnName);
  }

  @Override
  public String toString() {
    return "ColumnSlice(" + columnsList + ")";
  }
}
