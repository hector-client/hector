package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.SubColumnQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;

public class AbstractSubColumnQuery<SN, N, V> implements SubColumnQuery<SN, N, V>{

  protected final SubSliceQuery<SN,N,V> subSliceQuery;

  public AbstractSubColumnQuery(KeyspaceOperator keyspaceOperator,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    subSliceQuery = HFactory.createSubSliceQuery(keyspaceOperator, sNameSerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public SubColumnQuery<SN, N, V> setKey(String key) {
    subSliceQuery.setKey(key);
    return this;
  }

  @Override
  public SubColumnQuery<SN, N, V> setSuperColumn(SN superName) {
    subSliceQuery.setSuperColumn(superName);
    return this;
  }

  @Override
  @SuppressWarnings("unchecked")
  public SubColumnQuery<SN, N, V> setColumn(N columnName) {
    subSliceQuery.setColumnNames(columnName);
    return this;
  }

  @Override
  public String toString() {
    return "SubColumnQuery(" + subSliceQuery + ")";
  }

  @Override
  public SubColumnQuery<SN, N, V> setColumnFamily(String cf) {
    subSliceQuery.setColumnFamily(cf);
    return this;
  }

  @Override
  public Result<HColumn<N, V>> execute() {
    Assert.isTrue(subSliceQuery.getColumnNames().size() == 1,
        "There should be exactly one column name set. Call setColumn");
    Result<ColumnSlice<N, V>> r = subSliceQuery.execute();
    ColumnSlice<N, V> slice = r.get();
    List<HColumn<N,V>> columns = slice.getColumns();
    HColumn<N, V> column = columns.size() == 0 ? null : columns.get(0);
    return new Result<HColumn<N,V>>(
        new ExecutionResult<HColumn<N,V>>(column, r.getExecutionTimeMicro(), r.getHostUsed()), this);
  }
}