package me.prettyprint.hom;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Keys in Cassandra cannot inherently be multi-field, so a strategy must be
 * employed to concatenate fields together.
 * 
 * <p/>
 * This strategy uses a delimiter to segment the fields. By default the
 * delimiter is 2 pipes, ||, but can be overriden by calling
 * {@link #setDelimiter(byte[])}.
 * 
 * @author B. Todd Burruss
 */
public class KeyConcatenationDelimiterStrategyImpl implements KeyConcatenationStrategy {
  private byte[] delimiter = "||".getBytes();

  @Override
  public byte[] concat(List<byte[]> segmentList) {
    int totalSegmentSize = 0;
    for (byte[] ba : segmentList) {
      totalSegmentSize += ba.length;
    }

    ByteBuffer colFamKey = ByteBuffer.allocate(totalSegmentSize + (segmentList.size() - 1)
        * delimiter.length);

    int segNum = 1;
    for (byte[] ba : segmentList) {
      colFamKey.put(ba);
      if (segNum++ < segmentList.size()) {
        colFamKey.put(delimiter);
      }
    }

    return colFamKey.array();
  }

  @Override
  public List<byte[]> split(byte[] colFamKey) {
    ByteBuffer bb = ByteBuffer.wrap(colFamKey);
    List<byte[]> segmentList = new LinkedList<byte[]>();
    int segStart = 0;
    bb.mark();
    while (bb.remaining() >= delimiter.length) {
      // compare delimeter to current position
      boolean delimiterFound = true;
      for (byte b : delimiter) {
        if (bb.get() != b) {
          delimiterFound = false;
          break;
        }
      }
      
      if (delimiterFound) {
        int segSize = bb.position() - segStart-delimiter.length;
        segmentList.add(copyFromMark(bb, segStart, segSize));
        bb.position(bb.position() + delimiter.length);
        segStart = bb.position();
        bb.mark();
      }
    }
    
    
    segmentList.add(copyFromMark(bb, segStart, bb.capacity()-segStart));

    return segmentList;
  }

  private byte[] copyFromMark(ByteBuffer bb, int segStart, int segSize) {
    byte[] newSeg = new byte[segSize];

    bb.reset();
    bb.get(newSeg, 0, segSize);
    return newSeg;
  }

  public byte[] getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(byte[] delimiter) {
    this.delimiter = delimiter;
  }
}
