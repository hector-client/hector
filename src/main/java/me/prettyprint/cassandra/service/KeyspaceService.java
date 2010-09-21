package me.prettyprint.cassandra.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * The keyspace is a high level handle to all read/write operations to cassandra.
 *
 * A Keyspace object is not thread safe. Use one keyspace per thread please!
 *
 * @author rantav
 */
public interface KeyspaceService {

  public static String CF_TYPE = "Type" ;
  public static String CF_TYPE_STANDARD = "Standard" ;
  public static String CF_TYPE_SUPER = "Super" ;

  /**
   * @return The cassandra client object used to obtain this KeySpace.
   */
  CassandraClient getClient();

  /**
   * Get the Column at the given columnPath.
   *
   * If no value is present, NotFoundException is thrown.
   *
   * @throws HNotFoundException
   *           if no value exists for the column
   */
  Column getColumn(String key, ColumnPath columnPath) throws HectorException;

  /**
   * Get the SuperColumn at the given columnPath.
   *
   * If no value is present, NotFoundException is thrown.
   *
   * by default will return column with native order and the size of the list is
   * unlimited (so be careful...)
   *
   * @throws HNotFoundException
   *           when a supercolumn is not found
   */
  SuperColumn getSuperColumn(String key, ColumnPath columnPath) throws HectorException;

  /**
   * Get the SuperColumn at the given columnPath.
   *
   * If no value is present, NotFoundException is thrown.
   *
   * by default will return column with native order and the size of the list is
   * unlimited (so be careful...)
   *
   * @param reversed
   *          the result Column sort
   * @param size
   *          the result column size
   * @throws HNotFoundException
   *           when a supercolumn is not found
   */
  SuperColumn getSuperColumn(String key, ColumnPath columnPath, boolean reversed, int size)
      throws HectorException;

  /**
   * Get the group of columns contained by columnParent.
   *
   * Returns Either a ColumnFamily name or a ColumnFamily/SuperColumn specified
   * by the given predicate. If no matching values are found, an empty list is
   * returned.
   */
  List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException;

  /**
   * Get the group of superColumn contained by columnParent.
   */
  List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  /**
   * Performs a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the Column found. If no value corresponding
   * to a key is present, the key will still be in the map, but both the column
   * and superColumn references of the ColumnOrSuperColumn object it maps to
   * will be null.
   */
  Map<String, Column> multigetColumn(List<String> keys, ColumnPath columnPath)
      throws HectorException;

  /**
   * Performs a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath)
      throws HectorException;

  /**
   * Perform a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException;

  /**
   * Performs a get_slice for columnParent and predicate for the given keys in
   * parallel.
   */
  Map<String, List<Column>> multigetSlice(List<String> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  /**
   * Performs a get_slice for a superColumn columnParent and predicate for the
   * given keys in parallel.
   */
  Map<String, List<SuperColumn>> multigetSuperSlice(List<String> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws HectorException;

  /**
   * Inserts a column.
   */
  void insert(String key, ColumnPath columnPath, byte[] value) throws HectorException;

  void insert(String key, ColumnPath columnPath, byte[] value, long timestamp) throws HectorException;

  /**
   * Insert Columns or SuperColumns across different Column Families for the same row key.
   */
  void batchInsert(String key, Map<String, List<Column>> cfmap,
      Map<String, List<SuperColumn>> superColumnMap) throws HectorException;

  /**
   * Call batch mutate with the assembled mutationMap. This method is a direct pass-through
   * to the underlying Thrift API
   */
  void batchMutate(Map<String, Map<String, List<Mutation>>> mutationMap) throws HectorException;

  /**
   * Call batch mutate with the BatchMutation object which encapsulates some of the complexity
   * of the batch_mutate API signature
   */
  void batchMutate(BatchMutation batchMutation) throws HectorException;

  /**
   * Remove data from the row specified by key at the columnPath.
   *
   * Note that all the values in columnPath besides columnPath.column_family are truly optional:
   * you can remove the entire row by just specifying the ColumnFamily, or you can remove
   * a SuperColumn or a single Column by specifying those levels too.
   */
  void remove(String key, ColumnPath columnPath) throws HectorException;

  /**
   * Same as two argument version, but the caller must specify their own timestamp
   */
  void remove(String key, ColumnPath columnPath, long timestamp) throws HectorException;

  /**
   * get a description of the specified keyspace
   */
  Map<String, Map<String, String>> describeKeyspace() throws HectorException;

  /**
   * Counts the columns present in columnParent.
   */
  int getCount(String key, ColumnParent columnParent) throws HectorException;

  /**
   * returns a subset of columns for a range of keys.
   * @deprecated use {@link #getRangeSlices(ColumnParent, SlicePredicate, KeyRange)}
   */
  @Deprecated
  Map<String, List<Column>> getRangeSlice(ColumnParent columnParent, SlicePredicate predicate,
      String start, String finish, int count)
      throws HectorException;

  /**
   * returns a subset of columns for a range of keys.
   */
  LinkedHashMap<String, List<Column>> getRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange) throws HectorException;

  /**
   * returns a subset of super columns for a range of keys.
   * @deprecated use {@link #getSuperRangeSlices(ColumnParent, SlicePredicate, KeyRange)}
   */
  @Deprecated
  Map<String, List<SuperColumn>> getSuperRangeSlice(ColumnParent columnParent, SlicePredicate predicate,
      String start, String finish, int count) throws HectorException;

  /**
   * returns a subset of super columns for a range of keys.
   */
  LinkedHashMap<String, List<SuperColumn>> getSuperRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange) throws HectorException;

  /**
   * @return The consistency level held by this keyspace instance.
   */
  ConsistencyLevel getConsistencyLevel();

  String getName();

  /**
   * @return The failover policy used by this keyspace.
   */
  FailoverPolicy getFailoverPolicy();

  /**
   * Creates a timestamp.
   * Timestamps are created according to the system's current time milli and if needed are
   * multiplied by 1000 (if micro is required).
   * The timestamp resolution is determined by {@link me.prettyprint.cassandra.service.CassandraClient#getTimestampResolution()}
   * @return a timestamp!
   */
  long createTimestamp();
}
