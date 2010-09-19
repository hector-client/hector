package me.prettyprint.cassandra.examples;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspaceOperator;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;

public class ExampleDaoV2 {

  private final static String KEYSPACE = "Keyspace1";
  private final static String HOST_PORT = "localhost:9170";
  private final static String CF_NAME = "Standard1";
  /** Column name where values are stored */
  private final static String COLUMN_NAME = "v";
  private final StringSerializer serializer = StringSerializer.get();

  private final KeyspaceOperator keyspaceOperator;

  public static void main(String[] args) throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", HOST_PORT);
    ExampleDaoV2 ed = new ExampleDaoV2(createKeyspaceOperator(KEYSPACE, c));
    ed.insert("key1", "value1");

    System.out.println(ed.get("key1"));
  }

  public ExampleDaoV2(KeyspaceOperator ko) {
    keyspaceOperator = ko;
  }

  /**
   * Insert a new value keyed by key
   *
   * @param key   Key for the value
   * @param value the String value to insert
   */
  public void insert(final String key, final String value) {
    createMutator(keyspaceOperator).insert(
        key, CF_NAME, createColumn(COLUMN_NAME, value, serializer, serializer));
  }

  private long createTimestamp() {
    return keyspaceOperator.createTimestamp();
  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public String get(final String key) throws HectorException {
    ColumnQuery<String, String> q = createColumnQuery(keyspaceOperator, serializer, serializer);
    Result<HColumn<String, String>> r = q.setKey(key).
        setName(COLUMN_NAME).
        setColumnFamily(CF_NAME).
        execute();
    HColumn<String, String> c = r.get();
    return c == null ? null : c.getValue();
  }

  /**
   * Get multiple values
   * @param keys
   * @return
   */
  public Map<String, String> getMulti(String... keys) {
    MultigetSliceQuery<String,String> q = createMultigetSliceQuery(keyspaceOperator, serializer, serializer);
    q.setColumnFamily(CF_NAME);
    q.setKeys(keys);
    q.setColumnNames(COLUMN_NAME);

    Result<Rows<String,String>> r = q.execute();
    Rows<String, String> rows = r.get();
    Map<String, String> ret = new HashMap<String, String>(keys.length);
    for (String k: keys) {
      HColumn<String, String> c = rows.getByKey(k).getColumnSlice().getColumnByName(COLUMN_NAME);
      if (c != null && c.getValue() != null) {
        ret.put(k, c.getValue());
      }
    }
    return ret;
  }

  /**
   * Insert multiple values
   */
  public void insertMulti(Map<String, String> keyValues) {
    Mutator m = createMutator(keyspaceOperator);
    for (Map.Entry<String, String> keyValue: keyValues.entrySet()) {
      m.addInsertion(keyValue.getKey(), CF_NAME,
          createColumn(COLUMN_NAME, keyValue.getValue(), createTimestamp(), serializer, serializer));
    }
    m.execute();
  }

  /**
   * Delete multiple values
   */
  public void delete(String... keys) {
    Mutator m = createMutator(keyspaceOperator);
    for (String key: keys) {
      m.addDeletion(key, CF_NAME,  COLUMN_NAME, serializer);
    }
    m.execute();
  }
}
