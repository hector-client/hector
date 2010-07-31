package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;


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
  public AbstractSliceQuery<N,V,T> setRange(N start, N finish, boolean reversed, int count) {
    Assert.noneNull(start, finish);
    this.start = start;
    this.finish = finish;
    this.reversed = reversed;
    this.count = count;
    useColumnNames = false;
    return this;
  }

  /**
   *
   * @return the thrift representation of the predicate
   */
  /*package*/ SlicePredicate getPredicate() {
    SlicePredicate pred = new SlicePredicate();
    if (useColumnNames) {
      if (columnNames == null || columnNames.isEmpty()) {
        return null;
      }
      pred.setColumn_names(toThriftColumnNames(columnNames));
    } else {
      if (start == null || finish == null) {
        return null;
      }
      SliceRange range = new SliceRange(columnNameExtractor.toBytes(start), columnNameExtractor.toBytes(finish),
          reversed, count);
      pred.setSlice_range(range);
    }
    return pred;
  }

  private List<byte[]> toThriftColumnNames(Collection<N> clms) {
    List<byte[]> ret = new ArrayList<byte[]>(clms.size());
    for (N name: clms) {
      ret.add(columnNameExtractor.toBytes(name));
    }
    return ret;
  }

  protected String toStringInternal() {
    return "" + (useColumnNames ? columnNames : "start:" + start + ",finish:" + finish);
  }
}
