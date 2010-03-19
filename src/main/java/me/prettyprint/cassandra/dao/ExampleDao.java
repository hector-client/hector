package me.prettyprint.cassandra.dao;

import me.prettyprint.cassandra.service.Keyspace;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.NotFoundException;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;

/**
 * An example DAO (data access object) which uses the Command pattern.
 * <p/>
 * This DAO is simple, it provides a get/insert/delte API for String values.
 * The underlying cassandra implementation stores the values under Keyspace1.key.Standard1.v
 * where key is the value's key, Standard1 is the name of the column family and "v" is just a column
 * name that's used to hold the value.
 * <p/>
 * what's interesting to notice here is that ease of operation that the command pattern provides.
 * The pattern assumes only one keyspace is required to perform the operation (get/insert/remove)
 * and injects it to the {@link Command#execute(Keyspace)} abstract method which is implemented
 * by all the dao methods.
 * The {@link Command#execute(String, int, String)} which is then invoked, takes care of creating
 * the {@link Keyspace} instance and releasing it after the operation completes.
 *
 * @author Ran Tavory (rantav@gmail.com)
 */
public class ExampleDao {

  private final static String CASSANDRA_KEYSPACE = "Keyspace1";
  private final static int CASSANDRA_PORT = 9170;
  private final static String CASSANDRA_HOST = "localhost";
  private final String CF_NAME = "Standard1";
  /** Column name where values are stored */
  private final String COLUMN_NAME = "v";

  public static void main(String[] args) throws Exception {
    ExampleDao ed = new ExampleDao();
    ed.insert("key1", "value1");

    System.out.println(ed.get("key1"));
  }

  /**
   * Insert a new value keyed by key
   *
   * @param key   Key for the value
   * @param value the String value to insert
   */
  public void insert(final String key, final String value) throws Exception {
    execute(new Command<Void>() {
      public Void execute(final Keyspace ks) throws Exception {
        ks.insert(key, createColumnPath(COLUMN_NAME), bytes(value));
        return null;
      }
    });
  }

  /**
   * Get a string value.
   *
   * @return The string value; null if no value exists for the given key.
   */
  public String get(final String key) throws Exception {
    return execute(new Command<String>() {
      public String execute(final Keyspace ks) throws Exception {
        try {
          return string(ks.getColumn(key, createColumnPath(COLUMN_NAME)).getValue());
        } catch (NotFoundException e) {
          return null;
        }
      }
    });
  }

  /**
   * Delete a key from cassandra
   */
  public void delete(final String key) throws Exception {
    execute(new Command<Void>() {
      public Void execute(final Keyspace ks) throws Exception {
        ks.remove(key, createColumnPath(COLUMN_NAME));
        return null;
      }
    });
  }

  protected static <T> T execute(Command<T> command) throws Exception {
    return command.execute(CASSANDRA_HOST, CASSANDRA_PORT, CASSANDRA_KEYSPACE);
  }

  protected ColumnPath createColumnPath(String columnName) {
    ColumnPath columnPath = new ColumnPath(CF_NAME);
    columnPath.setColumn(columnName.getBytes());
    return columnPath;
  }
}
