package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.KeyRange;

/**
 * A helper class for range queries
 *
 * @author Ran Tavory
 *
 */
/*package*/ final class HKeyRange {

  /** Whether to use start/end as tokens or as keys */
  private boolean useTokens = true;
  private String start, end;
  private int rowCount = 100;

  public HKeyRange setTokens(String start, String end) {
    useTokens = true;
    this.start = start;
    this.end = end;
    return this;
  }

  public HKeyRange setKeys(String start, String end) {
    useTokens = false;
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
    if (useTokens) {
      keyRange.setStart_token(start);
      keyRange.setEnd_token(end);
    } else {
      keyRange.setStart_key(start);
      keyRange.setEnd_key(end);
    }
    return keyRange;
  }

  @Override
  public String toString() {
    String tk = useTokens ? "t" : "k";
    return "HKeyRange(" + tk + "Start:" + start + "," + tk + "End:" + end + "," + ")";
  }

}
