package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.jndi.CassandraClientJndiResourcePool;

/**
 * This is a wrapper class around {@link CassandraClientFactory} used by the JNDI
 * resource pool {@link CassandraClientJndiResourcePool}.
 *
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 *
 */

public final class JndiCassandraClientFactory extends CassandraClientFactory {
  /**
   * JndiCassandraClientFactory constructor.
   *
   * @param url   url of the host that contains Cassandra
   * @param port  port number that Cassandra is listening on
   */

  public JndiCassandraClientFactory(String url, int port) {
    super(url, port);
  }
}
