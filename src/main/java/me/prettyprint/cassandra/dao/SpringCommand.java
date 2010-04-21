package me.prettyprint.cassandra.dao;

import org.apache.cassandra.thrift.ConsistencyLevel;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.Keyspace;

public abstract class SpringCommand<OUTPUT> {
  private CassandraClientPool cassandraClientPool;

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
   * @throws Exception
   */
  public abstract OUTPUT execute(final Keyspace ks) throws Exception;


  protected final OUTPUT execute(String keyspace, ConsistencyLevel consistency)
      throws Exception {
    CassandraClient c = cassandraClientPool.borrowClient();
    Keyspace ks = c.getKeyspace(keyspace, consistency);
    try {
      return execute(ks);
    } finally {
      cassandraClientPool.releaseClient(ks.getClient());
    }
  }

}
