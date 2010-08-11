package me.prettyprint.cassandra.dao;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createMultigetSliceQuery;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.createStringColumnQuery;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.extractors.StringExtractor;
import me.prettyprint.cassandra.model.ColumnQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.MultigetSliceQuery;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Rows;

public class SimpleCassandraDao {

  private String columnFamilyName;
  private String keyspaceName;
  private KeyspaceOperator keyspaceOperator;
  private StringExtractor extractor = StringExtractor.get();
    
  /**
   * Insert a new value keyed by key
   *
   * @param key   Key for the value
   * @param value the String value to insert
   */
  public void insert(final byte[] key, final String columnName, final String value) {
    createMutator(keyspaceOperator).insert(
        key, columnFamilyName, createColumn(columnName, value, extractor, extractor));
  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public String get(final byte[] key, final String columnName) throws HectorException {
    ColumnQuery<String, String> q = createStringColumnQuery(keyspaceOperator);
    Result<HColumn<String, String>> r = q.setKey(key).
        setName(columnName).
        setColumnFamily(columnFamilyName).
        execute();
    HColumn<String, String> c = r.get();
    return c != null ? c.getValue() : null;
  }

  /**
   * Get multiple values
   * @param keys
   * @return
   */
  public Map<byte[], String> getMulti(String columnName, byte[]... keys) {
    MultigetSliceQuery<String,String> q = createMultigetSliceQuery(keyspaceOperator, extractor, extractor);
    q.setColumnFamily(columnFamilyName);
    q.setKeys(keys);
    q.setColumnNames(columnName);

    Result<Rows<String,String>> r = q.execute();
    Rows<String,String> rows = r.get();
    Map<byte[], String> ret = new HashMap<byte[], String>(keys.length);
    for (byte[] k: keys) {
      HColumn<String,String> c = rows.getByKey(k).getColumnSlice().getColumnByName(columnName);
      if (c != null && c.getValue() != null) {
        ret.put(k, c.getValue());
      }
    }
    return ret;
  }

  /**
   * Insert multiple values for a given columnName
   */
  public void insertMulti(String columnName, Map<byte[], String> keyValues) {
    Mutator m = createMutator(keyspaceOperator);
    for (Map.Entry<byte[], String> keyValue: keyValues.entrySet()) {
      m.addInsertion(keyValue.getKey(), columnFamilyName,
          createColumn(columnName, keyValue.getValue(), keyspaceOperator.createClock(), extractor, extractor));
    }
    m.execute();
  }
  

  /**
   * Delete multiple values
   */
  public void delete(String columnName, byte[]... keys) {
    Mutator m = createMutator(keyspaceOperator);
    for (byte[] key: keys) {
      m.addDeletion(key, columnFamilyName,  columnName, extractor);
    }
    m.execute();
  }

  public void setColumnFamilyName(String columnFamilyName) {
    this.columnFamilyName = columnFamilyName;
  }

  public void setKeyspaceName(String keyspaceName) {
    this.keyspaceName = keyspaceName;
  }

  public void setKeyspaceOperator(KeyspaceOperator keyspaceOperator) {
    this.keyspaceOperator = keyspaceOperator;
  }
  
  
}
