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
  byte[] start_key;
private byte[] end_key;

String start_token;
String end_token;
  private int rowCount = 100;

  public HKeyRange setTokens(String start, String end) {
    useTokens = true;
    this.start_token = start;
    this.end_token = end;
    return this;
  }

  public <K> HKeyRange setKeys(K start, K end, Extractor<K> keyExtractor) {
    useTokens = false;
    this.start_key = keyExtractor.toBytes(start);
    this.end_key = keyExtractor.toBytes(end);
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

    KeyRange keyRange = new KeyRange(rowCount);
    if (useTokens) {
      Assert.notNull(start_token, "start_token can't be null");
      Assert.notNull(end_token, "end_token can't be null");
      keyRange.setStart_token(start_token);
      keyRange.setEnd_token(end_token);
    } else {
      Assert.notNull(start_key, "start can't be null");
      Assert.notNull(end_key, "end can't be null");
      keyRange.setStart_key(start_key);
      keyRange.setEnd_key(end_key);
    }
    return keyRange;
  }

  @Override
  public String toString() {
    String tk = useTokens ? "t" : "k";
    return "HKeyRange(" + tk + "Start:" + start_key + "," + tk + "End:" + end_key + "," + ")";
  }

}
