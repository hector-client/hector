package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.utils.FBUtilities;

/**
 * A helper class for range queries
 *
 * @author Ran Tavory
 *
 */
/*package*/ final class HKeyRange {

  /** Whether to use start/end as tokens */
  private boolean useTokens = false;
  /** Whether to use start/end Key */
  private boolean useKeys = false;
  byte[] startKey;
  private byte[] endKey;

  String startToken;
  String endToken;
  private int rowCount = 100;

  public HKeyRange setTokens(String start, String end) {
    useTokens = true;
    useKeys = false;
    this.startToken = start;
    this.endToken = end;
    return this;
  }

  public <K> HKeyRange setKeys(K start, K end, Serializer<K> keySerializer) {
    useKeys = true;
    useTokens = false;
    this.startKey = keySerializer.toBytes(start);
    this.endKey = keySerializer.toBytes(end);
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
      Assert.notNull(startToken, "start_token can't be null");
      Assert.notNull(endToken, "end_token can't be null");
      keyRange.setStart_token(FBUtilities.md5hash(startToken.getBytes()).toString());
      keyRange.setEnd_token(FBUtilities.md5hash(endToken.getBytes()).toString());
    } 
    if (useKeys) {
      Assert.notNull(startKey, "start can't be null");
      Assert.notNull(endKey, "end can't be null");
      keyRange.setStart_key(startKey);
      keyRange.setEnd_key(endKey);
    }
    return keyRange;
  }

  @Override
  public String toString() {
    String tk = useTokens ? "t" : "k";
    return "HKeyRange(" + tk + "Start:" + startKey + "," + tk + "End:" + endKey + "," + ")";
  }

}
