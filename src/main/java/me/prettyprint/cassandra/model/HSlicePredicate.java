package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
   * Allows the use of returning just the keys. This avoids de-serialization of row data 
   * and can be a huge optimization in some use cases
   * 
   */
  public HSlicePredicate<N> setKeysOnlyPredicate() {
    this.columnNames = new ArrayList<N>();
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

  public Collection<N> getColumnNames() {
    return Collections.unmodifiableCollection(columnNames);
  }

  public SlicePredicate toThrift() {
    SlicePredicate pred = new SlicePredicate();
    if (useColumnNames) {
      if (columnNames == null) {
        return null;
      }
      pred.setColumn_names(toThriftColumnNames(columnNames));
    } else {
      pred.setSlice_range(new SliceRange(findBytes(start),findBytes(finish),
          reversed, count));
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
        + (useColumnNames ? columnNames : "cStart:" + start + ",cFinish:" + finish) + ")";
  }
}
