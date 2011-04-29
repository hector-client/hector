package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.List;
import java.nio.ByteBuffer;

import junit.framework.Assert;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
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
  StringSerializer se = StringSerializer.get();
  LongSerializer le = LongSerializer.get();


  @Test
  public void testConstruction() {
    List<Column> tColumns = new ArrayList<Column>();
    ColumnSlice<String, Long> slice = new ColumnSliceImpl<String, Long>(tColumns, se, le);
    Assert.assertTrue(slice.getColumns().isEmpty());

    Column column = new Column(ByteBuffer.wrap(new byte[]{}));
    column.setValue(ByteBuffer.wrap(new byte[]{}));
    column.setTimestamp(0L);
    tColumns.add(column);
    slice = new ColumnSliceImpl<String, Long>(tColumns, se, le);
    Assert.assertEquals(1, slice.getColumns().size());

    tColumns = new ArrayList<Column>();
    column = new Column(se.toByteBuffer("1"));
    column.setValue(le.toByteBuffer(1L));
    column.setTimestamp(0L);
    tColumns.add(column);
    slice = new ColumnSliceImpl<String, Long>(tColumns, se, le);
    Assert.assertEquals((Long) 1L, slice.getColumnByName("1").getValue());
  }

  @Test
  public void testMultiCallOnByteBuffer() {
    List<Column> tColumns = new ArrayList<Column>();
    Column column = new Column(se.toByteBuffer("1"));
    column.setValue(ByteBuffer.wrap("colvalue".getBytes()));
    column.setTimestamp(0L);
    tColumns.add(column);
    ColumnSlice<String, ByteBuffer> slice = new ColumnSliceImpl<String, ByteBuffer>(tColumns, se, ByteBufferSerializer.get());

    ByteBuffer value = slice.getColumnByName("1").getValue();
    Assert.assertEquals("colvalue", se.fromByteBuffer(value));
    value.rewind();
    Assert.assertEquals("colvalue", se.fromByteBuffer(value));
    value.rewind();
    Assert.assertEquals("colvalue", se.fromByteBuffer(value));
  }
}
