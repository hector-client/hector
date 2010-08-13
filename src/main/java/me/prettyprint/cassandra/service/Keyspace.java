package me.prettyprint.cassandra.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.NotFoundException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Clock;
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
  Column getColumn(byte[] key, ColumnPath columnPath) throws HectorException;

  Column getColumn(String key, ColumnPath columnPath) throws HectorException;
  
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
  SuperColumn getSuperColumn(byte[] key, ColumnPath columnPath) throws HectorException;

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
   * @throws NotFoundException
   *           when a supercolumn is not found
   */
  SuperColumn getSuperColumn(byte[] key, ColumnPath columnPath, boolean reversed, int size)
      throws HectorException;

  /**
   * Get the group of columns contained by columnParent.
   *
   * Returns Either a ColumnFamily name or a ColumnFamily/SuperColumn specified
   * by the given predicate. If no matching values are found, an empty list is
   * returned.
   */
  List<Column> getSlice(byte[] key, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException;

  List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
  throws HectorException;
  
  /**
   * Get the group of superColumn contained by columnParent.
   */
  List<SuperColumn> getSuperSlice(byte[] key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
	      SlicePredicate predicate) throws HectorException;
  
  /**
   * Performs a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<byte[], SuperColumn> multigetSuperColumn(List<byte[]> keys, ColumnPath columnPath)
      throws HectorException;

  /**
   * Perform a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<byte[], SuperColumn> multigetSuperColumn(List<byte[]> keys, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException;

  /**
   * Performs a get_slice for columnParent and predicate for the given keys in
   * parallel.
   */
  Map<byte[], List<Column>> multigetSlice(List<byte[]> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  /**
   * Performs a get_slice for a superColumn columnParent and predicate for the
   * given keys in parallel.
   */
  Map<byte[], List<SuperColumn>> multigetSuperSlice(List<byte[]> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws HectorException;

  /**
   * Inserts a column.
   */
  void insert(byte[] key, ColumnParent columnParent, Column column) throws HectorException;

  void insert(String key, ColumnPath columnPath, byte[] value) throws HectorException;

  void insert(String key, ColumnPath columnPath, byte[] value, long timestamp) throws HectorException;

  /**
   * Call batch mutate with the assembled mutationMap. This method is a direct pass-through
   * to the underlying Thrift API
   */
  void batchMutate(Map<byte[],Map<String,List<Mutation>>> mutationMap) throws HectorException;

  /**
   * Call batch mutate with the BatchMutation object which encapsulates some of the complexity
   * of the batch_mutate API signature
   */
  void batchMutate(BatchMutation batchMutation) throws HectorException;

  void remove(byte[] key, ColumnPath columnPath);

/**
   * Same as two argument version, but the caller must specify their own clock
   */
  void remove(byte[] key, ColumnPath columnPath, Clock clock) throws HectorException;

  void remove(String key, ColumnPath columnPath) throws HectorException;

  void remove(String key, ColumnPath columnPath, long timestamp) throws HectorException;
  
  /**
   * get a description of the specified keyspace
   */
  Map<String, Map<String, String>> describeKeyspace() throws HectorException;

  /**
   * Counts the columns present in columnParent.
   */
  int getCount(byte[] key, ColumnParent columnParent, SlicePredicate predicate) throws HectorException;

  /**
   * returns a subset of columns for a range of keys.
   */
  Map<byte[], List<Column>> getRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange) throws HectorException;

  /**
   * returns a subset of super columns for a range of keys.
   */
  Map<byte[], List<SuperColumn>> getSuperRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
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
   * Creates a clock.
   * Clocks are created according to the system's current time milli and if needed are
   * multiplied by 1000 (if micro is required).
   * The clock resolution is determined by {@link me.prettyprint.cassandra.service.CassandraClient#getClockResolution()}
   * @return a clock!
   */
  Clock createClock();
}
