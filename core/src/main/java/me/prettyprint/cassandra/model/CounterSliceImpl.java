package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HCounterColumn;

import org.apache.cassandra.thrift.CounterColumn;

public final class CounterSliceImpl<N> implements CounterSlice<N> {

  private final Map<N,HCounterColumn<N>> columnsMap;
  private final List<HCounterColumn<N>> columnsList;

  public CounterSliceImpl(List<CounterColumn> tColumns, Serializer<N> nameSerializer) {
     
    Assert.noneNull(tColumns, nameSerializer);
    columnsMap = new HashMap<N,HCounterColumn<N>>(tColumns.size());
    List<HCounterColumn<N>> list = new ArrayList<HCounterColumn<N>>(tColumns.size());
    for (CounterColumn c: tColumns) {
    	HCounterColumn<N> column = new HCounterColumnImpl<N>(c, nameSerializer);
      columnsMap.put(column.getName(), column);
      list.add(column);
    }
    columnsList = list;
  }

  /**
   *
   * @return an unmodifiable list of the columns
   */
  @Override
  public List<HCounterColumn<N>> getColumns() {
    return columnsList;
  }

  @Override
  public HCounterColumn<N> getColumnByName(N columnName) {
    return columnsMap.get(columnName);
  }

  @Override
  public String toString() {
    return String.format("ColumnSlice(%s)", columnsList.toString());
  }
}
