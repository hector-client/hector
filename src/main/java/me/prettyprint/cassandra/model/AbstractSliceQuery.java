package me.prettyprint.cassandra.model;

import java.util.Collection;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.Query;

import org.apache.cassandra.thrift.SlicePredicate;


/**
 * Base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
 * @author Ran Tavory
 *
 * @param <N>
 * @param <T>
 */
public abstract class AbstractSliceQuery<K,N,V,T> extends AbstractQuery<K,N,V,T> implements Query<T> {

  protected final HSlicePredicate<N> slicePredicate;

  public AbstractSliceQuery(Keyspace k, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
    slicePredicate = new HSlicePredicate<N>(nameSerializer);
  }

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  public Query<T> setColumnNames(N... columnNames) {
    if ( columnNames != null && columnNames.length > 0) {
      slicePredicate.setColumnNames(columnNames);
    } else {
      slicePredicate.setKeysOnlyPredicate();
    }
    return this;
  }

  /**
   * Wraps the underlying call to {@link HSlicePredicate#setKeysOnlyPredicate()}
   * Use this for a substantial performance increase when you only need the keys returned
   */
  public AbstractSliceQuery<K,N,V,T> setReturnKeysOnly() {
    slicePredicate.setKeysOnlyPredicate();
    return this;
  }


  public Collection<N> getColumnNames() {
    return slicePredicate.getColumnNames();
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
  public Query<T> setRange(N start, N finish, boolean reversed, int count) {
    slicePredicate.setRange(start, finish, reversed, count);
    return this;
  }

  /**
   *
   * @return the thrift representation of the predicate
   */
  public SlicePredicate getPredicate() {
    return slicePredicate.toThrift();
  }

  protected String toStringInternal() {
    return slicePredicate.toString();
  }
}
