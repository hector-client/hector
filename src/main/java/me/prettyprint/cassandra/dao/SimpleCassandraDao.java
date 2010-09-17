package me.prettyprint.cassandra.dao;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Rows;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;

public class SimpleCassandraDao {

  private String columnFamilyName;
  private KeyspaceOperator keyspaceOperator;
  private final StringSerializer serializer = StringSerializer.get();

  /**
   * Insert a new value keyed by key
   *
   * @param key   Key for the value
   * @param value the String value to insert
   */
  public void insert(final String key, final String columnName, final String value) {
    createMutator(keyspaceOperator, serializer).insert(
        key, columnFamilyName, createColumn(columnName, value, serializer, serializer));

  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public String get(final String key, final String columnName) throws HectorException {
    ColumnQuery<String, String, String> q = HFactory.createColumnQuery(keyspaceOperator,
        serializer, serializer, serializer);
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
  public Map<String, String> getMulti(String columnName, String... keys) {
    MultigetSliceQuery<String, String,String> q = createMultigetSliceQuery(keyspaceOperator, serializer, serializer, serializer);
    q.setColumnFamily(columnFamilyName);
    q.setKeys(keys);
    q.setColumnNames(columnName);

    Result<Rows<String,String,String>> r = q.execute();
    Rows<String,String,String> rows = r.get();
    Map<String, String> ret = new HashMap<String, String>(keys.length);
    for (String k: keys) {
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
  public void insertMulti(String columnName, Map<String, String> keyValues) {
    Mutator<String> m = createMutator(keyspaceOperator, serializer);
    for (Map.Entry<String, String> keyValue: keyValues.entrySet()) {
      m.addInsertion(keyValue.getKey(), columnFamilyName,
          createColumn(columnName, keyValue.getValue(), keyspaceOperator.createClock(), serializer, serializer));
    }
    m.execute();
  }


  /**
   * Delete multiple values
   */
  public void delete(String columnName, String... keys) {
    Mutator<String> m = createMutator(keyspaceOperator, serializer);
    for (String key: keys) {
      m.addDeletion(key, columnFamilyName,  columnName, serializer);
    }
    m.execute();
  }

  public void setColumnFamilyName(String columnFamilyName) {
    this.columnFamilyName = columnFamilyName;
  }


  public void setKeyspaceOperator(KeyspaceOperator keyspaceOperator) {
    this.keyspaceOperator = keyspaceOperator;
  }


}
