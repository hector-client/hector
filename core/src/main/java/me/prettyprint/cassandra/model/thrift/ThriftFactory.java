package me.prettyprint.cassandra.model.thrift;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.CounterColumn;

/**
 * Utility factory class for creating thrift objects.
 *
 * @author Ran Tavory
 *
 */
public class ThriftFactory {

  // probably should be typed for thrift vs. avro
  public static <N> ColumnPath createColumnPath(String columnFamilyName, N columnName,
      Serializer<N> nameSerializer) {
    return createColumnPath(columnFamilyName, nameSerializer.toByteBuffer(columnName));
  }
  private static <N> ColumnPath createColumnPath(String columnFamilyName, ByteBuffer columnName) {
    notNull(columnFamilyName, "columnFamilyName cannot be null");
    ColumnPath columnPath = new ColumnPath(columnFamilyName);
    if (columnName != null) {
      columnPath.setColumn(columnName);
    }
    return columnPath;
  }

  /*package*/ static <N> ColumnPath createColumnPath(String columnFamilyName) {
    return createColumnPath(columnFamilyName, null);
  }

  public static <SN,N> ColumnPath createSuperColumnPath(String columnFamilyName,
      SN superColumnName, N columnName, Serializer<SN> superNameSerializer,
      Serializer<N> nameSerializer) {
    noneNull(columnFamilyName, superColumnName, superNameSerializer, nameSerializer);
    ColumnPath columnPath = createColumnPath(columnFamilyName, nameSerializer.toByteBuffer(columnName));
    columnPath.setSuper_column(superNameSerializer.toByteBuffer(superColumnName));
    return columnPath;
  }

  public static <SN> ColumnPath createSuperColumnPath(String columnFamilyName,
      SN superColumnName, Serializer<SN> superNameSerializer) {
    noneNull(columnFamilyName, superNameSerializer);
    ColumnPath columnPath = createColumnPath(columnFamilyName, null);
    if (superColumnName != null) {
      columnPath.setSuper_column(superNameSerializer.toByteBuffer(superColumnName));
    }
    return columnPath;
  }
  
  public static CounterColumn createCounterColumn(String name, long value) {
    CounterColumn cc = new CounterColumn();
    cc.setName(StringSerializer.get().toByteBuffer(name));
    cc.setValue(value);
    return cc;
  }
  
  public static <N> CounterColumn createCounterColumn(N name, long value, Serializer<N> ns) {
    CounterColumn cc = new CounterColumn();
    cc.setName(ns.toByteBuffer(name));
    cc.setValue(value);
    return cc;
  }

}
