package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.beans.SuperRow;
import me.prettyprint.hector.api.beans.SuperRows;
import me.prettyprint.hector.api.beans.SuperSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SubColumnQuery;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SuperCountQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

public class SuperCfTemplate<K, SN, N> extends AbstractColumnFamilyTemplate<K, SN> {
  private Serializer<N> subSerializer;

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
  public <VAL> HColumn<N, VAL> querySingleSubColumn(K key,
      SN columnName, N subColumnName, Class<VAL> valueClass) {
    return querySingleSubColumn(key, columnName, subColumnName,
        (Serializer<VAL>) SerializerTypeInferer.getSerializer(valueClass));
  }

  public <VAL> HColumn<N, VAL> querySingleSubColumn(K key,
      SN columnName, N subColumnName, Serializer<VAL> valueSerializer) {
    SubColumnQuery<K, SN, N, VAL> query = HFactory
        .createSubColumnQuery(keyspace, keySerializer, topSerializer,
            subSerializer, valueSerializer);
    query.setColumnFamily(columnFamily);
    query.setKey(key);
    query.setSuperColumn(columnName);
    query.setColumn(subColumnName);
    QueryResult<HColumn<N, VAL>> result = query.execute();
    return result != null ? result.get() : null;
  }

  /**
   * Writes a series of object in a row of a super column family. Each object's
   * properties are written to a super-column's child columns
   * 
   * @param <OBJ>
   *          the type of Object to be persisted
   * @param <N>
   *          the type of the sub column name
   * @param key
   *          the row key
   * @param objects
   *          list of objects to write to the db
   * @param subSerializer
   *          the serializer for the child columns in the super column
   * @param updater
   *          the object which performs updates on a
   */
  public <OBJ> void update(K key, List<OBJ> objects,
      SuperCfUpdater<K, SN, N, OBJ> updater) {
    if (objects == null || objects.size() == 0) {
      return;
    }

    updater.key = key;
    updater.subSerializer = subSerializer;

    for (OBJ obj : objects) {
      updater.columns = new ArrayList<HColumn<N, ByteBuffer>>();

      SN columnName = updater.update(obj);

      HSuperColumn<SN, N, ByteBuffer> superColumn = HFactory
          .createSuperColumn(columnName, updater.columns, topSerializer,
              subSerializer, ByteBufferSerializer.get());
      mutator.addInsertion(key, columnFamily, superColumn);

      if (updater.columnsToDelete != null) {
        HSuperColumn<SN, N, ByteBuffer> superColumnWithDeletes = HFactory
            .createSuperColumn(columnName, updater.columnsToDelete,
                topSerializer, subSerializer, ByteBufferSerializer.get());
        mutator.addSubDelete(key, columnFamily, superColumnWithDeletes);
        updater.columnsToDelete = null;
      }
    }
    executeIfNotBatched();
  }

  @SuppressWarnings("unchecked")
  public <OBJ> List<OBJ> querySuperColumns(K key,
      SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    return querySuperColumns(key, (SN) ALL_COLUMNS_START,
        (SN) ALL_COLUMNS_END, mapper);
  }

  public <OBJ> List<OBJ> querySuperColumns(K key, SN start, SN end,
      SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    SuperSliceQuery<K, SN, N, ByteBuffer> query = createSuperSliceQuery(key);
    query.setRange(start, end, false, ALL_COLUMNS_COUNT);
    return executeSuperSliceQuery(key, query, mapper);
  }

  @SuppressWarnings("unchecked")
  public <OBJ> List<OBJ> querySuperColumns(K key, List<SN> columns,
      SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    SuperSliceQuery<K, SN, N, ByteBuffer> query = createSuperSliceQuery(key);
    query.setColumnNames((SN[]) columns.toArray());
    return executeSuperSliceQuery(key, query, mapper);
  }

  private <OBJ> List<OBJ> executeSuperSliceQuery(K key,
      SuperSliceQuery<K, SN, N, ByteBuffer> query,
      SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    QueryResult<SuperSlice<SN, N, ByteBuffer>> result = query.execute();
    SuperSlice<SN, N, ByteBuffer> slice = result.get();
    if (slice.getSuperColumns() != null && slice.getSuperColumns().size() > 0) {
      List<OBJ> ret = new ArrayList<OBJ>();
      for (HSuperColumn<SN, N, ByteBuffer> superCol : slice
          .getSuperColumns()) {
        /*SuperCfResultWrapper wrapper = new SuperCfResultWrapper(key, superCol);
        ret.add(mapper.mapRow(wrapper));*/
      }
      return ret;
    }
    return null;
  }

  private SuperSliceQuery<K, SN, N, ByteBuffer> createSuperSliceQuery(
      K key) {
    SuperSliceQuery<K, SN, N, ByteBuffer> query = HFactory
        .createSuperSliceQuery(keyspace, keySerializer, topSerializer,
            subSerializer, ByteBufferSerializer.get());
    query.setKey(key);
    query.setColumnFamily(columnFamily);
    return query;
  }

  @SuppressWarnings("unchecked")
  public <OBJ> ColumnFamilyResultsIterator<K, List<OBJ>> querySuperColumns(
      List<K> key, SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    return querySuperColumns(key, (SN) ALL_COLUMNS_START,
        (SN) ALL_COLUMNS_END, mapper);
  }

  public <OBJ> ColumnFamilyResultsIterator<K, List<OBJ>> querySuperColumns(
      List<K> key, SN start, SN end,
      SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    MultigetSuperSliceQuery<K, SN, N, ByteBuffer> query = createMultigetSuperSliceQuery(key);
    query.setRange(start, end, false, ALL_COLUMNS_COUNT);
    return executeMultigetSuperSliceQuery(key, query, mapper);
  }

  @SuppressWarnings("unchecked")
  public <OBJ> ColumnFamilyResultsIterator<K, List<OBJ>> querySuperColumns(
      List<K> key, List<SN> columns,
      SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    MultigetSuperSliceQuery<K, SN, N, ByteBuffer> query = createMultigetSuperSliceQuery(key);
    query.setColumnNames((SN[]) columns.toArray());
    return executeMultigetSuperSliceQuery(key, query, mapper);
  }

  private <OBJ> ColumnFamilyResultsIterator<K, List<OBJ>> executeMultigetSuperSliceQuery(
      final List<K> key,
      MultigetSuperSliceQuery<K, SN, N, ByteBuffer> query,
      final SuperCfRowMapper<K, SN, N, OBJ> mapper) {
    QueryResult<SuperRows<K, SN, N, ByteBuffer>> result = query
        .execute();
    final SuperRows<K, SN, N, ByteBuffer> rows = result.get();

    final Iterator<SuperRow<K, SN, N, ByteBuffer>> iter = rows
        .iterator();
    return new ColumnFamilyResultsIterator<K, List<OBJ>>() {
      public boolean hasNext() {
        return iter.hasNext();
      }

      public List<OBJ> next() {
        return mapRow(iter.next());
      }

      public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
      }

      public List<OBJ> getByKey(K key) {
        return mapRow(rows.getByKey(key));
      }

      public int getCount() {
        return rows.getCount();
      }

      private List<OBJ> mapRow(SuperRow<K, SN, N, ByteBuffer> row) {
        SuperSlice<SN, N, ByteBuffer> slice = row.getSuperSlice();
        List<OBJ> ret = new ArrayList<OBJ>();
        for (HSuperColumn<SN, N, ByteBuffer> superCol : slice
            .getSuperColumns()) {
/*          SuperCfResultWrapper wrapper = new SuperCfResultWrapper(row.getKey(),
              superCol);
          ret.add(mapper.mapRow(wrapper));*/
        }
        return ret;
      }
    };
  }

  @SuppressWarnings("unchecked")
  private MultigetSuperSliceQuery<K, SN, N, ByteBuffer> createMultigetSuperSliceQuery(
      List<K> keys) {
    MultigetSuperSliceQuery<K, SN, N, ByteBuffer> query = HFactory
        .createMultigetSuperSliceQuery(keyspace, keySerializer, topSerializer,
            subSerializer, ByteBufferSerializer.get());
    query.setKeys((K[]) keys.toArray());
    query.setColumnFamily(columnFamily);
    return query;
  }

}
