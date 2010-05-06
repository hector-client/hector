package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import org.apache.cassandra.thrift.*;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;

/**
 * The keyspace is a high level handle to all read/write operations to cassandra.
 *
 * A Keyspace object is not thread safe. Use one keyspace per thread please!
 *
 * @author rantav
 */
public interface Keyspace {

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
   * @throws NotFoundException
   *           if no value exists for the column
   */
  Column getColumn(String key, ColumnPath columnPath) throws InvalidRequestException,
          NotFoundException, UnavailableException, TException, TimedOutException;

  /**
   * Get the SuperColumn at the given columnPath.
   *
   * If no value is present, NotFoundException is thrown.
   *
   * by default will return column with native order and the size of the list is
   * unlimited (so be careful...)
   *
   * @throws NotFoundException
   *           when a supercolumn is not found
   */
  SuperColumn getSuperColumn(String key, ColumnPath columnPath)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException;

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
   * @throws NotFoundException
   *           when a supercolumn is not found
   */
  SuperColumn getSuperColumn(String key, ColumnPath columnPath, boolean reversed, int size)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException;

  /**
   * Get the group of columns contained by columnParent.
   *
   * Returns Either a ColumnFamily name or a ColumnFamily/SuperColumn specified
   * by the given predicate. If no matching values are found, an empty list is
   * returned.
   */
  List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException;

  /**
   * Get the group of superColumn contained by columnParent.
   */
  List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
      SlicePredicate predicate) throws InvalidRequestException, NotFoundException,
      UnavailableException, TException, TimedOutException;

  /**
   * Performs a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the Column found. If no value corresponding
   * to a key is present, the key will still be in the map, but both the column
   * and superColumn references of the ColumnOrSuperColumn object it maps to
   * will be null.
   */
  Map<String, Column> multigetColumn(List<String> keys, ColumnPath columnPath)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException;

  /**
   * Performs a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException;

  /**
   * Perform a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath,
      boolean reversed, int size) throws InvalidRequestException, UnavailableException, TException,
      TimedOutException;

  /**
   * Performs a get_slice for columnParent and predicate for the given keys in
   * parallel.
   */
  Map<String, List<Column>> multigetSlice(List<String> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws InvalidRequestException, UnavailableException, TException,
      TimedOutException;

  /**
   * Performs a get_slice for a superColumn columnParent and predicate for the
   * given keys in parallel.
   */
  Map<String, List<SuperColumn>> multigetSuperSlice(List<String> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  /**
   * Inserts a column.
   */
  void insert(String key, ColumnPath columnPath, byte[] value)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException;

  /**
   * Insert Columns or SuperColumns across different Column Families for the same row key.
   */
  void batchInsert(String key, Map<String, List<Column>> cfmap,
      Map<String, List<SuperColumn>> superColumnMap) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  /**
   * Call batch mutate with the assembled mutationMap. This method is a direct pass-through
   * to the underlying Thrift API
   */
  void batchMutate(Map<String, Map<String, List<Mutation>>> mutationMap) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  /**
   * Call batch mutate with the BatchMutation object which encapsulates some of the complexity
   * of the batch_mutate API signature
   */
  void batchMutate(BatchMutation batchMutation) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  /**
   * Remove data from the row specified by key at the columnPath.
   *
   * Note that all the values in columnPath besides columnPath.column_family are truly optional:
   * you can remove the entire row by just specifying the ColumnFamily, or you can remove
   * a SuperColumn or a single Column by specifying those levels too.
   */
  void remove(String key, ColumnPath columnPath) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  /**
   * get a description of the specified keyspace
   */
  Map<String, Map<String, String>> describeKeyspace() throws NotFoundException, TException;

  /**
   * Counts the columns present in columnParent.
   */
  int getCount(String key, ColumnParent columnParent) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  /**
   * returns a subset of columns for a range of keys.
   * @deprecated use {@link #getRangeSlices(ColumnParent, SlicePredicate, KeyRange)}
   */
  Map<String, List<Column>> getRangeSlice(ColumnParent columnParent, SlicePredicate predicate,
      String start, String finish, int count)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException;

  /**
   * returns a subset of columns for a range of keys.
   */
  Map<String, List<Column>> getRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException;

  /**
   * returns a subset of super columns for a range of keys.
   * @deprecated use {@link #getRangeSlices(ColumnParent, SlicePredicate, KeyRange)}
   */
  Map<String, List<SuperColumn>> getSuperRangeSlice(ColumnParent columnParent, SlicePredicate predicate,
      String start, String finish, int count)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException;

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
