package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.KeyRange;

/**
 * A helper class for range queries.
 * <p>
 * We allow only keys range, no tokens range since if you want to use tokens and you're not hadoop
 * then you're (probably) doing something wrong (JE)
 *
 * @author Ran Tavory
 *
 */
/*package*/ final class HKeyRange {

  private String start, end;
  private int rowCount = 100;

  public HKeyRange setKeys(String start, String end) {
    this.start = start;
    this.end = end;
    return this;
  }

  public HKeyRange setRowCount(int rowCount) {
    this.rowCount = rowCount;
    return this;
  }

  /**
   *
   * @return The thrift representation of this object
   */
  public KeyRange toThrift() {
    Assert.notNull(start, "start can't be null");
    Assert.notNull(end, "end can't be null");

    KeyRange keyRange = new KeyRange(rowCount);
    keyRange.setStart_key(start);
    keyRange.setEnd_key(end);
    return keyRange;
  }

  @Override
  public String toString() {
    return "HKeyRange(start:" + start + ",end:" + end + "," + ")";
  }
}
