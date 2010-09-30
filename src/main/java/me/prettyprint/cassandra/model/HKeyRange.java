package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;

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
public final class HKeyRange<K> {

  private K startKey;
  private K endKey;

  private int rowCount = 100;

  private final Serializer<K> keySerializer;

  public HKeyRange(Serializer<K> keySerializer) {
    Assert.notNull(keySerializer, "keySerializer is null");
    this.keySerializer = keySerializer;
  }

  public HKeyRange<K> setKeys(K start, K end) {
    this.startKey = start;
    this.endKey = end;
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
    Assert.notNull(startKey, "start can't be null");
    Assert.notNull(endKey, "end can't be null");

    KeyRange keyRange = new KeyRange(rowCount);
    keyRange.setStart_key(keySerializer.toBytes(startKey));
    keyRange.setEnd_key(keySerializer.toBytes(endKey));
    return keyRange;
  }

  @Override
  public String toString() {
    return "HKeyRange(start:" + startKey + ",end:" + endKey + "," + ")";
  }
}
