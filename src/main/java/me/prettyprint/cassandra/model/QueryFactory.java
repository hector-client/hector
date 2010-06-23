package me.prettyprint.cassandra.model;

public class QueryFactory {

  public static ColumnQuery createColumnQuery(KeyspaceOperator ko) {
    return new ColumnQuery(ko);
  }

  public static SuperColumnQuery createSuperColumnQuery(KeyspaceOperator ko) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 
   * @param <R> Row key type
   * @param keyspaceOperator
   * @return
   */
  public static <R, K> MultigetSliceQuery<R, K> createMultigetSliceQuery(KeyspaceOperator keyspaceOperator) {
    // TODO Auto-generated method stub
    return null;
  }
}
