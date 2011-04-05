package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import me.prettyprint.cassandra.model.HColumnImpl;
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


  
  public <V> HColumn<N, V> querySingleSubColumn(K key,
      SN columnName, N subColumnName, Serializer<V> valueSerializer) {
    
    SuperCfResult<K,SN,N> result = doExecuteSlice(key, columnName, activeSlicePredicate);        
    HColumn<N,ByteBuffer> origCol = result.getColumn(subColumnName);
    // TODO make this far less hacky
    if ( columnName == null || origCol == null ) {
      return null;
    }
    return new HColumnImpl<N, V>(subColumnName, 
        valueSerializer.fromByteBuffer(origCol.getValue()), origCol.getClock(), 
        subSerializer, valueSerializer);
  }
  
  @SuppressWarnings("unchecked")
  public SuperCfResult<K, SN, N> querySuperColumns(K key, List<SN> sColumnNames) {
    HSlicePredicate<SN> workingSlicePredicate = new HSlicePredicate<SN>(topSerializer);
    workingSlicePredicate.setColumnNames(sColumnNames);
    return doExecuteSlice(key, null, workingSlicePredicate);    
  }

  @SuppressWarnings("unchecked")
  public SuperCfResult<K, SN, N> querySuperColumns(List<K> keys, List<SN> sColumnNames) {
    HSlicePredicate<SN> workingSlicePredicate = new HSlicePredicate<SN>(topSerializer);
    workingSlicePredicate.setColumnNames(sColumnNames);
    return doExecuteMultigetSlice(keys, workingSlicePredicate);    
  }

  public <T> T querySuperColumns(K key, List<SN> sColumnNames,
      SuperCfRowMapper<K, SN, N, T> mapper) {
    
    HSlicePredicate<SN> workingSlicePredicate = new HSlicePredicate<SN>(topSerializer);
    workingSlicePredicate.setColumnNames(sColumnNames);
    return mapper.mapRow(doExecuteSlice(key, null, workingSlicePredicate));
  }

  public SuperCfResult<K, SN, N> querySuperColumn(K key, SN sColumnName) {
    HSlicePredicate<SN> workingSlicePredicate = new HSlicePredicate<SN>(topSerializer);
    workingSlicePredicate.addColumnName(sColumnName);
    return doExecuteSlice(key, sColumnName, workingSlicePredicate);
  }
  
  public SuperCfUpdater<K, SN, N> createUpdater(K key, SN sColumnName) {    
    return createUpdater(key).addSuperColumn(sColumnName);
  }

  public SuperCfUpdater<K, SN, N> createUpdater(K key) {
    SuperCfUpdater<K, SN, N> updater = new SuperCfUpdater<K, SN, N>(this, columnFactory);
    updater.addKey(key);
    return updater;
  }
  
  public void update(SuperCfUpdater<K, SN, N> updater) {
    updater.updateInternal();
    updater.update();
    executeIfNotBatched();
  }
  

  
  protected abstract SuperCfResult<K,SN,N> doExecuteSlice(K key, SN sColumnName, HSlicePredicate<SN> predicate);
  
  protected abstract SuperCfResult<K,SN,N> doExecuteMultigetSlice(List<K> keys, HSlicePredicate<SN> predicate);
  

}
