package me.prettyprint.cassandra.examples;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createColumnQuery;
import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.createMultigetSliceQuery;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;

import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.thrift.Clock;

import me.prettyprint.cassandra.extractors.StringExtractor;
import me.prettyprint.cassandra.model.ColumnQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.MultigetSliceQuery;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Rows;
import me.prettyprint.cassandra.service.Cluster;

public class ExampleDaoV2 {

  private final static String KEYSPACE = "Keyspace1";
  private final static String HOST_PORT = "localhost:9170";
  private final static String CF_NAME = "Standard1";
  /** Column name where values are stored */
  private final static String COLUMN_NAME = "v";
  private final StringExtractor extractor = StringExtractor.get();

  private final KeyspaceOperator keyspaceOperator;

  public static void main(String[] args) throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", HOST_PORT);
    ExampleDaoV2 ed = new ExampleDaoV2(createKeyspaceOperator(KEYSPACE, c));
    ed.insert("key1".getBytes(), "value1");

    System.out.println(ed.get("key1".getBytes()));
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
  public void insert(final byte[] key, final String value) {
    createMutator(keyspaceOperator).insert(
        key, CF_NAME, createColumn(COLUMN_NAME, value, extractor, extractor));
  }

  private Clock createClock() {
    return keyspaceOperator.createClock();
  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public String get(final byte[] key) throws HectorException {
    ColumnQuery<String, String> q = createColumnQuery(keyspaceOperator, extractor, extractor);
    Result<HColumn<String, String>> r = q.setKey(key).
        setName(COLUMN_NAME).
        setColumnFamily(CF_NAME).
        execute();
    HColumn<String, String> c = r.get();
    return c.getValue();
  }

  /**
   * Get multiple values
   * @param keys
   * @return
   */
  public Map<byte[], String> getMulti(byte[]... keys) {
    MultigetSliceQuery<String,String> q = createMultigetSliceQuery(keyspaceOperator, extractor, extractor);
    q.setColumnFamily(CF_NAME);
    q.setKeys(keys);
    q.setColumnNames(COLUMN_NAME);

    Result<Rows<String,String>> r = q.execute();
    Rows<String,String> rows = r.get();
    Map<byte[], String> ret = new HashMap<byte[], String>(keys.length);
    for (byte[] k: keys) {
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
  public void insertMulti(Map<byte[], String> keyValues) {
    Mutator m = createMutator(keyspaceOperator);
    for (Map.Entry<byte[], String> keyValue: keyValues.entrySet()) {
      m.addInsertion(keyValue.getKey(), CF_NAME,
          createColumn(COLUMN_NAME, keyValue.getValue(), createClock(), extractor, extractor));
    }
    m.execute();
  }

  /**
   * Delete multiple values
   */
  public void delete(byte[]... keys) {
    Mutator m = createMutator(keyspaceOperator);
    for (byte[] key: keys) {
      m.addDeletion(key, CF_NAME,  COLUMN_NAME, extractor);
    }
    m.execute();
  }
}
