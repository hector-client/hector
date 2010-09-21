package me.prettyprint.cassandra.model.thrift;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.ColumnPath;

/**
 * Utility factory class for creating thrift objects.
 *
 * @author Ran Tavory
 *
 */
public class ThriftFactory {

  // probably should be typed for thrift vs. avro
  /*package*/ static <N> ColumnPath createColumnPath(String columnFamilyName, N columnName,
      Serializer<N> nameSerializer) {
    return createColumnPath(columnFamilyName, nameSerializer.toBytes(columnName));
  }
  private static <N> ColumnPath createColumnPath(String columnFamilyName, byte[] columnName) {
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
    ColumnPath columnPath = createColumnPath(columnFamilyName, nameSerializer.toBytes(columnName));
    columnPath.setSuper_column(superNameSerializer.toBytes(superColumnName));
    return columnPath;
  }

  /*package*/ static <SN> ColumnPath createSuperColumnPath(String columnFamilyName,
      SN superColumnName, Serializer<SN> superNameSerializer) {
    noneNull(columnFamilyName, superNameSerializer);
    ColumnPath columnPath = createColumnPath(columnFamilyName, null);
    if (superColumnName != null) {
      columnPath.setSuper_column(superNameSerializer.toBytes(superColumnName));
    }
    return columnPath;
  }

}
