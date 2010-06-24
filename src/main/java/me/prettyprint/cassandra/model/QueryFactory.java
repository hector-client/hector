package me.prettyprint.cassandra.model;

public class QueryFactory {

  public static <N,V> ColumnQuery<N,V> createColumnQuery(KeyspaceOperator ko, 
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new ColumnQuery<N,V>(ko, nameExtractor, valueExtractor);
  }

  public static <SN,N,V> SuperColumnQuery<SN,N,V> createSuperColumnQuery(KeyspaceOperator ko) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 
   * @param <K> Row key type
   * @param keyspaceOperator
   * @return
   */
  public static <K,N,V> MultigetSliceQuery<K,N,V> createMultigetSliceQuery(KeyspaceOperator keyspaceOperator) {
    // TODO Auto-generated method stub
    return null;
  }
}
