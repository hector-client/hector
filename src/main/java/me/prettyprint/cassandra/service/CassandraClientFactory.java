package me.prettyprint.cassandra.service;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientFactory {

  public CassandraClient create() {
    return new CassandraClientImpl();
  }
}
