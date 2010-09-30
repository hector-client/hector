package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.ColumnSlice;

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
    StringSerializer se = StringSerializer.get();
    LongSerializer le = LongSerializer.get();
    List<Column> tColumns = new ArrayList<Column>();
    ColumnSlice<String, Long> slice = new ColumnSliceImpl<String, Long>(tColumns, se, le);
    Assert.assertTrue(slice.getColumns().isEmpty());

    tColumns.add(new Column(new byte[]{}, new byte[]{}, 0L));
    slice = new ColumnSliceImpl<String, Long>(tColumns, se, le);
    Assert.assertEquals(1, slice.getColumns().size());

    tColumns = new ArrayList<Column>();
    tColumns.add(new Column(se.toBytes("1"), le.toBytes(1L), 0L));
    slice = new ColumnSliceImpl<String, Long>(tColumns, se, le);
    Assert.assertEquals((Long) 1L, slice.getColumnByName("1").getValue());
  }
}
