package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSuperSlice;
import me.prettyprint.hector.api.beans.HCounterSuperColumn;
import org.apache.cassandra.thrift.CounterSuperColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a return of the get_slice query for supercolumns
 *
 * @param <SN> Super column name type
 * @param <N> Column name type
 */
public final class CounterSuperSliceImpl<SN,N> implements CounterSuperSlice<SN, N> {

  private final Map<SN,HCounterSuperColumn<SN,N>> columnsMap;

  private final List<HCounterSuperColumn<SN,N>> columnsList;

  public CounterSuperSliceImpl(List<CounterSuperColumn> tSuperColumns, Serializer<SN> sNameSerializer,
                               Serializer<N> nameSerializer) {
    Assert.noneNull(tSuperColumns, sNameSerializer, nameSerializer);
    columnsMap = new HashMap<SN,HCounterSuperColumn<SN,N>>(tSuperColumns.size());
    columnsList = new ArrayList<HCounterSuperColumn<SN,N>>(tSuperColumns.size());
    for (CounterSuperColumn sc: tSuperColumns) {
      HCounterSuperColumn<SN,N> column = new HCounterSuperColumnImpl<SN,N>(sc, sNameSerializer, nameSerializer);
      columnsMap.put(column.getName(), column);
      columnsList.add(column);
    }
  }

  /**
   * @return an unmodifiable list of supercolumns
   */
  @Override
  public List<HCounterSuperColumn<SN,N>> getSuperColumns() {
    return columnsList;

  }

  @Override
  public HCounterSuperColumn<SN, N> getColumnByName(SN columnName) {
    return columnsMap.get(columnName);
  }
  @Override
  public String toString() {
    return "SuperSlice(" + columnsList.toString() + ")";
  }
}
