package me.prettyprint.cassandra.service.template;

import java.util.List;

import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SuperCountQuery;

public abstract class SuperCfTemplate<K, SN, N> extends AbstractColumnFamilyTemplate<K, SN> {
  protected Serializer<N> subSerializer;

  public SuperCfTemplate(Keyspace keyspace, String columnFamily,
      Serializer<K> keySerializer, Serializer<SN> topSerializer,
      Serializer<N> subSerializer) {
    super(keyspace, columnFamily, keySerializer, topSerializer);
    this.subSerializer = subSerializer;
  }

  public SuperCfTemplate(Keyspace keyspace, String columnFamily,
      Serializer<K> keySerializer, Serializer<SN> topSerializer,
      Serializer<N> subSerializer, Mutator<K> mutator) {
    super(keyspace, columnFamily, keySerializer, topSerializer, mutator);
    this.subSerializer = subSerializer;
  }

  public Serializer<N> getSubSerializer() {
    return subSerializer;
  }
  
  /**
   * Checks if there are any columns at a row specified by key in a super column
   * family
   * 
   * @param key
   * @return true if columns exist
   */
  public boolean isColumnsExist(K key) {
    return countColumns(key) > 0;
  }

  /**
   * Checks if there are any columns at a row specified by key in a specific
   * super column
   * 
   * @param key
   * @param superColumnName
   * @param subSerializer
   *          the column name serializer of the child columns
   * @return true if columns exist
   */
  public boolean isSubColumnsExist(K key, SN superColumnName) {
    return countSubColumns(key, superColumnName) > 0;
  }

  /**
   * @param key
   * @return the number of columns in a super column family at the specified row
   *         key
   */
  @SuppressWarnings("unchecked")
  public int countColumns(K key) {
    return countColumns(key, (SN) ALL_COLUMNS_START, (SN) ALL_COLUMNS_END,
        ALL_COLUMNS_COUNT);
  }

  /**
   * 
   * @param key
   * @param superColumnName
   * @return the number of child columns in a specified super column
   */
  @SuppressWarnings("unchecked")
  public int countSubColumns(K key, SN superColumnName) {
    return countSubColumns(key, superColumnName, (N) ALL_COLUMNS_START,
        (N) ALL_COLUMNS_END, ALL_COLUMNS_COUNT);
  }

  /**
   * Counts columns in the specified range of a super column family
   * 
   * @param key
   * @param start
   * @param end
   * @param max
   * @return
   */
  public int countColumns(K key, SN start, SN end, int max) {
    SuperCountQuery<K, SN> query = HFactory.createSuperCountQuery(keyspace,
        keySerializer, topSerializer);
    query.setKey(key);
    query.setColumnFamily(columnFamily);
    query.setRange(start, end, max);
    return query.execute().get();
  }

  /**
   * Counts child columns in the specified range of a children in a specified
   * super column
   * 
   * @param <SUBCOL>
   * @param key
   * @param superColumnName
   * @param start
   * @param end
   * @param max
   * @param subSerializer
   * @return
   */
  public int countSubColumns(K key, SN superColumnName, N start,
      N end, int max) {
    SubCountQuery<K, SN, N> query = HFactory.createSubCountQuery(
        keyspace, keySerializer, topSerializer, subSerializer);
    query.setKey(key);
    query.setColumnFamily(columnFamily);
    query.setSuperColumn(superColumnName);
    query.setRange(start, end, max);
    return query.execute().get();
  }

  @SuppressWarnings("unchecked")
  public <V> HColumn<N, V> querySingleSubColumn(K key,
      SN columnName, N subColumnName, Class<V> valueClass) {
    return null;
  }
  
  

  public <VAL> HColumn<N, VAL> querySingleSubColumn(K key,
      SN columnName, N subColumnName, Serializer<VAL> valueSerializer) {
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public <T> List<T> querySuperColumns(K key,
      SuperCfRowMapper<K, SN, N, T> mapper) {
    return null;
  }

  public <T> List<T> querySuperColumns(K key, HSlicePredicate<SN> predicate,
      SuperCfRowMapper<K, SN, N, T> mapper) {
    return null;
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> querySuperColumns(K key, List<SN> columns,
      SuperCfRowMapper<K, SN, N, T> mapper) {
    return null;
  }
  
  public SuperCfUpdater<K, SN, N> createUpdater(K key, SN sColumnName) {
    SuperCfUpdater<K, SN, N> updater = new SuperCfUpdater<K, SN, N>(this, columnFactory);
    updater.addKey(key);
    updater.addSuperColumn(sColumnName);
    return updater;
  }

  public void update(SuperCfUpdater<K, SN, N> updater) {
    updater.updateInternal();
    updater.update();
    executeIfNotBatched();
  }
  
  public SuperCfResult<K, SN, N> querySuperColumn(K key, SN sColumnName) {
    return doExecuteSlice(key, sColumnName, activeSlicePredicate);
  }
  
  protected abstract SuperCfResult<K,SN,N> doExecuteSlice(K key, SN sColumnName, HSlicePredicate<SN> predicate);

}
