package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import me.prettyprint.cassandra.extractors.LongExtractor;
import me.prettyprint.cassandra.extractors.StringExtractor;

import org.apache.cassandra.thrift.Column;
import org.junit.Test;


/**
 *
 * @author Ran Tavory
 *
 */
public class ColumnSliceTest {


  @Test
  public void testConstruction() {
    StringExtractor se = StringExtractor.get();
    LongExtractor le = LongExtractor.get();
    List<Column> tColumns = new ArrayList<Column>();
    ColumnSlice<String, Long> slice = new ColumnSlice<String, Long>(tColumns, se, le);
    Assert.assertTrue(slice.getColumns().isEmpty());

    tColumns.add(new Column(new byte[]{}, new byte[]{}, 0));
    slice = new ColumnSlice<String, Long>(tColumns, se, le);
    Assert.assertEquals(1, slice.getColumns().size());

    tColumns = new ArrayList<Column>();
    tColumns.add(new Column(se.toBytes("1"), le.toBytes(1L), 0));
    slice = new ColumnSlice<String, Long>(tColumns, se, le);
    Assert.assertEquals((Long) 1L, slice.getColumnByName("1").getValue());
  }
}
