package me.prettyprint.cassandra.model;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.IndexExpression;
import org.apache.cassandra.thrift.KeyRange;

/**
 * A helper class for range queries.
 * <p>
 * We allow only keys range, no tokens range since if you want to use tokens and you're not hadoop
 * then you're (probably) doing something wrong (JE)
 *
 * @author Ran Tavory
 * @author Javier A. Sotelo
 */
public final class HKeyRange<K> {

  private K startKey;
  private K endKey;
  private List<IndexExpression> rowFilters;

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
  
  public void addToExpressions(IndexExpression elem) {
    if (this.rowFilters == null) {
      this.rowFilters = new ArrayList<IndexExpression>();
    }
    this.rowFilters.add(elem);
  }

  /**
   *
   * @return The thrift representation of this object
   */
  public KeyRange toThrift() {
    KeyRange keyRange = new KeyRange(rowCount);
    keyRange.setStart_key(startKey == null ? ByteBuffer.wrap(new byte[0]) :
        keySerializer.toByteBuffer(startKey));
    keyRange.setEnd_key(endKey == null ? ByteBuffer.wrap(new byte[0]) :
        keySerializer.toByteBuffer(endKey));
    if (rowFilters != null)
      for (IndexExpression filter : rowFilters) {
        keyRange.addToRow_filter(filter);
      }
    return keyRange;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("HKeyRange(start:");
    sb.append(startKey);
    sb.append(",end:");
    sb.append(endKey);
    sb.append(",indexed expressions:");
    if (rowFilters == null || rowFilters.isEmpty())
      sb.append("[]");
    else
      sb.append(Arrays.toString(rowFilters.toArray()));
    sb.append(")");
    return  sb.toString();
  }
}
