package me.prettyprint.cassandra.examples;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;
import me.prettyprint.cassandra.dao.SpringCommand;
import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;

public class ExampleSpringDao {
  // statics for default values
  private static String CASSANDRA_KEYSPACE = "Keyspace1";
  private static String CF_NAME = "Standard1";
  private static String COLUMN_NAME = "v";
  // configurable values
  private String keyspace = CASSANDRA_KEYSPACE;
  private String columnFamilyName = CF_NAME;
  private String columnName = COLUMN_NAME;
  private ConsistencyLevel consistencyLevel = CassandraClient.DEFAULT_CONSISTENCY_LEVEL;
  // collaborator
  private final CassandraClientPool cassandraClientPool;

  /**
   * Must be constructed with a CassandraClientPool
   * @param cassandraClientPool
   */
  public ExampleSpringDao(CassandraClientPool cassandraClientPool) {
    this.cassandraClientPool = cassandraClientPool;
  }

  /**
   * Insert a new value keyed by key
   * @param key Key for the value
   * @param value the String value to insert
   */
  public <K> void insert(final K key, final String value, final Serializer<K> keySerializer) throws HectorException {
    execute(new SpringCommand<Void>(cassandraClientPool){
      @Override
      public Void execute(final KeyspaceService ks) throws HectorException {
        ks.insert(keySerializer.toBytes(key), new ColumnParent(columnFamilyName), new Column(bytes(columnName), bytes(value), ks.createClock()));
        return null;
      }
    });
  }

  /**
   * Get a string value.
   * @return The string value; null if no value exists for the given key.
   */
  public <K> String get(final K key, final Serializer<K> keySerializer) throws HectorException {
    return execute(new SpringCommand<String>(cassandraClientPool){
      @Override
      public String execute(final KeyspaceService ks) throws HectorException {
        try {
          return string(ks.getColumn(keySerializer.toBytes(key), createColumnPath(columnName)).getValue());
        } catch (HNotFoundException e) {
          return null;
        }
      }
    });
  }

  /**
   * Delete a key from cassandra
   */
  public <K> void delete(final K key, final Serializer<K> keySerializer) throws HectorException {
    execute(new SpringCommand<Void>(cassandraClientPool){
      @Override
      public Void execute(final KeyspaceService ks) throws HectorException {
        ks.remove(keySerializer.toBytes(key), createColumnPath(columnName));
        return null;
      }
    });
  }


  protected ColumnPath createColumnPath(String columnName) {
    return new ColumnPath(columnFamilyName).setColumn(bytes(columnName));
  }

  protected <T> T execute(SpringCommand<T> command) throws HectorException {
    return command.execute(keyspace, consistencyLevel);
  }


  public void setKeyspace(String keyspace) {
    this.keyspace = keyspace;
  }

  public void setColumnFamilyName(String columnFamilyName) {
    this.columnFamilyName = columnFamilyName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public void setConsistencyLevel(ConsistencyLevel consistencyLevel) {
    this.consistencyLevel = consistencyLevel;
  }
}
