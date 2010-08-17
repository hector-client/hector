package me.prettyprint.cassandra.model;

import org.apache.cassandra.thrift.SlicePredicate;


/**
 * base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
 * @author Ran Tavory
 *
 * @param <N>
 * @param <T>
 */
/*package*/ abstract class AbstractSliceQuery<K,N,V,T> extends AbstractQuery<K,N,V,T> implements Query<T> {

  protected final HSlicePredicate<N> slicePredicate;

  /*package*/ AbstractSliceQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
    slicePredicate = new HSlicePredicate<N>(nameSerializer);
  }

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  public AbstractSliceQuery<K,N,V,T> setColumnNames(N... columnNames) {
    slicePredicate.setColumnNames(columnNames);
    return this;
  }

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   *
   * @param start Start key
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  public AbstractSliceQuery<K,N,V,T> setRange(N start, N finish, boolean reversed, int count) {
    slicePredicate.setRange(start, finish, reversed, count);
    return this;
  }

  /**
   *
   * @return the thrift representation of the predicate
   */
  /*package*/ SlicePredicate getPredicate() {
    return slicePredicate.toThrift();
  }

  protected String toStringInternal() {
    return slicePredicate.toString();
  }
}
