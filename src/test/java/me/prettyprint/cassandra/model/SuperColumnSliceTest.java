package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import me.prettyprint.cassandra.extractors.BytesExtractor;
import me.prettyprint.cassandra.extractors.LongExtractor;
import me.prettyprint.cassandra.extractors.StringExtractor;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.SuperColumn;
import org.junit.Test;

/**
 *
 * @author Ran Tavory
 *
 */
public class SuperColumnSliceTest {

  @Test
  public void testConstruction() {
    LongExtractor le = LongExtractor.get();
    StringExtractor se = StringExtractor.get();
    BytesExtractor be = BytesExtractor.get();
    // empty one
    List<SuperColumn> tColumns = new ArrayList<SuperColumn>();
    SuperSlice<String, Long, byte[]> slice = new SuperSlice<String, Long, byte[]>(tColumns, se, le,
        be);
    Assert.assertTrue(slice.getSuperColumns().isEmpty());

    // non-empty one
    Column c = new Column(le.toBytes(5L), be.toBytes(new byte[] { 1 }), 2);
    tColumns.add(new SuperColumn(se.toBytes("super"), Arrays.asList(c)));
    slice = new SuperSlice<String, Long, byte[]>(tColumns, se, le, be);
    Assert.assertEquals(1, slice.getSuperColumns().size());
    Assert.assertEquals((Long) 5L, slice.getColumnByName("super").get(0).getName());
  }
}
