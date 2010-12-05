package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.beans.SuperSlice;

import org.apache.cassandra.thrift.SuperColumn;

/**
 * Represents a return of the get_slice query for supercolumns
 *
 * @param <SN> Super column name type
 * @param <N> Column name type
 * @param <V> Column value type
 */
public final class SuperSliceImpl<SN,N,V> implements SuperSlice<SN, N, V> {

  private final Map<SN,HSuperColumn<SN,N,V>> columnsMap;

  private final List<HSuperColumn<SN,N,V>> columnsList;

  public SuperSliceImpl(List<SuperColumn> tSuperColumns, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    Assert.noneNull(tSuperColumns, sNameSerializer, nameSerializer, valueSerializer);
    columnsMap = new HashMap<SN,HSuperColumn<SN,N,V>>(tSuperColumns.size());
    columnsList = new ArrayList<HSuperColumn<SN,N,V>>(tSuperColumns.size());
    for (SuperColumn sc: tSuperColumns) {
      HSuperColumn<SN,N,V> column = new HSuperColumnImpl<SN,N,V>(sc, sNameSerializer, nameSerializer,
          valueSerializer);
      columnsMap.put(column.getName(), column);
      columnsList.add(column);
    }
  }

  /**
   * @return an unmodifiable list of supercolumns
   */
  @Override
  public List<HSuperColumn<SN,N,V>> getSuperColumns() {
    return Collections.unmodifiableList(columnsList);

  }

  @Override
  public HSuperColumn<SN, N, V> getColumnByName(SN columnName) {
    return columnsMap.get(columnName);
  }
  @Override
  public String toString() {
    return "SuperSlice(" + columnsList.toString() + ")";
  }
}
