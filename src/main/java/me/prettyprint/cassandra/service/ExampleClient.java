package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;

import org.apache.cassandra.service.Column;
import org.apache.cassandra.service.ColumnPath;

/**
 * Example client that uses the cassandra hector client.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class ExampleClient {

  public static void main(String[] args) throws IllegalStateException, PoolExhaustedException, Exception {
    CassandraClientPool pool = CassandraClientPoolFactory.INSTANCE.get();
    CassandraClient client = pool.borrowClient("tush", 9160);
    // A load balanced version would look like this:
    // CassandraClient client = pool.borrowClient(new String[] {"cas1:9160", "cas2:9160", "cas3:9160"});

    try {
      Keyspace keyspace = client.getKeyspace("Keyspace1");
      ColumnPath columnPath = new ColumnPath("Standard1", null, bytes("column-name"));

      // insert
      keyspace.insert("key", columnPath, bytes("value"));

      // read
      Column col = keyspace.getColumn("key", columnPath);

      System.out.println("Read from cassandra: " + string(col.getValue()));

    } finally {
      // return client to pool. do it in a finally block to make sure it's executed
      pool.releaseClient(client);
    }
  }
}
