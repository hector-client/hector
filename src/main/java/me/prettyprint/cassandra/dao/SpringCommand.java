package me.prettyprint.cassandra.dao;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.ConsistencyLevel;

public abstract class SpringCommand<OUTPUT> {
  private final CassandraClientPool cassandraClientPool;

  public SpringCommand(CassandraClientPool cassandraClientPool) {
    this.cassandraClientPool = cassandraClientPool;
  }

  /**
   * Implement this abstract method to operate on cassandra.
   *
   * the given keyspace is the keyspace referenced by {@link #execute(String, int, String)}.
   * The method {@link #execute(String, int, String)} calls this method internally and provides it
   * with the keyspace reference.
   *
   * @param ks
   * @return
   */
  public abstract OUTPUT execute(final KeyspaceService ks) throws HectorException;

  public final OUTPUT execute(String keyspace, ConsistencyLevel consistency) throws HectorException {
    CassandraClient c = cassandraClientPool.borrowClient();
    KeyspaceService ks = c.getKeyspace(keyspace, consistency);
    try {
      return execute(ks);
    } finally {
      cassandraClientPool.releaseClient(ks.getClient());
    }
  }

}
