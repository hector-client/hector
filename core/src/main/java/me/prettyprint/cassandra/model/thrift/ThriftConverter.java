package me.prettyprint.cassandra.model.thrift;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.HConsistencyLevel;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ConsistencyLevel;

/**
 * @author: peter
 * @author ran
 */
public class ThriftConverter {

  public static ConsistencyLevel consistencyLevel(HConsistencyLevel c) {
    switch (c) {
    case ALL:
      return ConsistencyLevel.ALL;
    case ANY:
      return ConsistencyLevel.ANY;
    case ONE:
      return ConsistencyLevel.ONE;
    case TWO:
      return ConsistencyLevel.TWO;
    case THREE:
      return ConsistencyLevel.THREE;
    case QUORUM:
      return ConsistencyLevel.QUORUM;
    case EACH_QUORUM:
      return ConsistencyLevel.EACH_QUORUM;
    case LOCAL_QUORUM:
      return ConsistencyLevel.LOCAL_QUORUM;
    case LOCAL_ONE:
        return ConsistencyLevel.LOCAL_ONE;
    default:
      throw new RuntimeException("Unregornized consistency level " + c);
    }
  }

  /**
   * Converts a list of ColumnOrSuperColumn to Column
   * @param columns
   * @return
   */
  public static List<Column> getColumnList(List<ColumnOrSuperColumn> columns) {
    ArrayList<Column> list = new ArrayList<Column>(columns.size());
    for (ColumnOrSuperColumn col : columns) {
      list.add(col.getColumn());
    }
    return list;
  }

}
