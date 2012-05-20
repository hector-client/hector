package me.prettyprint.cassandra.model;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.KeyRange;

/**
 * A helper class for range queries.
 * <p>
 *  Exactly one of {start key, end key} or {start token, end token} must be specified
  * If none of the tokens is set, then the keys are assigned. If the keys are null, and empty byte array is used.
 *
 * @author Ran Tavory
 *
 */
public final class HKeyRange<K> {

  private K startKey;
  private K endKey;
  private String startToken;
  private String endToken;

  private int rowCount = 100;

  private final Serializer<K> keySerializer;

  public HKeyRange(Serializer<K> keySerializer) {
    Assert.notNull(keySerializer, "keySerializer is null");
    this.keySerializer = keySerializer;
  }

   /**
    * Set the keys and reset the tokens to null.
    * @param start
    * @param end
    * @return this
    */
  public HKeyRange<K> setKeys(K start, K end) {
    this.startKey = start;
    this.endKey = end;
    this.startToken = null;
    this.endToken = null;
    return this;
  }

   /**
    * Set the tokens and reset the keys to null.
    * @param start
    * @param end
    * @return this
    */
  public HKeyRange<K> setTokens(String start, String end) {
    this.startToken = start;
    this.endToken = end;
    this.startKey = null;
    this.endKey = null;
    return this;
  }

  public HKeyRange<K> setRowCount(int rowCount) {
    this.rowCount = rowCount;
    return this;
  }

  /**
   *
   * @return The thrift representation of this object
   */
  public KeyRange toThrift() {
     KeyRange keyRange = new KeyRange(rowCount);
     // exactly one of {start key, end key} or {start token, end token} must be specified
     // If none of the tokens is set, then the keys are assigned.
     if (startToken != null || endToken != null) {
        keyRange.setStart_token(startToken);
        keyRange.setEnd_token(endToken);
     } else {
        keyRange.setStart_key(startKey == null ? ByteBuffer.wrap(new byte[0]) :
              keySerializer.toByteBuffer(startKey));
        keyRange.setEnd_key(endKey == null ? ByteBuffer.wrap(new byte[0]) :
              keySerializer.toByteBuffer(endKey));
     }
     return keyRange;
  }

  @Override
  public String toString() {
    return "HKeyRange(start:" + startKey + ",end:" + endKey + "," + ")";
  }
}
