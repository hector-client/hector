package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

/**
 * Hector's version of cassandra SlicePredicate
 *
 * @author Ran Tavory
 *
 */
public final class HSlicePredicate<N> {

  protected Collection<N> columnNames;
  protected N start;
  protected N finish;
  protected boolean reversed;
  protected int count;
  /** Is count already set? */
  private boolean countSet = false;
  protected final Serializer<N> columnNameSerializer;
  protected enum PredicateType {Unknown, ColumnNames, Range};
  protected PredicateType predicateType = PredicateType.Unknown;

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
    predicateType = PredicateType.ColumnNames;
    return this;
  }

  /**
   * Allows the use of returning just the keys. This avoids de-serialization of row data
   * and can be a huge optimization in some use cases
   *
   */
  public HSlicePredicate<N> setKeysOnlyPredicate() {
    this.columnNames = new ArrayList<N>();
    predicateType = PredicateType.ColumnNames;
    return this;
  }

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   * Either start and or finish can be null which will toggle the underlying predicate to
   * use an empty byte[]
   * @param start
   *          Start key
   * @param finish
   *          End key
   * @param reversed
   * @param count
   * @return
   */
  public HSlicePredicate<N> setRange(N start, N finish, boolean reversed, int count) {
    this.start = start;
    this.finish = finish;
    this.reversed = reversed;
    this.count = count;
    countSet = true;
    predicateType = PredicateType.Range;
    return this;
  }

  public Collection<N> getColumnNames() {
    return Collections.unmodifiableCollection(columnNames);
  }

  /**
   * Will throw a runtime exception if neither columnsNames nor count were set.
   * @return
   */
  public SlicePredicate toThrift() {
    SlicePredicate pred = new SlicePredicate();

    switch (predicateType) {
    case ColumnNames:
      if (columnNames == null || columnNames.isEmpty()) {
        return null;
      }
      pred.setColumn_names(toThriftColumnNames(columnNames));
      break;
    case Range:
      Assert.isTrue(countSet, "Count was not set, neither were column-names set, can't execute");
      SliceRange range = new SliceRange(findBytes(start), findBytes(finish), reversed, count);
      pred.setSlice_range(range);
      break;
    case Unknown:
    default:
      throw new HectorException(
          "Neither column names nor range were set, this is an invalid slice predicate");
    }
    return pred;
  }

  private byte[] findBytes(N val) {
    byte[] valBytes;
    if (val == null) {
      valBytes =  new byte[]{};
    } else {
      valBytes = columnNameSerializer.toBytes(val);
    }
    return valBytes;
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
        + (predicateType == PredicateType.ColumnNames ? columnNames :
          "cStart:" + start + ",cFinish:" + finish) + ")";
  }
}
