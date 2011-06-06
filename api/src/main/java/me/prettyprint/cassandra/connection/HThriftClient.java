package me.prettyprint.cassandra.connection;

import org.apache.cassandra.thrift.Cassandra;


public interface HThriftClient {

  /**
   * Returns a new Cassandra.Client on each invocation using the underlying transport
   *
   */
  public Cassandra.Client getCassandra();

  public Cassandra.Client getCassandra(String keyspaceNameArg);

  HThriftClient close();


  HThriftClient open();


  boolean isOpen();

  /**
   * If CassandraHost was not null we use {@link CassandraHost#getCassandraThriftSocketTimeout()}
   * if it was greater than zero. Otherwise look for an environment
   * variable name CASSANDRA_THRIFT_SOCKET_TIMEOUT value.
   * If doesn't exist, returns 0.
   * @param cassandraHost
   */
  public int getTimeout(HCassandraHost cassandraHost);

  public void startToUse();
  
  /**
   * @return Time in MS since it was used.
   */
  public long getSinceLastUsed();

  @Override
  public String toString();

  /**
   * Compares the toString of these clients
   */
  @Override
  public boolean equals(Object obj);

}
