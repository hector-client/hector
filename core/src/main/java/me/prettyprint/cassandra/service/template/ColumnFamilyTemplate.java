package me.prettyprint.cassandra.service.template;

import java.util.List;

import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.QueryResult;

/**
 * This applies a Template Method pattern, much like Spring's JdbcTemplate, to
 * Cassandra. The ColumnFamilyTemplate instance maintains many of the fields in
 * common between various query/update operations so that they do not need to be
 * constantly passed for every operation on the column family. These include the
 * keyspace, column family name, key serializer, and the column name serializer
 * (for standard column name or the super column name).
 * 
 * The Java generic types of the ColumnFamilyTemplate class itself are limited to
 * the key and column name type. It defers the generic types for super column
 * child types to the individual update/query operation.
 * 
 * @author david
 * @author zznate
 * @param <K>
 *          The column family key type
 * @param <N>
 *          The column family name type 
 */
public abstract class ColumnFamilyTemplate<K, N> extends AbstractColumnFamilyTemplate<K, N> {
  
  public ColumnFamilyTemplate(Keyspace keyspace, String columnFamily,
      Serializer<K> keySerializer, Serializer<N> topSerializer) {
    super(keyspace, columnFamily, keySerializer, topSerializer);
  }


  // Just so method chaining will return this type instead of the parent class
  // for operations down the chain
  public ColumnFamilyTemplate<K, N> setBatched(boolean batched) {
    super.setBatched(batched);
    return this;
  }

  public ColumnFamilyUpdater<K, N> createUpdater() {
    ColumnFamilyUpdater<K, N> updater = new ColumnFamilyUpdater<K, N>(this, columnFactory);
    return updater;
  }

  public ColumnFamilyUpdater<K, N> createUpdater(K key) {
    ColumnFamilyUpdater<K, N> updater = new ColumnFamilyUpdater<K, N>(this, columnFactory);
    updater.addKey(key);
    return updater;
  }

  public ColumnFamilyUpdater<K, N> createUpdater(K key, Mutator<K> mutator) {
    ColumnFamilyUpdater<K, N> updater = new ColumnFamilyUpdater<K, N>(this, columnFactory, mutator);
    updater.addKey(key);
    return updater;
  }

  public void update(ColumnFamilyUpdater<K, N> updater) {
    updater.update();
    executeIfNotBatched(updater);
  }

  /**
   * Checks if there are any columns at a row specified by key in a standard
   * column family
   * 
   * @param key
   * @return true if columns exist
   */
  public boolean isColumnsExist(K key) {
    return countColumns(key) > 0;
  }

  /**
   * @param key
   * @return the number of columns in a standard column family at the specified
   *         row key
   */
  @SuppressWarnings("unchecked")
  public int countColumns(K key) {
    return countColumns(key, (N) ALL_COLUMNS_START, (N) ALL_COLUMNS_END,
        ALL_COLUMNS_COUNT);
  }

  /**
   * Counts columns in the specified range of a standard column family
   * 
   * @param key
   * @param start
   * @param end
   * @param max
   * @return
   */
  public int countColumns(K key, N start, N end, int max) {
    CountQuery<K, N> query = HFactory.createCountQuery(keyspace, keySerializer,
        topSerializer);
    query.setKey(key);
    query.setColumnFamily(columnFamily);
    query.setRange(start, end, max);
    return query.execute().get();
  }

  public ColumnFamilyResult<K, N> queryColumns(K key) {
    return doExecuteSlice(key, activeSlicePredicate);
  }

  public ColumnFamilyResult<K, N> queryColumns(Iterable<K> keys) {
    return doExecuteMultigetSlice(keys, activeSlicePredicate);
  }

  public <T> T queryColumns(K key, ColumnFamilyRowMapper<K, N, T> mapper) {
    return queryColumns(key, activeSlicePredicate, mapper);
  }

  /**
   * Queries a range of columns at the given key and maps them to an object of
   * type OBJ using the given mapping object
   * 
   * @param <T>
   * @param key
   * @param predicate The {@link HSlicePredicate} which can hold specific column names
   * or a range of columns
   * @param mapper
   * @return
   */
  public <T> T queryColumns(K key, HSlicePredicate<N> predicate,
      ColumnFamilyRowMapper<K, N, T> mapper) {
    return doExecuteSlice(key, predicate, mapper);
  }

  /**
   * Queries all columns at a given key and maps them to an object of type OBJ
   * using the given mapping object
   * 
   * @param <T>
   * @param key
   * @param columns
   * @param mapper
   * @return
   */
  public <T> T queryColumns(K key, List<N> columns,
      ColumnFamilyRowMapper<K, N, T> mapper) {
    HSlicePredicate<N> predicate = new HSlicePredicate<N>(topSerializer);
    predicate.setColumnNames(columns);        
    return doExecuteSlice(key, predicate, mapper);
  }

  public ColumnFamilyResult<K, N> queryColumns(K key, List<N> columns) {
    HSlicePredicate<N> predicate = new HSlicePredicate<N>(topSerializer);
    predicate.setColumnNames(columns);        
    return doExecuteSlice(key, predicate);
  }

  public ColumnFamilyResult<K, N> queryColumns(Iterable<K> keys, List<N> columns) {
    HSlicePredicate<N> predicate = new HSlicePredicate<N>(topSerializer);
    predicate.setColumnNames(columns);        
    return doExecuteMultigetSlice(keys, predicate);
  }

  public ColumnFamilyResult<K, N> queryColumns(K key, HSlicePredicate<N> predicate) {
    return doExecuteSlice(key, predicate);
  }

  public <V> MappedColumnFamilyResult<K,N,V> queryColumns(Iterable<K> keys,
      ColumnFamilyRowMapper<K, N, V> mapper) {    
    return doExecuteMultigetSlice(keys, activeSlicePredicate, mapper);
  }

  public <V> MappedColumnFamilyResult<K,N,V> queryColumns(Iterable<K> keys,
      HSlicePredicate<N> predicate, ColumnFamilyRowMapper<K, N, V> mapper) {     
    return doExecuteMultigetSlice(keys, predicate, mapper);
  }

  public <V> MappedColumnFamilyResult<K,N,V> queryColumns(Iterable<K> keys,
      List<N> columns, ColumnFamilyRowMapper<K, N, V> mapper) {
    HSlicePredicate<N> predicate = new HSlicePredicate<N>(topSerializer);
    predicate.setColumnNames(columns);
    return doExecuteMultigetSlice(keys, predicate, mapper);
  }

  public <V> ColumnFamilyResult<K,N> queryColumns(IndexedSlicesPredicate<K,N,V> predicate) {
    return doExecuteIndexedSlices(predicate);
  }
  public <V> ColumnFamilyResult<K,N> queryColumns(IndexedSlicesPredicate<K,N,V> predicate, HSlicePredicate<N> slicePredicate) {
    return doExecuteIndexedSlices(predicate, slicePredicate);
  }
  public <V> ColumnFamilyResult<K,N> queryColumns(IndexedSlicesPredicate<K,N,V> predicate, List<N> columns) {
    HSlicePredicate<N> slicePredicate = new HSlicePredicate<N>(topSerializer);
    slicePredicate.setColumnNames(columns);
    return doExecuteIndexedSlices(predicate, slicePredicate);
  }
  public <R,V> MappedColumnFamilyResult<K,N,R> queryColumns(IndexedSlicesPredicate<K,N,V> predicate, ColumnFamilyRowMapper<K, N, R> mapper) {
    return doExecuteIndexedSlices(predicate, mapper);
  }
  public <R,V> MappedColumnFamilyResult<K,N,R> queryColumns(IndexedSlicesPredicate<K,N,V> predicate, HSlicePredicate<N> slicePredicate, ColumnFamilyRowMapper<K, N, R> mapper) {
    return doExecuteIndexedSlices(predicate, slicePredicate, mapper);
  }
  public <R,V> MappedColumnFamilyResult<K,N,R> queryColumns(IndexedSlicesPredicate<K,N,V> predicate, List<N> columns, ColumnFamilyRowMapper<K, N, R> mapper) {
    HSlicePredicate<N> slicePredicate = new HSlicePredicate<N>(topSerializer);
    slicePredicate.setColumnNames(columns);
    return doExecuteIndexedSlices(predicate, slicePredicate, mapper);
  }

  @SuppressWarnings("unchecked")
  public <V> HColumn<N, V> querySingleColumn(K key, N columnName,
      Class<V> valueClass) {
    return querySingleColumn(key, columnName,
        (Serializer<V>) SerializerTypeInferer.getSerializer(valueClass));
  }

  public <V> HColumn<N, V> querySingleColumn(K key, N columnName,
      Serializer<V> valueSerializer) {
    ColumnQuery<K, N, V> query = HFactory.createColumnQuery(keyspace,
        keySerializer, topSerializer, valueSerializer);
    query.setColumnFamily(columnFamily);
    query.setKey(key);
    query.setName(columnName);
    QueryResult<HColumn<N, V>> result = query.execute();
    return result != null ? result.get() : null;
  }

  //-------------------------- delegation methods ----------------------------

  protected abstract <T> T doExecuteSlice(K key, HSlicePredicate<N> predicate, ColumnFamilyRowMapper<K, N, T> mapper);

  protected abstract ColumnFamilyResult<K, N> doExecuteSlice(final K key, final HSlicePredicate<N> workingSlicePredicate);

  protected abstract ColumnFamilyResult<K, N> doExecuteMultigetSlice(final Iterable<K> keys, final HSlicePredicate<N> workingSlicePredicate);

  protected abstract <V> MappedColumnFamilyResult<K, N, V> doExecuteMultigetSlice(final Iterable<K> keys, 
      final HSlicePredicate<N> workingSlicePredicate,
      final ColumnFamilyRowMapper<K, N, V> mapper);

  protected abstract <V> ColumnFamilyResult<K, N> doExecuteIndexedSlices(final IndexedSlicesPredicate<K, N, V> predicate);
  protected abstract <V> ColumnFamilyResult<K, N> doExecuteIndexedSlices(final IndexedSlicesPredicate<K, N, V> predicate,
      final HSlicePredicate<N> slicePredicate);
  protected abstract <R,V> MappedColumnFamilyResult<K, N, R> doExecuteIndexedSlices(final IndexedSlicesPredicate<K,N,V> predicate,
      final ColumnFamilyRowMapper<K, N, R> mapper);
  protected abstract <R,V> MappedColumnFamilyResult<K, N, R> doExecuteIndexedSlices(final IndexedSlicesPredicate<K,N,V> predicate,
      final HSlicePredicate<N> slicePredicate,
      final ColumnFamilyRowMapper<K, N, R> mapper);

}
