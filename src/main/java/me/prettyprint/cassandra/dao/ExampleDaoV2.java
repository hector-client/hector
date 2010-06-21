package me.prettyprint.cassandra.dao;

import me.prettyprint.cassandra.model.Column;
import me.prettyprint.cassandra.model.ColumnQuery;
import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.KeyspaceOperatorFactory;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.MutatorFactory;
import me.prettyprint.cassandra.model.QueryFactory;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.ClusterFactory;

public class ExampleDaoV2 {

  private final static String KEYSPACE = "Keyspace1";
  private final static String HOST_PORT = "localhost:9170";
  private final static String CF_NAME = "Standard1";
  /** Column name where values are stored */
  private final static String COLUMN_NAME = "v";

  private final KeyspaceOperator keyspaceOperator;
  
  public static void main(String[] args) throws HectorException {
    Cluster c = ClusterFactory.getOrCreate("MyCluster", HOST_PORT);
    ExampleDaoV2 ed = new ExampleDaoV2(KeyspaceOperatorFactory.create(KEYSPACE, c));
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
    Mutator m = MutatorFactory.createMutator(keyspaceOperator);
    m.insert(key, CF_NAME, m.createColumn(COLUMN_NAME, value));
  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public String get(final String key) throws HectorException {
    ColumnQuery q = QueryFactory.createColumnQuery(keyspaceOperator);
    Result<Column> r = q.setKey(key).setName(COLUMN_NAME).setColumnFamily(CF_NAME).execute();
    Column c = r.get();
    if (c == null || c.getValue() == null) {
      return null;
    }
    return c.getValue().asString();
  }

  /**
   * Delete a key from cassandra
   */
  public void delete(final String key) throws HectorException {
    Mutator m = MutatorFactory.createMutator(keyspaceOperator);
    m.delete(key, CF_NAME, COLUMN_NAME);
  }
}
