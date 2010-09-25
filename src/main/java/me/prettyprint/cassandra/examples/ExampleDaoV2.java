package me.prettyprint.cassandra.examples;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

public class ExampleDaoV2 {

  private final static String KEYSPACE = "Keyspace1";
  private final static String HOST_PORT = "localhost:9170";
  private final static String CF_NAME = "Standard1";
  /** Column name where values are stored */
  private final static String COLUMN_NAME = "v";
  private final StringSerializer serializer = StringSerializer.get();

  private final Keyspace keyspace;

  public static void main(String[] args) throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", HOST_PORT);
    ExampleDaoV2 ed = new ExampleDaoV2(createKeyspace(KEYSPACE, c));
    ed.insert("key1", "value1", StringSerializer.get());

    System.out.println(ed.get("key1", StringSerializer.get()));
  }

  public ExampleDaoV2(Keyspace keyspace) {
    this.keyspace = keyspace;
  }

  /**
   * Insert a new value keyed by key
   *
   * @param key   Key for the value
   * @param value the String value to insert
   */
  public <K> void insert(final K key, final String value, Serializer<K> keySerializer) {
    createMutator(keyspace, keySerializer).insert(
        key, CF_NAME, createColumn(COLUMN_NAME, value, serializer, serializer));
  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public <K> String get(final K key, Serializer<K> keySerializer) throws HectorException {
    ColumnQuery<K, String, String> q = createColumnQuery(keyspace, keySerializer, serializer, serializer);
    QueryResult<HColumn<String, String>> r = q.setKey(key).
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
  public <K> Map<K, String> getMulti(Serializer<K> keySerializer, K... keys) {
    MultigetSliceQuery<K, String,String> q = createMultigetSliceQuery(keyspace, keySerializer, serializer, serializer);
    q.setColumnFamily(CF_NAME);
    q.setKeys(keys);
    q.setColumnNames(COLUMN_NAME);

    QueryResult<Rows<K, String,String>> r = q.execute();
    Rows<K, String,String> rows = r.get();
    Map<K, String> ret = new HashMap<K, String>(keys.length);
    for (K k: keys) {
      HColumn<String,String> c = rows.getByKey(k).getColumnSlice().getColumnByName(COLUMN_NAME);
      if (c != null && c.getValue() != null) {
        ret.put(k, c.getValue());
      }
    }
    return ret;
  }

  /**
   * Insert multiple values
   */
  public <K> void insertMulti(Map<K, String> keyValues, Serializer<K> keySerializer) {
    Mutator<K> m = createMutator(keyspace, keySerializer);
    for (Map.Entry<K, String> keyValue: keyValues.entrySet()) {
      m.addInsertion(keyValue.getKey(), CF_NAME,
          createColumn(COLUMN_NAME, keyValue.getValue(), keyspace.createClock(), serializer, serializer));
    }
    m.execute();
  }

  /**
   * Delete multiple values
   */
  public <K> void delete(Serializer<K> keySerializer, K... keys) {
    Mutator<K> m = createMutator(keyspace, keySerializer);
    for (K key: keys) {
      m.addDeletion(key, CF_NAME,  COLUMN_NAME, serializer);
    }
    m.execute();
  }
}
