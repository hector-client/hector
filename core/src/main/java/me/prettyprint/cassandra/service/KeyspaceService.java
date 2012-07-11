package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.exceptions.HectorException;
import org.apache.cassandra.thrift.*;

/**
 * The keyspace is a high level handle to all read/write operations to cassandra.
 *
 * A Keyspace object is NOT THREAD SAFE. Use one keyspace per thread please!
 *
 * @author rantav
 */
public interface KeyspaceService {

  public static String CF_TYPE = "Type" ;
  public static String CF_TYPE_STANDARD = "Standard" ;
  public static String CF_TYPE_SUPER = "Super" ;

//  /**
//   * @return The cassandra client object used to obtain this KeySpace.
//   */
//  CassandraClient getClient();

  /**
   * Get the Column at the given columnPath.
   *
   * If no value is present, HNotFoundException is thrown (which is a sub-type of HectorException)
   *
   * @throws HectorException
   *           if any other error occurs (including HNotFoundException if no value is present)
   */
  Column getColumn(ByteBuffer key, ColumnPath columnPath) throws HectorException;

  Column getColumn(String key, ColumnPath columnPath) throws HectorException;
  
  /**
   * Get the Counter Column at the given columnPath.
   *
   * If no value is present, HNotFoundException is thrown (which is a sub-type of HectorException)
   *
   * @throws HectorException
   *           if any other error occurs (including HNotFoundException if no value is present)
   */
  CounterColumn getCounter(ByteBuffer key, ColumnPath columnPath) throws HectorException;
  
  CounterColumn getCounter(String key, ColumnPath columnPath) throws HectorException;

  /**
   * Get the SuperColumn at the given columnPath.
   *
   * by default will return column with native order and the size of the list is
   * unlimited (so be careful...)
   *
   * If no value is present, HNotFoundException is thrown (which is a sub-type of HectorException)
   *
   * @throws HectorException
   *           if any other error occurs (including HNotFoundException if no value is present)
   */
  SuperColumn getSuperColumn(ByteBuffer key, ColumnPath columnPath) throws HectorException;

  SuperColumn getSuperColumn(String key, ColumnPath columnPath) throws HectorException;

  /**
   * Get the SuperColumn at the given columnPath.
   *
   * If no value is present, HNotFoundException is thrown (which is a sub-type of HectorException)
   *
   * by default will return column with native order and the size of the list is
   * unlimited (so be careful...)
   *
   * @param reversed
   *          the result Column sort
   * @param size
   *          the result column size
   * @throws HectorException
   *           if any other error occurs (including HNotFoundException if no value is present)
   */
  SuperColumn getSuperColumn(ByteBuffer key, ColumnPath columnPath, boolean reversed, int size)
      throws HectorException;

  /**
   * Get the group of columns contained by columnParent.
   *
   * Returns Either a ColumnFamily name or a ColumnFamily/SuperColumn specified
   * by the given predicate. If no matching values are found, an empty list is
   * returned.
   */
  List<Column> getSlice(ByteBuffer key, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException;
  
  List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException;
  
  /**
   * Get the group of counter columns contained by columnParent.
   *
   * Returns Either a ColumnFamily name or a ColumnFamily/SuperColumn specified
   * by the given predicate. If no matching values are found, an empty list is
   * returned.
   */
  List<CounterColumn> getCounterSlice(ByteBuffer key, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException;
  
  public List<CounterColumn> getCounterSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException;


  /**
   * Get the group of superColumn contained by columnParent.
   */
  List<SuperColumn> getSuperSlice(ByteBuffer key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
          SlicePredicate predicate) throws HectorException;

  /**
   * Get the group of superColumn contained by columnParent.
   */
  List<CounterSuperColumn> getCounterSuperSlice(ByteBuffer key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  List<CounterSuperColumn> getCounterSuperSlice(String key, ColumnParent columnParent,
          SlicePredicate predicate) throws HectorException;

  /**
   * Performs a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<ByteBuffer, SuperColumn> multigetSuperColumn(List<ByteBuffer> keys, ColumnPath columnPath)
      throws HectorException;

  /**
   * Perform a get for columnPath in parallel on the given list of keys.
   *
   * The return value maps keys to the ColumnOrSuperColumn found. If no value
   * corresponding to a key is present, the key will still be in the map, but
   * both the column and superColumn references of the ColumnOrSuperColumn
   * object it maps to will be null.
   */
  Map<ByteBuffer, SuperColumn> multigetSuperColumn(List<ByteBuffer> keys, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException;

  /**
   * Performs a get_slice for columnParent and predicate for the given keys in
   * parallel.
   */
  Map<ByteBuffer, List<Column>> multigetSlice(List<ByteBuffer> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;

  /**
   * Performs a get_slice for columnParent and predicate for the given keys in
   * parallel.
   */
  Map<ByteBuffer, List<CounterColumn>> multigetCounterSlice(List<ByteBuffer> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException;



  /**
   * Performs a get_slice for a superColumn columnParent and predicate for the
   * given keys in parallel.
   */
  Map<ByteBuffer, List<SuperColumn>> multigetSuperSlice(List<ByteBuffer> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws HectorException;

  /**
   * Performs a get_slice for a superColumn columnParent and predicate for the
   * given keys in parallel.
   */
  Map<ByteBuffer, List<CounterSuperColumn>> multigetCounterSuperSlice(List<ByteBuffer> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws HectorException;




  /**
   * Inserts a column.
   */
  void insert(ByteBuffer key, ColumnParent columnParent, Column column) throws HectorException;

  void insert(String key, ColumnPath columnPath, ByteBuffer value) throws HectorException;

  void insert(String key, ColumnPath columnPath, ByteBuffer value, long timestamp) throws HectorException;
  
  /**
   * Add a counter with CL.ONE
   */
  void addCounter(ByteBuffer key, ColumnParent columnParent, CounterColumn counterColumn) throws HectorException;
  
  /**
   * Add a counter with CL.ONE
   */
  void addCounter(String key, ColumnParent columnParent, CounterColumn counterColumn) throws HectorException;

  /**
   * Call batch mutate with the assembled mutationMap. This method is a direct pass-through
   * to the underlying Thrift API
   */
  void batchMutate(Map<ByteBuffer,Map<String,List<Mutation>>> mutationMap) throws HectorException;

  /**
   * Call batch mutate with the BatchMutation object which encapsulates some of the complexity
   * of the batch_mutate API signature
   */
  void batchMutate(BatchMutation batchMutation) throws HectorException;

  void remove(ByteBuffer key, ColumnPath columnPath);

/**
   * Same as two argument version, but the caller must specify their own clock
   */
  void remove(ByteBuffer key, ColumnPath columnPath, long timestamp) throws HectorException;

  void remove(String key, ColumnPath columnPath) throws HectorException;

  void remove(String key, ColumnPath columnPath, long timestamp) throws HectorException;
  
  void removeCounter(ByteBuffer key, ColumnPath columnPath) throws HectorException;
  
  void removeCounter(String key, ColumnPath columnPath) throws HectorException;


  /**
   * Counts the columns present in columnParent.
   */
  int getCount(ByteBuffer key, ColumnParent columnParent, SlicePredicate predicate) throws HectorException;

  /**
   * returns a subset of columns for a range of keys.
   */
  Map<ByteBuffer, List<Column>> getRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange) throws HectorException;


  /**
   * returns a subset of counter columns for a range of keys
   */
  public Map<ByteBuffer, List<CounterColumn>> getRangeCounterSlices(final ColumnParent columnParent,
        final SlicePredicate predicate, final KeyRange keyRange) throws HectorException;



  /**
   * returns a subset of super columns for a range of keys.
   */
  Map<ByteBuffer, List<SuperColumn>> getSuperRangeSlices(ColumnParent columnParent, SlicePredicate predicate,
      KeyRange keyRange) throws HectorException;

  /**
   * returns a subset of counter super columns for a range of keys
   */
  public Map<ByteBuffer, List<CounterSuperColumn>> getSuperRangeCounterSlices(
        final ColumnParent columnParent, final SlicePredicate predicate, final KeyRange keyRange);

  /**
   * returns a subset of columns for a range of keys.
   */
  Map<ByteBuffer, List<Column>> getIndexedSlices(ColumnParent columnParent, IndexClause indexClause,
      SlicePredicate predicate) throws HectorException;

  /**
   * Returns a map of key to column count
   */
  Map<ByteBuffer, Integer> multigetCount(List<ByteBuffer> keys, ColumnParent columnParent,
      SlicePredicate slicePredicate) throws HectorException;

  /**
   * @return The consistency level held by this keyspace instance.
   */
  HConsistencyLevel getConsistencyLevel(OperationType operationType);

  String getName();

  CassandraHost getCassandraHost();
}
