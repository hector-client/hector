package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.StringUtils;

import org.apache.cassandra.thrift.ColumnPath;

public class ModelUtils {
  // probably should be typed for thrift vs. avro
  public static ColumnPath createColumnPath(String columnFamilyName, String columnName) {
    ColumnPath columnPath = new ColumnPath(columnFamilyName);
    if ( columnName != null ) {
      columnPath.setColumn(StringUtils.bytes(columnName));
    }
    return columnPath;
  }
}
