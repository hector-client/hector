package me.prettyprint.cassandra.model;

import java.util.Arrays;
import java.util.Collection;

import me.prettyprint.cassandra.utils.Assert;


/**
 * base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
 * @author Ran Tavory
 *
 * @param <N>
 * @param <T>
 */
/*package*/ @SuppressWarnings("unchecked")
abstract class AbstractSliceQuery<N,V,T> extends AbstractQuery<N,V,T> implements Query<T> {

  protected Collection<N> columnNames;
  protected N start;
  protected N finish;
  protected boolean reversed;
  protected int count;

  /** Use column names or start/finish? */
  protected boolean useColumnNames;

  /*package*/ AbstractSliceQuery(KeyspaceOperator ko, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  public AbstractSliceQuery<N,V,T> setColumnNames(N... columnNames) {
    this.columnNames = Arrays.asList(columnNames);
    useColumnNames = true;
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
  public AbstractSliceQuery<N,V,T> setPredicate(N start, N finish, boolean reversed, int count) {
    Assert.noneNull(start, finish);
    this.start = start;
    this.finish = finish;
    this.reversed = reversed;
    this.count = count;
    useColumnNames = false;
    return this;
  }
}
