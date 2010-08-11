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
  byte[] start;
private byte[] end;

String start_token;
String end_token;
  private int rowCount = 100;

  public HKeyRange setTokens(String start, String end) {
    useTokens = true;
    this.start_token = start;
    this.end_token = end;
    return this;
  }

  public HKeyRange setKeys(byte[] start, byte[] end) {
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
      keyRange.setStart_token(start_token);
      keyRange.setEnd_token(end_token);
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
