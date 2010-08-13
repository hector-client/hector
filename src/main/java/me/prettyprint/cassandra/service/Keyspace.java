package me.prettyprint.cassandra.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.Extractor;
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
  <K> Column getColumn(K key, ColumnPath columnPath, Extractor<K> keyExtractor) throws HectorException;

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
  <K> SuperColumn getSuperColumn(K key, ColumnPath columnPath,  Extractor<K> keyExtractor) throws HectorException;

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
  <K> SuperColumn getSuperColumn(K key, ColumnPath columnPath, boolean reversed, int size,  Extractor<K> keyExtractor)
      throws HectorException;

  /**
   * Get the group of columns contained by columnParent.
   *
   * Returns Either a ColumnFamily name or a ColumnFamily/SuperColumn specified
   * by the given predicate. If no matching values are found, an empty list is
   * returned.
   */
  <K> List<Column> getSlice(K key, ColumnParent columnParent, SlicePredicate predicate, Extractor<K> keyExtractor)
      throws HectorException;

  List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
  throws HectorException;
  
  /**
   * Get the group of superColumn contained by columnParent.
   */
  <K> List<SuperColumn> getSuperSlice(K key, ColumnParent columnParent,
      SlicePredicate predicate,  Extractor<K> keyExtractor) throws HectorException;

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
  <K> Map<K, SuperColumn> multigetSuperColumn(List<K> keys, ColumnPath columnPath,  Extractor<K> keyExtractor)
      throws HectorException;

  /**
   * Perform a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  <K> Map<K, SuperColumn> multigetSuperColumn(List<K> keys, ColumnPath columnPath,
      boolean reversed, int size, Extractor<K> keyExtractor) throws HectorException;

  /**
   * Performs a get_slice for columnParent and predicate for the given keys in
   * parallel.
   */
  <K> Map<K, List<Column>> multigetSlice(List<K> keys,
      ColumnParent columnParent, SlicePredicate predicate, Extractor<K> keyExtractor) throws HectorException;

  /**
   * Performs a get_slice for a superColumn columnParent and predicate for the
   * given keys in parallel.
   */
  <K> Map<K, List<SuperColumn>> multigetSuperSlice(List<K> keys,
      ColumnParent columnParent, SlicePredicate predicate, Extractor<K> keyExtractor) throws HectorException;

  /**
   * Inserts a column.
   */
  <K> void insert(K key, ColumnParent columnParent, Column column, Extractor<K> keyExtractor) throws HectorException;

  void insert(String key, ColumnPath columnPath, byte[] value) throws HectorException;

  void insert(String key, ColumnPath columnPath, byte[] value, long timestamp) throws HectorException;

  /**
   * Call batch mutate with the assembled mutationMap. This method is a direct pass-through
   * to the underlying Thrift API
   */
  <K> void batchMutate(Map<K,Map<String,List<Mutation>>> mutationMap, Extractor<K> keyExtractor) throws HectorException;

  /**
   * Call batch mutate with the BatchMutation object which encapsulates some of the complexity
   * of the batch_mutate API signature
   */
  <K> void batchMutate(BatchMutation<K> batchMutation) throws HectorException;

  <K> void remove(K key, ColumnPath columnPath, Extractor<K> keyExtractor);

/**
   * Same as two argument version, but the caller must specify their own clock
   */
  <K> void remove(K key, ColumnPath columnPath, Clock clock, Extractor<K> keyExtractor) throws HectorException;

  void remove(String key, ColumnPath columnPath) throws HectorException;

  void remove(String key, ColumnPath columnPath, long timestamp) throws HectorException;
  
  /**
   * get a description of the specified keyspace
   */
  Map<String, Map<String, String>> describeKeyspace() throws HectorException;

  /**
   * Counts the columns present in columnParent.
   */
  <K> int getCount(K key, ColumnParent columnParent, SlicePredicate predicate, Extractor<K> keyExtractor) throws HectorException;

  /**
   * returns a subset of columns for a range of keys.
   */
  <K> LinkedHashMap<K, List<Column>> getRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange, Extractor<K> keyExtractor) throws HectorException;

  /**
   * returns a subset of super columns for a range of keys.
   */
  <K> LinkedHashMap<K, List<SuperColumn>> getSuperRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange, Extractor<K> keyExtractor) throws HectorException;

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
