package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

/**
 * Hector's version of cassandra SlicePredicate
 *
 * @author Ran Tavory
 *
 */
/*package*/final class HSlicePredicate<N> {

  /** Use column names or start/finish? */
  protected boolean useColumnNames;
  protected Collection<N> columnNames;
  protected N start;
  protected N finish;
  protected boolean reversed;
  protected int count;
  protected final Serializer<N> columnNameSerializer;

  public HSlicePredicate(Serializer<N> columnNameSerializer) {
    Assert.notNull(columnNameSerializer, "columnNameSerializer can't be null");
    this.columnNameSerializer = columnNameSerializer;
  }

  /**
   * Sets the column names to be retrieved by this query
   *
   * @param columns
   *          a list of column names
   */
  public HSlicePredicate<N> setColumnNames(N... columnNames) {
    this.columnNames = Arrays.asList(columnNames);
    useColumnNames = true;
    return this;
  }

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   *
   * @param start
   *          Start key
   * @param finish
   *          End key
   * @param reversed
   * @param count
   * @return
   */
  public HSlicePredicate<N> setRange(N start, N finish, boolean reversed, int count) {
    Assert.noneNull(start, finish);
    this.start = start;
    this.finish = finish;
    this.reversed = reversed;
    this.count = count;
    useColumnNames = false;
    return this;
  }

  public SlicePredicate toThrift() {
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
      SliceRange range = new SliceRange(columnNameSerializer.toBytes(start),
          columnNameSerializer.toBytes(finish), reversed, count);
      pred.setSlice_range(range);
    }
    return pred;
  }

  private List<byte[]> toThriftColumnNames(Collection<N> clms) {
    List<byte[]> ret = new ArrayList<byte[]>(clms.size());
    for (N name : clms) {
      ret.add(columnNameSerializer.toBytes(name));
    }
    return ret;
  }

  @Override
  public String toString() {
    return "HSlicePredicate("
        + (useColumnNames ? columnNames : "cStart:" + start + ",cFinish:" + finish) + ")";
  }
}
