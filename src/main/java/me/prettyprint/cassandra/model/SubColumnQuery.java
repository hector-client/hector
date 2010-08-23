package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.utils.Assert;

/**
 * Used to get the value of a subcolumn within a super column
 * @author Ran Tavory
 *
 * @param <SN> supercolumn name type
 * @param <N> column name type
 * @param <V> column value type
 */
public final class SubColumnQuery<SN,N,V> implements Query<HColumn<N,V>> {

  private final SubSliceQuery<SN,N,V> subSliceQuery;

  /*package*/ public SubColumnQuery(KeyspaceOperator keyspaceOperator,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    subSliceQuery = HFactory.createSubSliceQuery(keyspaceOperator, sNameSerializer, nameSerializer,
        valueSerializer);
  }

  public SubColumnQuery<SN,N,V> setKey(String key) {
    subSliceQuery.setKey(key);
    return this;
  }

  public SubColumnQuery<SN,N,V> setSuperColumn(SN superName) {
    subSliceQuery.setSuperColumn(superName);
    return this;
  }

  @SuppressWarnings("unchecked")
  public SubColumnQuery<SN,N,V> setColumn(N columnName) {
    subSliceQuery.setColumnNames(columnName);
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

  @Override
  public String toString() {
    return "SubColumnQuery(" + subSliceQuery + ")";
  }

  public SubColumnQuery<SN,N,V> setColumnFamily(String cf) {
    subSliceQuery.setColumnFamily(cf);
    return this;
  }
}
