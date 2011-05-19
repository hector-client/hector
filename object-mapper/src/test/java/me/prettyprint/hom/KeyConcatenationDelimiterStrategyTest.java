package me.prettyprint.hom;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class KeyConcatenationDelimiterStrategyTest {
  KeyConcatenationDelimiterStrategyImpl keyCatStrat = new KeyConcatenationDelimiterStrategyImpl();

  @Test
  public void testConcatAndSplitMultipleSegments() {
    List<byte[]> segList1 = new LinkedList<byte[]>();
    segList1.add("foo".getBytes());
    segList1.add("bar".getBytes());
    segList1.add("forever".getBytes());

    byte[] concat = keyCatStrat.concat(segList1);

    assertEquals(
        "foo".getBytes().length + keyCatStrat.getDelimiter().length + "bar".getBytes().length
            + keyCatStrat.getDelimiter().length + "forever".getBytes().length, concat.length);

    List<byte[]> segList2 = keyCatStrat.split(concat);

    assertEquals(segList1.size(), segList2.size());
    for (int i = 0; i < segList1.size(); i++) {
      assertEquals("'" + new String(segList1.get(i)) + "' expected, but got '"
          + new String(segList2.get(i)) + "'", ByteBuffer.wrap(segList1.get(i)),
          ByteBuffer.wrap(segList2.get(i)));
    }
  }

  @Test
  public void testConcatAndSplitOneSegments() {
    List<byte[]> segList1 = new LinkedList<byte[]>();
    segList1.add("foo".getBytes());

    byte[] concat = keyCatStrat.concat(segList1);

    assertEquals("foo".getBytes().length, concat.length);

    List<byte[]> segList2 = keyCatStrat.split(concat);

    assertEquals(segList1.size(), segList2.size());
    for (int i = 0; i < segList1.size(); i++) {
      assertEquals("'" + new String(segList1.get(i)) + "' expected, but got '"
          + new String(segList2.get(i)) + "'", ByteBuffer.wrap(segList1.get(i)),
          ByteBuffer.wrap(segList2.get(i)));
    }
  }

  @Test
  public void testConcatAndSplitOneSegmentContainingPartOfDelimeter() {
    List<byte[]> segList1 = new LinkedList<byte[]>();
    segList1.add("f|o|o".getBytes());

    byte[] concat = keyCatStrat.concat(segList1);

    assertEquals("f|o|o".getBytes().length, concat.length);

    List<byte[]> segList2 = keyCatStrat.split(concat);

    assertEquals(segList1.size(), segList2.size());
    for (int i = 0; i < segList1.size(); i++) {
      assertEquals("'" + new String(segList1.get(i)) + "' expected, but got '"
          + new String(segList2.get(i)) + "'", ByteBuffer.wrap(segList1.get(i)),
          ByteBuffer.wrap(segList2.get(i)));
    }
  }

  @Test
  public void testConcatAndSplitWithEmptySegments() {
    List<byte[]> segList1 = new LinkedList<byte[]>();
    segList1.add("foo".getBytes());
    segList1.add("".getBytes());
    segList1.add("".getBytes());
    segList1.add("forever".getBytes());
    segList1.add("".getBytes());

    byte[] concat = keyCatStrat.concat(segList1);

    assertEquals(
        "foo".getBytes().length + keyCatStrat.getDelimiter().length
        + "".getBytes().length + keyCatStrat.getDelimiter().length
        + "".getBytes().length + keyCatStrat.getDelimiter().length
        + "forever".getBytes().length + keyCatStrat.getDelimiter().length
        + "".getBytes().length
        , concat.length)
        ;

    List<byte[]> segList2 = keyCatStrat.split(concat);

    assertEquals(segList1.size(), segList2.size());
    for (int i = 0; i < segList1.size(); i++) {
      assertEquals("'" + new String(segList1.get(i)) + "' expected, but got '"
          + new String(segList2.get(i)) + "'", ByteBuffer.wrap(segList1.get(i)),
          ByteBuffer.wrap(segList2.get(i)));
    }
  }

}
